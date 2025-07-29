package com.url.shortner.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.url.shortner.service.userDetailsImpl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
    
	@Value("${jwt.secret}")
	private String jwtSecret;
    
	@Value("${jwt.expiration}")
    private int jwtExpirationMS;
	
// method to extract the JWT token 
public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");	
	if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
		return bearerToken.substring(7);
				
	}
    return null;
}
// method to generate the token
public String generateToken(userDetailsImpl userdetails) {
    String username	=userdetails.getUsername();
    String role = userdetails.getAuthorities().stream()
    		.map(authority-> authority.getAuthority())
    		.collect(Collectors.joining(","));
   
	return Jwts.builder()
			.subject(username)
			.claim("roles", role)
			.issuedAt(new Date())
			.expiration(new Date((new Date().getTime() +jwtExpirationMS)))
			.signWith(key())
			.compact();
   
}
// method to return username form JWT token	
public String getUserNameFromJwtToken(String token) {
	return Jwts.parser()
			.verifyWith((SecretKey) key())
			.build().parseSignedClaims(token)
			.getPayload().getSubject();
}

private Key key() {
	return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
}

public boolean validateToken(String authToken) {
	
		try {
			Jwts.parser().verifyWith((SecretKey) key())
			.build().parseSignedClaims(authToken);
			
			return true;
		}
		catch(JwtException e) {
			throw new RuntimeException(e);
		}
        catch(IllegalArgumentException e) {
		   	throw new RuntimeException(e);
		}
        catch(Exception e) {
        	throw new RuntimeException(e);
		}
}
}



