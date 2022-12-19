package com.digitalbook.user.dto;

public class UserDTO {

	private int id;
	private String userName;
	private String password;
	private String email;
	private RoleDTO role;
	

	public UserDTO() {
		super();
		
	}
	
	public UserDTO(String userName, String password, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
	}
	public UserDTO(int id, String userName, String password, String email, RoleDTO role) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RoleDTO getRole() {
		return role;
	}
	public void setRole(RoleDTO role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", role=" + role + "]";
	}
	
	
}