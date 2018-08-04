package id.stimik.khs.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemMatKul implements Serializable {

	@SerializedName("id_dosen")
	private int idDosen;

	@SerializedName("nama")
	private String nama;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("dosen")
	private Dosen dosen;

	@SerializedName("kode")
	private String kode;

	@SerializedName("id_semester")
	private int idSemester;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("sks")
	private int sks;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public void setIdDosen(int idDosen){
		this.idDosen = idDosen;
	}

	public int getIdDosen(){
		return idDosen;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setDosen(Dosen dosen){
		this.dosen = dosen;
	}

	public Dosen getDosen(){
		return dosen;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setIdSemester(int idSemester){
		this.idSemester = idSemester;
	}

	public int getIdSemester(){
		return idSemester;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setSks(int sks){
		this.sks = sks;
	}

	public int getSks(){
		return sks;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDeletedAt(Object deletedAt){
		this.deletedAt = deletedAt;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}

	@Override
 	public String toString(){
		return 
			"ItemSemester{" +
			"id_dosen = '" + idDosen + '\'' + 
			",nama = '" + nama + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",dosen = '" + dosen + '\'' + 
			",kode = '" + kode + '\'' + 
			",id_semester = '" + idSemester + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",sks = '" + sks + '\'' + 
			",id = '" + id + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			"}";
		}
}