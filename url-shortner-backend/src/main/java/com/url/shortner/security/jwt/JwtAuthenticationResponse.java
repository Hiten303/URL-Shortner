package com.url.shortner.security.jwt;

import lombok.Data;

@Data

public class JwtAuthenticationResponse {
   private String token;

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public JwtAuthenticationResponse(String token) {
	super();
	this.token = token;
}

public JwtAuthenticationResponse() {
	super();
	
}
}
