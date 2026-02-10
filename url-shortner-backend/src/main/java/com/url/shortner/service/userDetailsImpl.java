package com.url.shortner.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.url.shortner.entities.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class userDetailsImpl implements UserDetails {

	
	private static final long serialVersionUID=1L;
	private Long id;
	private String username;
	private String email;
	private String password;
	private Collection <?extends GrantedAuthority> authorities;
	
	

	public userDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static userDetailsImpl build(user User) {
		GrantedAuthority authority =new SimpleGrantedAuthority(User.getRole());
		return new userDetailsImpl(
			User.getId(),
			User.getUsername(),
			User.getEmail(),
			User.getPassword(),
			Collections.singletonList(authority)
			
		);
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}
	
	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return username;
	}

}
