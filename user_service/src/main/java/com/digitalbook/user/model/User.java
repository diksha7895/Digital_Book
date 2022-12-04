package com.digitalbook.user.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="users",
					uniqueConstraints= {
					@UniqueConstraint(columnNames="username"),
					@UniqueConstraint(columnNames="email")
					})
public class User {
	
	public User() {
		
	}
	
	public User(int id, String userName, String password, String email, Role role) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	public User(String userName,String email,String password) {
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + ", role="
				+ role + "]";
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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid", nullable = false, unique = true, length = 20)
	private int id;
	
	@Column(name="username", nullable = false, length = 40)
	private String userName;
	
	@Column(name="password",nullable = false, length = 20)
	private String password;
	
	@Column(name="email",nullable = false, unique = true, length = 45)
	private String email;
	
//	@ManyToMany(fetch=FetchType.LAZY)
//	@JoinTable(name="user_roles",
//								joinColumns = @JoinColumn(name="user_id"),
//								inverseJoinColumns = @JoinColumn(name="role_id"))
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	
}
