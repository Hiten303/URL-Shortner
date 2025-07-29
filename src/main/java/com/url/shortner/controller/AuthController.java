package com.url.shortner.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.dtos.LoginRequest;
import com.url.shortner.dtos.RegisterRequest;
import com.url.shortner.entities.user;
import com.url.shortner.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	  
	  @Autowired
	  private UserService userService;
	 
	  @PostMapping("/public/login")
      public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
             return ResponseEntity.ok(userService.authenticateUser(loginRequest));
	  }
	  
	  @PostMapping("/public/register")
      public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
    	  user User=new user();
    	  User.setUsername(registerRequest.getUsername());
    	  User.setEmail(registerRequest.getEmail());
    	  User.setPassword(registerRequest.getPassword());
    	  User.setRole("ROLE_USER");
          userService.registerUser(User);
    	  
		  return ResponseEntity.ok("User Registered successfully");
      }
}
