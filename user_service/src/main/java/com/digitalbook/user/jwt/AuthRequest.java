package com.digitalbook.user.jwt;

public class AuthRequest {
	
	private String userName;
	private String pwd;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public AuthRequest(String userName, String pwd) {
		super();
		this.userName = userName;
		this.pwd = pwd;
	}
	public AuthRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "AuthRequest [userName=" + userName + ", pwd=" + pwd + "]";
	}
	
}
