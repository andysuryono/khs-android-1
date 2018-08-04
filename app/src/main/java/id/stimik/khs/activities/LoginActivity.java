package id.stimik.khs.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.stimik.khs.MainActivity;
import id.stimik.khs.MainDosenActivity;
import id.stimik.khs.MainMahasiswaActivity;
import id.stimik.khs.R;
import id.stimik.khs.activities.dosen.DosenMatkulActivity;
import id.stimik.khs.data.Session;
import id.stimik.khs.models.LoginResponse;
import id.stimik.khs.utils.CommonUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


import static id.stimik.khs.data.Contans.LOGIN;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    private ProgressDialog progress;
    private ArrayList<View> arrayListView = new ArrayList<>();

    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new Session(this);
        if (session.isLoggedIn()) {
            openAdminActivity();
        }
    }

    @OnClick({R.id.btn_signin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signin:
                checkAccount();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public Boolean checkAccount() {
        boolean moveActivity = false;
        arrayListView.add(etUsername);
        arrayListView.add(etPassword);

        if (CommonUtil.validateEmptyEntries(arrayListView)) {
            moveActivity = true;
            login();
        } else {
            CommonUtil.showToast(this, "Please fill in all fields");
        }
        return moveActivity;
    }

    private void login() {
        openDialog();
        ANRequest.PostRequestBuilder post = new ANRequest.PostRequestBuilder(LOGIN);
        post.addBodyParameter("username", etUsername.getText().toString());
        post.addBodyParameter("password", etPassword.getText().toString());
        post.build().getAsObject(LoginResponse.class, new ParsedRequestListener() {
            @Override
            public void onResponse(Object response) {
                closeDialog();
                if (response instanceof LoginResponse) {
                    if (((LoginResponse) response).getStatus()) {
                        CommonUtil.showToast(LoginActivity.this, ((LoginResponse) response).getMessage());
                        getRole((LoginResponse) response);
                    } else {
                        CommonUtil.showToast(LoginActivity.this, ((LoginResponse) response).getMessage());
                    }
                } else {
                    CommonUtil.showToast(LoginActivity.this, ((LoginResponse) response).getMessage());
                }
            }

            @Override
            public void onError(ANError anError) {
                closeDialog();
                CommonUtil.showToast(LoginActivity.this, "Error");
            }
        });
    }

    void getRole(LoginResponse role) {
        if (role.getRole().equals("mahasiswa")) {
            session.createMahasiswa(role.getMahasiswa());
            openMahasiswa();
        } else if (role.getRole().equals("dosen")) {
            session.createDosen(role.getDosen());
            openDosen();
        } else if (role.getRole().equals("admin")) {
            session.createAdamin(role.getAdmin());
            openAdminActivity();
        }
    }

    void openAdminActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    void openMahasiswa() {
        Intent i = new Intent(this, MainMahasiswaActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    void openDosen() {
        Intent i = new Intent(this, MainDosenActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void openDialog() {
        progress = new ProgressDialog(this);
        progress.setMessage("Loading . . . ");
        progress.setCancelable(false);
        progress.show();
    }

    private void closeDialog() {
        progress.dismiss();
    }


}
