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
import id.stimik.khs.activities.admin.MasterMatkulSemesterActivity;
import id.stimik.khs.activities.dosen.DosenMatkulActivity;
import id.stimik.khs.data.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainDosenActivity extends AppCompatActivity {

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_selamat_datang)
    TextView tvSelamatDatang;
    @BindView(R.id.btn_input_nilai)
    LinearLayout btnInputNilai;
    @BindView(R.id.btn_profil)
    LinearLayout btnProfil;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dosen);
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

    @OnClick({R.id.btn_input_nilai, R.id.btn_profil})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_input_nilai:
                startActivity(new Intent(this, DosenMatkulActivity.class));
                break;
            case R.id.btn_profil:
//                startActivity(new Intent(this, DosenMatkulActivity.class));
                break;
        }
    }
}
