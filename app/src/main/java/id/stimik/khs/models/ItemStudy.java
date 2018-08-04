package id.stimik.khs.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemStudy implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("id_matakuliah")
    private int idMatakuliah;

    @SerializedName("nilai")
    private int nilai;

    @SerializedName("id_semester")
    private int idSemester;

    @SerializedName("semester")
    private String semester;

    @SerializedName("sks")
    private int sks;

    @SerializedName("id_mahasiswa")
    private int idMahasiswa;

    @SerializedName("matakuliah")
    private String matakuliah;

    @SerializedName("kode")
    private String kode;

    @SerializedName("dosen")
    private String dosen;

    @SerializedName("mahasiswa")
    private String mahasiswa;

    @SerializedName("nim")
    private String nim;

    public String getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(String mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdMatakuliah(int idMatakuliah) {
        this.idMatakuliah = idMatakuliah;
    }

    public int getIdMatakuliah() {
        return idMatakuliah;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public int getNilai() {
        return nilai;
    }

    public void setIdSemester(int idSemester) {
        this.idSemester = idSemester;
    }

    public int getIdSemester() {
        return idSemester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public int getSks() {
        return sks;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    @Override
    public String toString() {
        return
                "ItemStudy{" +
                        "id_matakuliah = '" + idMatakuliah + '\'' +
                        ",nilai = '" + nilai + '\'' +
                        ",id_semester = '" + idSemester + '\'' +
                        ",semester = '" + semester + '\'' +
                        ",sks = '" + sks + '\'' +
                        ",id_mahasiswa = '" + idMahasiswa + '\'' +
                        "}";
    }
}