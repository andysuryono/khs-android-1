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
import id.stimik.khs.data.Session;
import id.stimik.khs.models.ItemMatKul;
import id.stimik.khs.models.MataKuliahResponse;
import id.stimik.khs.models.StudyResponse;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.MATKUL;
import static id.stimik.khs.data.Contans.NILAI;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAmbilMatkul extends DialogFragment {
    private static final String KEY = "sender";
    private static final String ID_SEMESTER = "id_semester";
    private static final String TAG = "DialogMatkul";
    Unbinder unbinder;
    @BindView(R.id.et_matakuliah)
    EditText etMatakuliah;
    @BindView(R.id.et_kode_matakuliah)
    EditText etKodeMatakuliah;
    @BindView(R.id.et_dosen)
    EditText etDosen;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.card)
    CardView card;


    private ProgressDialog progress;
    private ItemMatKul item;
    int id_semester = 0;
    private int id_matakuliah;

    List<ItemMatKul> matakul = new ArrayList<>();
    List<String> namaMatkul = new ArrayList<>();
    Session session;

    public DialogAmbilMatkul() {
        // Required empty public constructor
    }

    public static DialogAmbilMatkul newInstance(int id_semester) {

        Bundle args = new Bundle();
        args.putInt(ID_SEMESTER,id_semester);
        DialogAmbilMatkul fragment = new DialogAmbilMatkul();
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
        View v = inflater.inflate(R.layout.dialog_ambil_matkul, container, false);
        unbinder = ButterKnife.bind(this, v);
        id_semester = getArguments().getInt(ID_SEMESTER);
        session = new Session(getActivity());

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @OnClick({R.id.et_matakuliah, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_matakuliah:
                if (matakul.size() == 0) {
                    loadData();
                } else {
                    dialogMatkul();
                }

                break;
            case R.id.btn_submit:
                if (getArguments().getSerializable(KEY) != null) {
                    editData(item);
                } else {
                    addData();
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

    void addData() {
        openDialog();
        AndroidNetworking.post(NILAI)
                .addBodyParameter("id_semester", String.valueOf(id_semester))
                .addBodyParameter("id_matakuliah", String.valueOf(id_matakuliah))
                .addBodyParameter("id_mahasiswa", String.valueOf(session.getMahasiswa().getId()))
                .addBodyParameter("nilai", String.valueOf(0))
                .build()
                .getAsObject(StudyResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), ((StudyResponse)response).getMessage());
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
                .addBodyParameter("id_matakuliah", String.valueOf(id_matakuliah))
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

    public void dialogMatkul() {
        if (namaMatkul.size() == 0) {
            for (int i = 0; i < matakul.size(); i++) {
                namaMatkul.add(matakul.get(i).getNama());
            }
        }
        String[] items = new String[namaMatkul.size()];
        items = namaMatkul.toArray(items);

        final String[] finalItems = items;
        DialogUtils.dialogArray(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etMatakuliah.setText(finalItems[which]);
                etDosen.setText(matakul.get(which).getDosen().getNama());
                etKodeMatakuliah.setText(matakul.get(which).getKode());
                id_matakuliah = matakul.get(which).getId();
            }
        });
    }

    private void loadData() {
        openDialog();
        AndroidNetworking.get(MATKUL)
                .build()
                .getAsObject(MataKuliahResponse.class, new ParsedRequestListener() {
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
        if (response instanceof MataKuliahResponse) {
            if (((MataKuliahResponse) response).isSuccess()) {
                if (((MataKuliahResponse) response).getData() != null && ((MataKuliahResponse) response).getData().size() != 0) {
                    matakul.addAll(((MataKuliahResponse) response).getData());
                    for (int i = 0; i < matakul.size(); i++) {
                        Log.d(TAG, "dialogMatkul: " + matakul.get(i).getNama());
                    }
                    dialogMatkul();
                } else {
                    CommonUtil.showToast(getActivity(), "Data tidak ditemukan");
                }
            }

        }
    }
}
