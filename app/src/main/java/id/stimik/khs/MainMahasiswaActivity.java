package id.stimik.khs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.stimik.khs.activities.mahasiswa.MatkulAmbilActivity;
import id.stimik.khs.activities.mahasiswa.MatkulSemesterActivity;
import id.stimik.khs.activities.mahasiswa.StudySemesterActivity;
import id.stimik.khs.activities.mahasiswa.TranskripResultActivity;
import id.stimik.khs.data.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainMahasiswaActivity extends AppCompatActivity {

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_ambil_makul)
    LinearLayout btnAmbilMakul;
    @BindView(R.id.btn_hasil_study)
    LinearLayout btnHasilStudy;
    @BindView(R.id.btn_trankrip)
    LinearLayout btnTrankrip;
    @BindView(R.id.btn_mahasiswa)
    LinearLayout btnMahasiswa;

    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mahasiswa);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        session = new Session(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_id_logout:
                session.logoutUser();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick({R.id.btn_ambil_makul, R.id.btn_hasil_study, R.id.btn_trankrip, R.id.btn_mahasiswa})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ambil_makul:
                startActivity(new Intent(this, MatkulSemesterActivity.class));
                break;
            case R.id.btn_hasil_study:
                startActivity(new Intent(this, StudySemesterActivity.class));
                break;
            case R.id.btn_trankrip:
                startActivity(new Intent(this, TranskripResultActivity.class));
                break;
            case R.id.btn_mahasiswa:
                break;
        }
    }
}
