package id.stimik.khs.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DialogUtils {
    public static void dialogSucces(Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Data Berhasil Disimpan");
        builder.setPositiveButton("OK", onClickListener);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void dialogFailed(Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Data Gagal di simpan");
        builder.setPositiveButton("OK", onClickListener);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void dialogDelete(Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Anda yakin menghapus data ini ?");
        builder.setPositiveButton("OK", onClickListener);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void dialogTanggalFormat(Context context, final EditText etDate, final String format) {
        final DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        dateFormat.setTimeZone(tz);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormat.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static void dialogArray(Context context, final String[] items, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                .setTitle("Organization Type")
                .setItems(items, clickListener);

        Dialog dialog = builder.create();
        dialog.show();
    }

    public static void dialogMultipleArray(Context context, final String[] items, boolean[] checkedItem, DialogInterface.OnMultiChoiceClickListener multiChoiceClickListener, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).
//                .setTitle("Organization Type")
        setMultiChoiceItems(items, checkedItem, multiChoiceClickListener)
                .setPositiveButton("PILIH", clickListener);

        Dialog dialog = builder.create();
        dialog.show();
    }

    public static boolean[] isCheckedDialog(String[] items, String value) {
        final boolean[] checkedItem = new boolean[items.length];
        String[] separated = value.split(",");
        for (int i = 0; i < separated.length; i++) {
            for (int j = 0; j < items.length; j++) {
                if (separated[i].contains(items[j])) {
                    checkedItem[j] = true;
                }
            }
        }

        for (int i = 0; i < checkedItem.length; i++) {
            Log.d("CheckedDialog", "isCheckedDialog: " + checkedItem[i] + " " + items[i]);
        }

        return checkedItem;
    }

    public static void dialogTanggal(Context context, final EditText etDate) {
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        dateFormat.setTimeZone(tz);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
