package com.digitalbook.user.controller;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import com.digitalbook.user.dto.BookWithByteFile;
import com.digitalbook.user.dto.Books;
import com.digitalbook.user.dto.UserDTO;
import com.digitalbook.user.jwt.AuthRequest;
import com.digitalbook.user.jwt.JwtResponse;
import com.digitalbook.user.jwt.JwtUtils;
import com.digitalbook.user.jwt.UserDetailsImpl;
import com.digitalbook.user.model.Role;
import com.digitalbook.user.model.User;
import com.digitalbook.user.payload.request.LoginRequest;
import com.digitalbook.user.payload.request.SignupRequest;
import com.digitalbook.user.service.UserService;
import com.digitalbooks.user.payload.response.MessageResponse;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/digitalbooks")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	
//
//	@Autowire
//	User user;
	
	private byte[] byteslogo;
	String bookUrl = "http://localhost:8081/digitalbooks/searchBook/";
	String createBook= "http://localhost:8081/digitalbooks/author/";
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO user) {
		System.out.println("UserDTO :"+user);
		User duplicateUser = userService.duplicateUserNameAndEmail(user.getUserName(),user.getEmail());
			if(duplicateUser != null){
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username & Email is already exist!"));
		}
			user.setPassword(encoder.encode(user.getPassword()));
			
			userService.saveUser(user);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User registered successfully."));

		}
	
	

		@PostMapping("/signin")
		public ResponseEntity<?> generateToken(@Valid @RequestBody AuthRequest authReq, HttpServletResponse httpServletResp, HttpSession session) 
				throws Exception {
			try {
				System.out.println("SIGNIN userName : "+authReq.getUserName()+" and Password : "+authReq.getPwd());
				Authentication authentication = authenticationManager.authenticate(new 
						UsernamePasswordAuthenticationToken(authReq.getUserName(),authReq.getPwd()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateToken(authReq.getUserName());
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				List<String> role = userDetails.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
				System.out.println("before return : "+userDetails.getUsername());
				return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getId(),userDetails.getUsername(),
						userDetails.getEmail(),role));
				
				
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid Username And Password"));
		}
	}
		
		@PostMapping(value="/author/{author-id}/books", consumes = { "application/json" })
	    public ResponseEntity<?> saveBook(
	    		@RequestBody Books book, 
	    		@PathVariable("author-id") int authorId) throws IOException {
			
			boolean isUserAuthor = userService.checkAuthorExist(authorId);
			if(isUserAuthor) {
				byte[] bytes = this.byteslogo;
				
			
				BookWithByteFile bookWithLogo = new BookWithByteFile();
				bookWithLogo.setFile(bytes);
				bookWithLogo.setBooks(book);

				Books savedBook = restTemplate.postForObject(createBook+authorId, bookWithLogo, Books.class);

				if(savedBook!=null) {
					return ResponseEntity.ok(savedBook);	
				}
				else {
			       return ResponseEntity.badRequest().body(new MessageResponse("Author has already a book with same Name"));
				}
				
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User is either not present or user does not have author role"));
			}
				
		}

		
	
	
}
