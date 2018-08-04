package id.stimik.khs.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.stimik.khs.R;
import id.stimik.khs.models.ItemJurusan;
import id.stimik.khs.models.ItemMahasiswa;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.MAHASISWA;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogMahasiswa extends DialogFragment {
    private static final String KEY = "sender";
    private static final String ID_JURUSAN = "id_semester";
    Unbinder unbinder;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_nim)
    EditText etNim;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_gender)
    EditText etGender;

    private ProgressDialog progress;
    private ItemMahasiswa item;
    int id_jurusan = 0;

    public DialogMahasiswa() {
        // Required empty public constructor
    }

    public static DialogMahasiswa newInstance(ItemMahasiswa itemMahasiswa, int id_jurusan) {
        Bundle args = new Bundle();
        args.putSerializable(KEY, itemMahasiswa);
        args.putInt(ID_JURUSAN, id_jurusan);
        DialogMahasiswa fragment = new DialogMahasiswa();
        fragment.setArguments(args);
        return fragment;
    }

    public static DialogMahasiswa newInstance( int id_jurusan) {
        Bundle args = new Bundle();
        args.putInt(ID_JURUSAN, id_jurusan);
        DialogMahasiswa fragment = new DialogMahasiswa();
        fragment.setArguments(args);
        return fragment;
    }

    DialogSucces dialogSucces;

    public interface DialogSucces {
        void onSucces();
    }

    public DialogSucces getDialogSucces() {
        return dialogSucces;
    }

    public void setDialogSucces(DialogSucces dialogSucces) {
        this.dialogSucces = dialogSucces;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_mahasiswa, container, false);
        unbinder = ButterKnife.bind(this, v);
        id_jurusan = getArguments().getInt(ID_JURUSAN);

        if (getArguments().getSerializable(KEY) != null) {
            item = (ItemMahasiswa) getArguments().getSerializable(KEY);
            etNama.setText(item.getNama());
            etAlamat.setText(item.getAlamat());
            etPassword.setText(item.getPassword());
            etNim.setText("NIM : " + item.getNim());
            etGender.setText(item.getGender());
            btnSubmit.setText("Update");
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.et_gender, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_gender:
                dialogGender();
                break;
            case R.id.btn_submit:
                if (getArguments().getSerializable(KEY) != null) {
                    editData(item);
                } else {
                    adddData();
                }
                break;
        }
    }

    void openDialog() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading . . . ");
        progress.setCancelable(false);
        progress.show();
    }

    void closeDialog() {
        progress.dismiss();
    }

    void adddData() {
        openDialog();
        AndroidNetworking.post(MAHASISWA)
                .addBodyParameter("id_semester", String.valueOf(id_jurusan))
                .addBodyParameter("nama", etNama.getText().toString())
                .addBodyParameter("nim", etNim.getText().toString())
                .addBodyParameter("alamat", etAlamat.getText().toString())
                .addBodyParameter("password", etPassword.getText().toString())
                .addBodyParameter("gender", etGender.getText().toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Berhasil Di tambahkan");
                        dialogSucces.onSucces();
                        dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Gagal Di tambahkan");
                        dialogSucces.onSucces();
                        dismiss();
                    }
                });
    }

    void editData(ItemMahasiswa itemMahasiswa) {
        openDialog();
        AndroidNetworking.put(MAHASISWA + "/{id_mahasiswa}")
                .addPathParameter("id_mahasiswa", String.valueOf(itemMahasiswa.getId()))
                .addBodyParameter("id_semester", String.valueOf(id_jurusan))
                .addBodyParameter("nama", etNama.getText().toString())
                .addBodyParameter("nim", etNim.getText().toString())
                .addBodyParameter("alamat", etAlamat.getText().toString())
                .addBodyParameter("password", etPassword.getText().toString())
                .addBodyParameter("gender", etGender.getText().toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Berhasil Di di edit");
                        dialogSucces.onSucces();
                        dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Gagal Di tambahkan");
                        dialogSucces.onSucces();
                        dismiss();
                    }
                });
    }

    public void dialogGender() {
        final String[] items = getResources().getStringArray(R.array.array_gender);
        DialogUtils.dialogArray(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etGender.setText(items[which]);
            }
        });
    }
}
