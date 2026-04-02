package com.url.shortner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//@Data
@Table(name="users")
public class user {
@Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private long id;
 private String email;
 private String username;
 private String password;
 private String role="ROLE_USER";
public String getRole() {
	return role;
}
public long getId() {
	return id;
}
public String getUsername() {
	
	return username;
}
public String getPassword() {
	return password;
}
public String getEmail() {
	return email;
}

 
public void setId(long id) {
	this.id = id;
}
public void setEmail(String email) {
	this.email = email;
}
public void setUsername(String username) {
	this.username = username;
}
public void setPassword(String password) {
	this.password = password;
}
public void setRole(String role) {
	this.role = role;
}
}
