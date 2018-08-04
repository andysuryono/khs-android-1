package id.stimik.khs.activities.mahasiswa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.stimik.khs.R;
import id.stimik.khs.adapters.EmptyAdapter;
import id.stimik.khs.adapters.SemesterAdapter;
import id.stimik.khs.models.SemesterResponse;

import static id.stimik.khs.data.Contans.SEMESTER;

public class MatkulSemesterActivity extends AppCompatActivity {
    private static final String TAG = "MasterJurusanActivity";
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    SemesterAdapter adapter;
    EmptyAdapter emptyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_matkul_semester);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SemesterAdapter(this);
        emptyAdapter = new EmptyAdapter(this);
        recyclerView.setAdapter(adapter);

        loadData();
        adapter.setOnItemClickListener(new SemesterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MatkulSemesterActivity.this, MatkulAmbilActivity.class);
                intent.putExtra("id_semester",adapter.getItem(position).getId());
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
        AndroidNetworking.get(SEMESTER)
                .build()
                .getAsObject(SemesterResponse.class, new ParsedRequestListener() {
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
        if (response instanceof SemesterResponse) {
            if (((SemesterResponse) response).isSuccess()) {
                if (((SemesterResponse) response).getData() != null && ((SemesterResponse) response).getData().size() != 0) {
                    adapter.swap(((SemesterResponse) response).getData());
                    recyclerView.setAdapter(adapter);
                } else {
                    recyclerView.setAdapter(emptyAdapter);
                }
            }

        }
    }


}
