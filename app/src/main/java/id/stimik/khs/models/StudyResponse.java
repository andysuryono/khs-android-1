package id.stimik.khs.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StudyResponse {

    @SerializedName("jumlah_sks")
    private int jumlahSks;

    @SerializedName("jumlah_mutu")
    private int jumlahMutu;

    @SerializedName("data")
    private List<ItemStudy> data;

    @SerializedName("ipk")
    private double ipk;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("sisa_sks")
    private int sisa_sks;

    public int getSisa_sks() {
        return sisa_sks;
    }

    public void setSisa_sks(int sisa_sks) {
        this.sisa_sks = sisa_sks;
    }

    public void setJumlahSks(int jumlahSks) {
        this.jumlahSks = jumlahSks;
    }

    public int getJumlahSks() {
        return jumlahSks;
    }

    public void setJumlahMutu(int jumlahMutu) {
        this.jumlahMutu = jumlahMutu;
    }

    public int getJumlahMutu() {
        return jumlahMutu;
    }

    public void setData(List<ItemStudy> data) {
        this.data = data;
    }

    public List<ItemStudy> getData() {
        return data;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    public double getIpk() {
        return ipk;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "StudyResponse{" +
                        "jumlah_sks = '" + jumlahSks + '\'' +
                        ",jumlah_mutu = '" + jumlahMutu + '\'' +
                        ",data = '" + data + '\'' +
                        ",ipk = '" + ipk + '\'' +
                        ",success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}