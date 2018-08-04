package id.stimik.khs.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.stimik.khs.R;
import id.stimik.khs.models.Dosen;
import id.stimik.khs.models.DosenResponse;
import id.stimik.khs.models.ItemDosen;
import id.stimik.khs.models.ItemMatKul;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.DOSEN;
import static id.stimik.khs.data.Contans.MATKUL;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogMatkul extends DialogFragment {
    private static final String KEY = "sender";
    private static final String ID_SEMESTER = "id_semester";
    private static final String TAG = "DialogMatkul";
    Unbinder unbinder;

    @BindView(R.id.et_kode)
    EditText etKode;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_sks)
    EditText etSks;
    @BindView(R.id.et_dosen)
    EditText etDosen;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.card)
    CardView card;

    private ProgressDialog progress;
    private ItemMatKul item;
    int id_semester = 0;
    private int id_dosen;

    List<ItemDosen> dosenList = new ArrayList<>();
    List<String> namaDosen = new ArrayList<>();

    public DialogMatkul() {
        // Required empty public constructor
    }

    public static DialogMatkul newInstance(ItemMatKul itemMahasiswa, int id_jurusan) {
        Bundle args = new Bundle();
        args.putSerializable(KEY, itemMahasiswa);
        args.putInt(ID_SEMESTER, id_jurusan);
        DialogMatkul fragment = new DialogMatkul();
        fragment.setArguments(args);
        return fragment;
    }

    public static DialogMatkul newInstance(int id_semester) {
        Bundle args = new Bundle();
        args.putInt(ID_SEMESTER, id_semester);
        DialogMatkul fragment = new DialogMatkul();
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
        View v = inflater.inflate(R.layout.dialog_matkul, container, false);
        unbinder = ButterKnife.bind(this, v);
        id_semester = getArguments().getInt(ID_SEMESTER);

        if (getArguments().getSerializable(KEY) != null) {
            item = (ItemMatKul) getArguments().getSerializable(KEY);
            etNama.setText(item.getNama());
            etKode.setText(item.getKode());
            etSks.setText(item.getSks()+"");
            etDosen.setText(item.getDosen().getNama());
            btnSubmit.setText("Update");
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.et_dosen, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_dosen:
                if (dosenList.size() == 0) {
                    loadData();
                } else {
                    dialogDosen();
                }
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
        AndroidNetworking.post(MATKUL)
                .addBodyParameter("kode", etKode.getText().toString())
                .addBodyParameter("nama", etNama.getText().toString())
                .addBodyParameter("sks", etSks.getText().toString())
                .addBodyParameter("id_dosen", String.valueOf(id_dosen))
                .addBodyParameter("id_semester", String.valueOf(id_semester))
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

    void editData(ItemMatKul itemMatKul) {
        openDialog();
        AndroidNetworking.put(MATKUL + "/{id_matkul}")
                .addPathParameter("id_matkul", String.valueOf(itemMatKul.getId()))
                .addBodyParameter("kode", etKode.getText().toString())
                .addBodyParameter("nama", etNama.getText().toString())
                .addBodyParameter("sks", etSks.getText().toString())
                .addBodyParameter("id_dosen", String.valueOf(id_dosen))
                .addBodyParameter("id_semester", String.valueOf(id_semester))
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
                        CommonUtil.showToast(getActivity(), "Gagal Di edit");
                        dialogSucces.onSucces();
                        dismiss();
                    }
                });
    }

    public void dialogDosen() {
        if(namaDosen.size() == 0){
            for (int i = 0; i < dosenList.size(); i++) {
                namaDosen.add(dosenList.get(i).getNama());
            }
        }
        String[] items = new String[namaDosen.size()];
        items = namaDosen.toArray(items);

        final String[] finalItems = items;
        DialogUtils.dialogArray(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etDosen.setText(finalItems[which]);
                id_dosen = dosenList.get(which).getId();
            }
        });
    }

    private void loadData() {
        openDialog();
        AndroidNetworking.get(DOSEN)
                .build()
                .getAsObject(DosenResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        loadResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        Log.d(TAG, "onError: " + anError.getErrorDetail());
                    }
                });
    }

    void loadResponse(Object response) throws NullPointerException {
        if (response instanceof DosenResponse) {
            if (((DosenResponse) response).isSuccess()) {
                if (((DosenResponse) response).getData() != null && ((DosenResponse) response).getData().size() != 0) {
                    dosenList.addAll(((DosenResponse) response).getData());
                    for (int i = 0; i <dosenList.size() ; i++) {
                        Log.d(TAG, "dialogMatkul: "+dosenList.get(i).getNama());
                    }
                    dialogDosen();
                } else {
                    CommonUtil.showToast(getActivity(), "Data tidak ditemukan");
                }
            }

        }
    }
}
