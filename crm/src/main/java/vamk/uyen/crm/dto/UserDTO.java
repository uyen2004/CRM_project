package vamk.uyen.crm.dto;


public class UserDTO {
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	private Integer Id;
	private String email;
	private String password;
	private Integer role_id;
}
