package id.stimik.khs.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
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
import id.stimik.khs.models.ItemDosen;
import id.stimik.khs.utils.CommonUtil;

import static id.stimik.khs.data.Contans.DOSEN;
import static id.stimik.khs.data.Contans.JURUSAN;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogDosen extends DialogFragment {
    private static final String KEY = "sender";
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

    private ProgressDialog progress;
    private String path = "";
    private String fileName = "";
    Bitmap myBitmap;
    int pos = 0;
    private ItemDosen item;

    public DialogDosen() {
        // Required empty public constructor
    }

    public static DialogDosen newInstance(ItemDosen item) {

        Bundle args = new Bundle();
        args.putSerializable(KEY, item);
        DialogDosen fragment = new DialogDosen();
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
        View v = inflater.inflate(R.layout.dialog_dosen, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (getArguments() != null) {
            item = (ItemDosen) getArguments().getSerializable(KEY);
            etNama.setText(item.getNama());
            etNim.setText("NIP "+item.getNip());
            etPassword.setText(item.getPassword());
            btnSubmit.setText("Update");
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        AndroidNetworking.post(DOSEN)
                .addBodyParameter("nama", etNama.getText().toString())
                .addBodyParameter("nip", etNim.getText().toString())
                .addBodyParameter("password", etPassword.getText().toString())
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

    void editData(ItemDosen jurusan) {
        openDialog();
        AndroidNetworking.put(DOSEN + "/{id_dosen}")
                .addPathParameter("id_dosen", String.valueOf(jurusan.getId()))
                .addBodyParameter("nama", etNama.getText().toString())
                .addBodyParameter("nip", etNim.getText().toString())
                .addBodyParameter("password", etPassword.getText().toString())
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

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (getArguments() != null) {
            editData(item);
        } else {
            adddData();
        }
    }


}
