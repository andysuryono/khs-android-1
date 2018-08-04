package id.stimik.khs.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("role")
	private String role;

	@SerializedName("mahasiswa")
	private Mahasiswa mahasiswa;

	@SerializedName("dosen")
	private Dosen dosen;

	@SerializedName("admin")
	private Admin admin;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setMahasiswa(Mahasiswa mahasiswa){
		this.mahasiswa = mahasiswa;
	}

	public Mahasiswa getMahasiswa(){
		return mahasiswa;
	}

	public void setDosen(Dosen dosen){
		this.dosen = dosen;
	}

	public Dosen getDosen(){
		return dosen;
	}

	public void setAdmin(Admin admin){
		this.admin = admin;
	}

	public Admin getAdmin(){
		return admin;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" + 
			"role = '" + role + '\'' + 
			",mahasiswa = '" + mahasiswa + '\'' + 
			",dosen = '" + dosen + '\'' + 
			",admin = '" + admin + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}