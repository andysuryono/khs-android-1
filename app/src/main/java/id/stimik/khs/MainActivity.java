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
import id.stimik.khs.activities.admin.MasterDosenActivity;
import id.stimik.khs.activities.admin.MasterJurusanActivity;
import id.stimik.khs.activities.admin.MasterMahasiswaJurusanActivity;
import id.stimik.khs.activities.admin.MasterMatkulSemesterActivity;
import id.stimik.khs.data.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_mahasiswa)
    LinearLayout btnMahasiswa;
    @BindView(R.id.btn_dosen)
    LinearLayout btnDosen;
    @BindView(R.id.btn_jurusan)
    LinearLayout btnJurusan;
    @BindView(R.id.btn_matakuliah)
    LinearLayout btnMatakuliah;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        session = new Session(this);
    }

    @OnClick({R.id.btn_mahasiswa, R.id.btn_dosen, R.id.btn_jurusan, R.id.btn_matakuliah})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mahasiswa:
                startActivity(new Intent(this, MasterMahasiswaJurusanActivity.class));
                break;
            case R.id.btn_dosen:
                startActivity(new Intent(this, MasterDosenActivity.class));
                break;
            case R.id.btn_jurusan:
                startActivity(new Intent(this, MasterJurusanActivity.class));
                break;
            case R.id.btn_matakuliah:
                startActivity(new Intent(this, MasterMatkulSemesterActivity.class));
                break;
        }
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
}
