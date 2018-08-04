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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.stimik.khs.R;
import id.stimik.khs.models.ItemStudy;
import id.stimik.khs.models.ItemStudy;
import id.stimik.khs.utils.CommonUtil;
import id.stimik.khs.utils.DialogUtils;

import static id.stimik.khs.data.Contans.MATKUL;
import static id.stimik.khs.data.Contans.NILAI;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogInputNilai extends DialogFragment {
    private static final String KEY = "sender";
    private static final String ID_SEMESTER = "id_semester";
    private static final String TAG = "DialogMatkul";
    Unbinder unbinder;

    @BindView(R.id.et_gender)
    EditText etNiliai;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.card)
    CardView card;


    private ProgressDialog progress;
    private ItemStudy item;
    int id_semester = 0;
    private int id_matakuliah;

    List<ItemStudy> matakul = new ArrayList<>();
    List<String> namaMatkul = new ArrayList<>();
    private int nilai;

    public DialogInputNilai() {
        // Required empty public constructor
    }

    public static DialogInputNilai newInstance(ItemStudy itemStudy) {

        Bundle args = new Bundle();
        args.putSerializable(KEY, itemStudy);
        DialogInputNilai fragment = new DialogInputNilai();
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
        View v = inflater.inflate(R.layout.dialog_nilai, container, false);
        unbinder = ButterKnife.bind(this, v);
        item = (ItemStudy) getArguments().getSerializable(KEY);

        return v;
    }

    @OnClick({R.id.et_gender, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_gender:
                dialogNilai();
                break;
            case R.id.btn_submit:
                editData(item);
                break;
        }
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


    void editData(ItemStudy itemMatKul) {
        openDialog();
        AndroidNetworking.put(NILAI + "/{id_study}")
                .addPathParameter("id_study", String.valueOf(itemMatKul.getId()))
                .addBodyParameter("id_matakuliah", String.valueOf(itemMatKul.getIdMatakuliah()))
                .addBodyParameter("id_semester", String.valueOf(itemMatKul.getIdSemester()))
                .addBodyParameter("id_mahasiswa", String.valueOf(itemMatKul.getIdMahasiswa()))
                .addBodyParameter("nilai", String.valueOf(nilai))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Berhasil Di di input");
                        dialogSucces.onSucces();
                        dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        closeDialog();
                        CommonUtil.showToast(getActivity(), "Gagal Di input");
                        dialogSucces.onSucces();
                        dismiss();
                    }
                });
    }

    public void dialogNilai() {
        final String[] items = getResources().getStringArray(R.array.array_nilai);

        DialogUtils.dialogArray(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etNiliai.setText(items[which]);
                if (which == 0) {
                    nilai = 8;
                } else if (which == 1) {
                    nilai = 6;
                } else if (which == 2) {
                    nilai = 4;

                } else if (which == 3) {
                    nilai = 2;
                } else if (which == 4) {
                    nilai = 0;

                }
            }
        });
    }


}
