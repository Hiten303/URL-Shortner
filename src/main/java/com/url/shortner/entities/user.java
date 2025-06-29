package com.url.shortner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class user {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private long id;
 private String email;
 private String password;
 private String role="ROLE_USER";
 
}
