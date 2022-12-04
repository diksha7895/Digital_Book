package com.digitalbook.user.controller;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.user.jwt.JwtUtils;
import com.digitalbook.user.repository.UserRepository;

@RestController
@RequestMapping("api/v1/digitalbook")
public class AuthController {
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpReq){
		
	}
	
}
