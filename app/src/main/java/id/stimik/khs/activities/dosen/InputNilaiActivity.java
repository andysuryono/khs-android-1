package id.stimik.khs.activities.dosen;

import android.content.DialogInterface;
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
import id.stimik.khs.adapters.InputNilaiAdapter;
import id.stimik.khs.fragments.DialogInputNilai;
import id.stimik.khs.models.ItemMatKul;
import id.stimik.khs.models.ItemStudy;
import id.stimik.khs.models.StudyResponse;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.NILAI;

public class InputNilaiActivity extends AppCompatActivity {
    private static final String TAG = "InputNilaiActivity";

    InputNilaiAdapter adapter;
    EmptyAdapter emptyAdapter;
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
    @BindView(R.id.et_matakuliah)
    EditText etMatakuliah;


    int id_matkul = 0;
    String nama_matkul = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_input_nilai);
        ButterKnife.bind(this);

        getData();

        initView();
    }

    private void getData() {
        nama_matkul = getIntent().getStringExtra("nama_matkul");
        id_matkul = getIntent().getIntExtra("id_matkul",0);
        etMatakuliah.setText(nama_matkul);
    }

    private void initView() {


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new InputNilaiAdapter(this);
        emptyAdapter = new EmptyAdapter(this);
        recyclerView.setAdapter(adapter);

        loadData();
        adapter.setOnItemClickListener(new InputNilaiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openEditDialog(adapter.getItem(position));
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
                .addQueryParameter("id_matakuliah", String.valueOf(id_matkul))
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
                    adapter.swap(((StudyResponse) response).getData());
                    recyclerView.setAdapter(adapter);
                } else {
                    recyclerView.setAdapter(emptyAdapter);
                }
            } else {
                recyclerView.setAdapter(emptyAdapter);
                CommonUtil.showToast(InputNilaiActivity.this, ((StudyResponse) response).getMessage());
            }

        }
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
                        CommonUtil.showToast(InputNilaiActivity.this, "Berhasil Di hapus");
                        loadData();
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                    }
                });
    }
    
    void openEditDialog(ItemStudy item) {
        DialogInputNilai dialogMahasiswa = DialogInputNilai.newInstance(item);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, dialogMahasiswa).addToBackStack(null).commit();
        dialogMahasiswa.setDialogSucces(new DialogInputNilai.DialogSucces() {
            @Override
            public void onSucces() {
                loadData();
            }
        });


    }

 
    @OnClick(R.id.btn_add_data)
    public void onViewClicked() {
    }
}
