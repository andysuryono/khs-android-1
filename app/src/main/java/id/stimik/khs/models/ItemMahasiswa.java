package id.stimik.khs.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemMahasiswa implements Serializable {

	@SerializedName("password")
	private String password;

	@SerializedName("nim")
	private String nim;

	@SerializedName("nama")
	private String nama;

	@SerializedName("gender")
	private String gender;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("jurusan")
	private Jurusan jurusan;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private Object deletedAt;

	@SerializedName("id_jurusan")
	private int idJurusan;

	@SerializedName("alamat")
	private String alamat;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNim(String nim){
		this.nim = nim;
	}

	public String getNim(){
		return nim;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setJurusan(Jurusan jurusan){
		this.jurusan = jurusan;
	}

	public Jurusan getJurusan(){
		return jurusan;
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

	public void setIdJurusan(int idJurusan){
		this.idJurusan = idJurusan;
	}

	public int getIdJurusan(){
		return idJurusan;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"ItemMahasiswa{" +
			"password = '" + password + '\'' + 
			",nim = '" + nim + '\'' + 
			",nama = '" + nama + '\'' + 
			",gender = '" + gender + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",jurusan = '" + jurusan + '\'' + 
			",id = '" + id + '\'' + 
			",deleted_at = '" + deletedAt + '\'' + 
			",id_jurusan = '" + idJurusan + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}