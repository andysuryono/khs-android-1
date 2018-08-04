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
import id.stimik.khs.adapters.JurusanAdapter;
import id.stimik.khs.fragments.DialogJurusan;
import id.stimik.khs.models.ItemJurusan;
import id.stimik.khs.models.JurusanResponse;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.JURUSAN;

public class MasterJurusanActivity extends AppCompatActivity {
    private static final String TAG = "MasterJurusanActivity";
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    JurusanAdapter adapter;
    EmptyAdapter emptyAdapter;
    @BindView(R.id.btn_add_data)
    FloatingActionButton btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jurusan);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new JurusanAdapter(this);
        emptyAdapter = new EmptyAdapter(this);
        recyclerView.setAdapter(adapter);

        loadData();
        adapter.setOnItemClickListener(new JurusanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                dialogEditDelete(adapter.getItem(position));
            }

            @Override
            public void onLongClick(int position) {
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
        AndroidNetworking.get(JURUSAN)
                .build()
                .getAsObject(JurusanResponse.class, new ParsedRequestListener() {
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
        if (response instanceof JurusanResponse) {
            if (((JurusanResponse) response).isSuccess()) {
                if (((JurusanResponse) response).getData() != null && ((JurusanResponse) response).getData().size() != 0) {
                    adapter.swap(((JurusanResponse) response).getData());
                    recyclerView.setAdapter(adapter);
                } else {
                    recyclerView.setAdapter(emptyAdapter);
                }
            }

        }
    }

    void dialogEditDelete(final ItemJurusan jurusan) {
        String[] items = new String[]{"Edit", "Delete"};

        DialogUtils.dialogArray(this, items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    openEditDialog(jurusan);
                } else {
                    DialogUtils.dialogDelete(MasterJurusanActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData(jurusan);
                        }
                    });
                }
            }
        });
    }

    void deleteData(ItemJurusan jurusan) {
        refresh.setRefreshing(true);
        AndroidNetworking.delete(JURUSAN + "/{id_semester}")
                .addPathParameter("id_semester", String.valueOf(jurusan.getId()))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        refresh.setRefreshing(false);
                        CommonUtil.showToast(MasterJurusanActivity.this, "Berhasil Di hapus");
                        loadData();
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                    }
                });
    }

    void openAddDialog() {
        DialogJurusan dialogJurusan = new DialogJurusan();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, dialogJurusan).addToBackStack(null).commit();
        dialogJurusan.setDialogSucces(new DialogJurusan.DialogSucces() {
            @Override
            public void onSucces() {
                loadData();
            }
        });


    }

    void openEditDialog(ItemJurusan jurusan) {
        DialogJurusan dialogJurusan = DialogJurusan.newInstance(jurusan);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(android.R.id.content, dialogJurusan).addToBackStack(null).commit();
        dialogJurusan.setDialogSucces(new DialogJurusan.DialogSucces() {
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
