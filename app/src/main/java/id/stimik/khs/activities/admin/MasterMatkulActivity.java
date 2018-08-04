package id.stimik.khs.activities.admin;

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
import id.stimik.khs.adapters.MatkulAdapter;
import id.stimik.khs.fragments.DialogMahasiswa;
import id.stimik.khs.fragments.DialogMatkul;
import id.stimik.khs.models.ItemMahasiswa;
import id.stimik.khs.models.ItemMatKul;
import id.stimik.khs.models.MataKuliahResponse;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.MAHASISWA;
import static id.stimik.khs.data.Contans.MATKUL;

public class MasterMatkulActivity extends AppCompatActivity {
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

    MatkulAdapter adapter;
    EmptyAdapter emptyAdapter;
    int id_semester = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jurusan);
        ButterKnife.bind(this);

        getData();

        initView();
    }

    private void getData() {
        id_semester = getIntent().getIntExtra("id_semester", 0);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MatkulAdapter(this);
        emptyAdapter = new EmptyAdapter(this);
        recyclerView.setAdapter(adapter);

        loadData();
        adapter.setOnItemClickListener(new MatkulAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                dialogEditDelete(adapter.getItem(position));
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
        AndroidNetworking.get(MATKUL)
                .addQueryParameter("id_semester", String.valueOf(id_semester))
                .build()
                .getAsObject(MataKuliahResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
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
        if (response instanceof MataKuliahResponse) {
            if (((MataKuliahResponse) response).isSuccess()) {
                if (((MataKuliahResponse) response).getData() != null && ((MataKuliahResponse) response).getData().size() != 0) {
                    adapter.swap(((MataKuliahResponse) response).getData());
                    recyclerView.setAdapter(adapter);
                } else {
                    recyclerView.setAdapter(emptyAdapter);
                }
            }

        }
    }

    void dialogEditDelete(final ItemMatKul item) {
        String[] items = new String[]{"Edit", "Delete"};

        DialogUtils.dialogArray(this, items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    openEditDialog(item);
                } else {
                    DialogUtils.dialogDelete(MasterMatkulActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData(item);
                        }
                    });
                }
            }
        });
    }

    void deleteData(ItemMatKul jurusan) {
        refresh.setRefreshing(true);
        AndroidNetworking.delete(MATKUL + "/{id_matkul}")
                .addPathParameter("id_matkul", String.valueOf(jurusan.getId()))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(MasterMatkulActivity.this, "Berhasil Di hapus");
                        loadData();
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                    }
                });
    }

    void openAddDialog() {
        DialogMatkul dialogMahasiswa = DialogMatkul.newInstance(id_semester);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, dialogMahasiswa).addToBackStack(null).commit();
        dialogMahasiswa.setDialogSucces(new DialogMatkul.DialogSucces() {
            @Override
            public void onSucces() {
                loadData();
            }
        });


    }

    void openEditDialog(ItemMatKul item) {
        DialogMatkul dialogMahasiswa = DialogMatkul.newInstance(item, id_semester);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, dialogMahasiswa).addToBackStack(null).commit();
        dialogMahasiswa.setDialogSucces(new DialogMatkul.DialogSucces() {
            @Override
            public void onSucces() {
                loadData();
            }
        });


    }

    @OnClick(R.id.btn_add_data)
    public void onViewClicked() {
        openAddDialog();
    }
}
