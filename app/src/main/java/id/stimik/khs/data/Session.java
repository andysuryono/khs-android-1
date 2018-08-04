package id.stimik.khs.data;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;


import id.stimik.khs.activities.LoginActivity;
import id.stimik.khs.models.Admin;
import id.stimik.khs.models.Dosen;
import id.stimik.khs.models.Mahasiswa;


/**
 * Created by Comp on 7/29/2017.
 */

public class Session {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "daikin";

    private static final String IS_LOGIN = "IsLoged";
    private static final String IS_FIRST = "IsFisrt";

    public static final String KEY_MAHASISWA = "mahasiswa";
    public static final String KEY_DOSEN = "dosen";
    public static final String KEY_ADMIN = "admin";
    public static final String ROLE = "role";


    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createMahasiswa(Mahasiswa mahasiswa) {
        Gson gson = new Gson();
        String json = gson.toJson(mahasiswa);

        editor.putString(KEY_MAHASISWA, json);

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ROLE, "mahasiswa");

        editor.commit();

    }

    public void createDosen(Dosen dosen) {
        Gson gson = new Gson();
        String json = gson.toJson(dosen);

        editor.putString(KEY_DOSEN, json);

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ROLE, "dosen");

        editor.commit();

    }

    public void createAdamin(Admin dosen) {
        Gson gson = new Gson();
        String json = gson.toJson(dosen);

        editor.putString(KEY_ADMIN, json);

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ROLE, "admin");
        editor.commit();

    }

    public Mahasiswa getMahasiswa() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_MAHASISWA, "");
        Mahasiswa obj = gson.fromJson(json, Mahasiswa.class);
        return obj;
    }

    public Dosen getDosen() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_DOSEN, "");
        Dosen obj = gson.fromJson(json, Dosen.class);
        return obj;
    }

    public Admin getAdmin() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_ADMIN, "");
        Admin obj = gson.fromJson(json, Admin.class);
        return obj;
    }

    public String getRole() {
        return pref.getString(ROLE, "");
    }

    public void checkLogin() {
        if (isLoggedIn()) {

        }
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        editor.putBoolean(IS_LOGIN, false);

        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void setIsLogin(Boolean v) {
        editor.putBoolean(IS_LOGIN, v);
        editor.commit();
    }

    public boolean isFirst() {
        return pref.getBoolean(IS_FIRST, false);
    }


    public void setIsFisrt(Boolean v) {
        editor.putBoolean(IS_FIRST, v);
        editor.commit();
    }


}
