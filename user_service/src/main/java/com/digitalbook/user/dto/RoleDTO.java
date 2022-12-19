package com.digitalbook.user.dto;

public class RoleDTO {

	private int id;
	private String roleName;
	public RoleDTO() {
		super();
	}
	public RoleDTO(int id) {
		
		this.id = id;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "RoleDTO [id=" + id + ", roleName=" + roleName + "]";
	}
	
	
}
