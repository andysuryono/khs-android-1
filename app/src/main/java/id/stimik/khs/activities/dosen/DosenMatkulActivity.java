package id.stimik.khs.activities.dosen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.stimik.khs.R;
import id.stimik.khs.adapters.EmptyAdapter;
import id.stimik.khs.adapters.MatkulAdapter;
import id.stimik.khs.data.Session;
import id.stimik.khs.models.MataKuliahResponse;

import static id.stimik.khs.data.Contans.MATKUL;

public class DosenMatkulActivity extends AppCompatActivity {
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

    Session session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_jurusan);
        ButterKnife.bind(this);

        getData();

        initView();
    }

    private void getData() {
        session = new Session(this);
    }

    private void initView() {
        btnAddData.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MatkulAdapter(this);
        emptyAdapter = new EmptyAdapter(this);
        recyclerView.setAdapter(adapter);

        loadData();
        adapter.setOnItemClickListener(new MatkulAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(DosenMatkulActivity.this, InputNilaiActivity.class);
                intent.putExtra("id_matkul",adapter.getItem(position).getId());
                intent.putExtra("nama_matkul",adapter.getItem(position).getNama());
                startActivity(intent);
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
                .addQueryParameter("id_dosen", String.valueOf(session.getDosen().getId()))
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


}
