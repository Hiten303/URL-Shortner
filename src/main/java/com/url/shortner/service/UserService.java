package com.url.shortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.url.shortner.dtos.LoginRequest;
import com.url.shortner.entities.user;
import com.url.shortner.repository.UserRepository;
import com.url.shortner.security.jwt.JwtAuthenticationResponse;
import com.url.shortner.security.jwt.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;
    
    public user registerUser(user User) {
    	User.setPassword(passwordEncoder.encode(User.getPassword()));
		return userRepository.save(User);
    }
    
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()
				)
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		userDetailsImpl userDetail=(userDetailsImpl) authentication.getPrincipal();
		String jwt=jwtUtils.generateToken(userDetail);
    	return new JwtAuthenticationResponse(jwt);
    }
    
    public user findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + name)
        );
    }	
}
