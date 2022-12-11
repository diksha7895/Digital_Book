package com.digitalbook.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name="users",
					uniqueConstraints= {
					@UniqueConstraint(columnNames="username"),
					@UniqueConstraint(columnNames="email")
					})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid", nullable = false, unique = true, length = 20)
	private int id;
	
	@Column(name="username", nullable = false, length = 40)
	private String userName;
	
	@Column(name="password",nullable = false)
	private String password;
	
	@Column(name="email",nullable = false, unique = true, length = 45)
	private String email;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	
	@Column(name="isActive")
	Boolean isActive;
	
	

public User() {
		
	}
	
	public User(int id, String userName, String password, String email, Role role,Boolean isActive) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.isActive = isActive;
	}
	
	public User(String userName,String email,String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive=isActive;
	}
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", role="
				+ role + ",isActive="+ isActive +" ]";
	}
	
}
