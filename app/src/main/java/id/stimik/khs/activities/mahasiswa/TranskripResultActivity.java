package id.stimik.khs.activities.mahasiswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.stimik.khs.R;
import id.stimik.khs.adapters.EmptyAdapter;
import id.stimik.khs.adapters.HasilStudyAdapter;
import id.stimik.khs.data.Session;
import id.stimik.khs.fragments.DialogAmbilMatkul;
import id.stimik.khs.fragments.DialogMatkul;
import id.stimik.khs.models.ItemMatKul;
import id.stimik.khs.models.ItemStudy;
import id.stimik.khs.models.StudyResponse;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.NILAI;

public class TranskripResultActivity extends AppCompatActivity {
    private static final String TAG = "MasterMatkulActivity";
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.btn_add_data)
    FloatingActionButton btnAddData;

    HasilStudyAdapter adapter;
    EmptyAdapter emptyAdapter;
    @BindView(R.id.et_jumlah_sks)
    EditText etJumlahSks;
    @BindView(R.id.et_sisa_sks)
    EditText etSisaSks;
    @BindView(R.id.et_semester)
    EditText etSemester;
    @BindView(R.id.et_jumlah_mutu)
    EditText etJumlahMutu;
    @BindView(R.id.et_ips)
    EditText etIps;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa_khs);
        ButterKnife.bind(this);


        initView();
    }



    private void initView() {

        session = new Session(this);
        etSemester.setText("Semua Semester");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HasilStudyAdapter(this);
        emptyAdapter = new EmptyAdapter(this);
        recyclerView.setAdapter(adapter);

        loadData();
        adapter.setOnItemClickListener(new HasilStudyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                dialogEditDelete(adapter.getItem(position));
            }


        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        adapter.removeAll();
        refresh.setRefreshing(true);
        AndroidNetworking.get(NILAI)
                .addQueryParameter("id_mahasiswa", String.valueOf(session.getMahasiswa().getId()))
                .build()
                .getAsObject(StudyResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        Log.d(TAG, "onResponse: " + ((StudyResponse) response).getMessage());
                        loadResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                        recyclerView.setAdapter(emptyAdapter);
                        Log.d(TAG, "onError: " + anError.getErrorDetail());
                    }
                });
    }

    void loadResponse(Object response) throws NullPointerException {
        if (response instanceof StudyResponse) {
            if (((StudyResponse) response).isSuccess()) {
                if (((StudyResponse) response).getData() != null && ((StudyResponse) response).getData().size() != 0) {
                    etJumlahSks.setText(((StudyResponse) response).getJumlahSks() + " SKS");
                    etSisaSks.setText(((StudyResponse) response).getSisa_sks() + " SKS");
                    etIps.setText(((StudyResponse) response).getIpk() + "");
                    etJumlahMutu.setText(((StudyResponse) response).getJumlahMutu() + "");
                    adapter.swap(((StudyResponse) response).getData());
                    recyclerView.setAdapter(adapter);
                } else {
                    noData();
                }
            } else {
                noData();
                CommonUtil.showToast(TranskripResultActivity.this, ((StudyResponse) response).getMessage());
            }

        }
    }

    void noData(){
        etJumlahSks.setText("-");
        etJumlahSks.setText("-");
        etIps.setText("-");
        etSisaSks.setText("-");
        etJumlahMutu.setText("-");
        recyclerView.setAdapter(emptyAdapter);
    }

    void dialogEditDelete(final ItemStudy item) {
        String[] items = new String[]{"Delete"};

        DialogUtils.dialogArray(this, items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(item);
            }
        });
    }

    void deleteData(ItemStudy delete) {
        refresh.setRefreshing(true);
        AndroidNetworking.delete(NILAI + "/{id_studies}")
                .addPathParameter("id_studies", String.valueOf(delete.getId()))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(TranskripResultActivity.this, "Berhasil Di hapus");
                        loadData();
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                    }
                });
    }

    @OnClick(R.id.btn_add_data)
    public void onViewClicked() {
        print();
    }

    private void print() {
        Intent intent = new Intent(this, DownloadKHSActivity.class);
        startActivity(intent);
    }
}
