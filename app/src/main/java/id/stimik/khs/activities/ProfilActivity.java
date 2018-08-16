package id.stimik.khs.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.stimik.khs.R;
import id.stimik.khs.data.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfilActivity extends AppCompatActivity {
    private static final String TAG = "ProfilActivity";
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_no)
    EditText etNo;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.card)
    CardView card;


    private ProgressDialog progress;
    private ArrayList<View> arrayListView = new ArrayList<>();

    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        ButterKnife.bind(this);

        session = new Session(this);
        if (session.getRole().equals("mahasiswa")) {
            etUsername.setText(session.getMahasiswa().getNama());
            etNo.setText(session.getMahasiswa().getNim());
            etAlamat.setText(session.getMahasiswa().getAlamat());
        } else if (session.getRole().equals("dosen")) {
            etUsername.setText(session.getDosen().getNama());
            etNo.setText(session.getDosen().getNip());
            etAlamat.setVisibility(View.GONE);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
