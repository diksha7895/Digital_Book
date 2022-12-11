package com.digitalbook.user.controller;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbook.user.jwt.AuthRequest;
import com.digitalbook.user.jwt.JwtResponse;
import com.digitalbook.user.jwt.JwtUtils;
import com.digitalbook.user.jwt.LoginRequest;
import com.digitalbook.user.jwt.SignupRequest;
import com.digitalbook.user.jwt.UserDetailsImpl;
import com.digitalbook.user.model.Role;
import com.digitalbook.user.model.User;
import com.digitalbook.user.service.UserService;
import com.digitalbooks.user.payload.response.MessageResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/digitalbooks")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;


	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		User duplicateUser = userService.duplicateUserNameAndEmail(user.getUserName(),user.getEmail());
			if(duplicateUser != null){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username & Email is already exist!"));
		}
			user.setPassword(encoder.encode(user.getPassword()));
			userService.saveUser(user);
			return ResponseEntity.ok(new MessageResponse("User registered successfully."));

		}

		@PostMapping("/signin")
		public ResponseEntity<?> generateToken(@RequestBody AuthRequest authReq, HttpServletResponse httpServletResp, HttpSession session) 
				throws Exception {
			try {
				Authentication authentication = authenticationManager.authenticate(new 
						UsernamePasswordAuthenticationToken(authReq.getUserName(),authReq.getPwd()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateToken(authReq.getUserName());
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				List<String> role = userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
				
				return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),
						userDetails.getEmail(),role));
				
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(new com.digitalbooks.user.payload.response.MessageResponse("Invalid Username And Password"));
		}
	}
		

		
	
	
}
