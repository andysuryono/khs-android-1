package id.stimik.khs.activities.mahasiswa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.stimik.khs.R;
import id.stimik.khs.data.Session;

import static id.stimik.khs.data.Contans.CETAK;
import static id.stimik.khs.data.Contans.CETAK_SEMUA;

public class DownloadKHSActivity extends AppCompatActivity {

    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private ProgressDialog progress;
    String html = "";
    String mimeType = "";
    String encoding = "";

    int id_semester, id_mahasiswa;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khs);
        ButterKnife.bind(this);

        session = new Session(this);
        id_semester = getIntent().getIntExtra("id_semester", 0);
        id_mahasiswa = session.getMahasiswa().getId();
        mimeType = "text/html";
        encoding = "UTF-8";
        String url="";
        if(id_semester == 0){
            url = CETAK_SEMUA + "?id_mahasiswa=" + id_mahasiswa;
        }else {
            url = CETAK + "?id_mahasiswa=" + id_mahasiswa + "&id_semester=" + id_semester;

        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
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
