package com.digitalbook.user.controller;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;

import com.digitalbook.user.dto.BookLogoFile;
import com.digitalbook.user.dto.BookWithByteFile;
import com.digitalbook.user.dto.Books;
import com.digitalbook.user.dto.UserDTO;
import com.digitalbook.user.jwt.AuthRequest;
import com.digitalbook.user.jwt.JwtResponse;
import com.digitalbook.user.jwt.JwtUtils;
import com.digitalbook.user.jwt.UserDetailsImpl;
import com.digitalbook.user.model.Role;
import com.digitalbook.user.model.User;
import com.digitalbook.user.payload.request.SubscriptionPayload;
import com.digitalbook.user.repository.SubscriptionRepository;
import com.digitalbook.user.repository.UserRepository;
import com.digitalbook.user.service.UserService;
import com.digitalbooks.user.payload.response.MessageResponse;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/digitalbooks")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Value("${bookservice.host}")
	private String bookServiceHost;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Autowired
	UserRepository userRepository;

	
	private byte[] byteslogo;
	String bookUrl = "http://localhost:8082/digitalbooks/searchBook/";
	String createBook= "http://localhost:8082/digitalbooks/author/";


	
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
		
		
		
		@PostMapping("/author/{author-id}/books")
		public ResponseEntity<?> createABook(HttpServletRequest request, @RequestBody Books book, @PathVariable("author-id") Long id) {
			if (ObjectUtils.isEmpty(id))
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid author id"));
			
			System.out.println("Inside createbook author id : "+id);
			String jwt = jwtUtils.parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String authorName = jwtUtils.getUserNameFromJwtToken(jwt);
				book.setAuthorName(authorName);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid Request"));
			}
			System.out.println("book: " +book.toString());
			return userService.createBook(book, id);
		}
		
		
		
		
	/*	@PostMapping(value="/author/{author-id}/books", consumes = { "application/json" })
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
				
		}*/

/*	@GetMapping("/searchBook")	
	public ResponseEntity<?> searchBook(@RequestParam("category") String category, @RequestParam("title") String title,
			@RequestParam("author") String author, @RequestParam("price") double price, @RequestParam("publisher") String publisher) throws Exception{
		
		BookLogoFile bookLogoFile=null;
		Books bookServiceResponse = new Books();
		byte[] logo = restTemplate.getForObject(bookUrl+"logo/"+ category +"/"+title+"/"+author+"/"
				+price+"/"+publisher, byte[].class);
		int authorId = userService.findByName(author);
		bookServiceResponse = restTemplate.getForObject(bookUrl + category +"/"+title+"/"+author+"/"
				+price+"/"+publisher, Books.class);
		try {
			Blob blob = UserService.fetchBlob(logo);
			bookLogoFile = new BookLogoFile(blob,bookServiceResponse);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		if(bookServiceResponse == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Invalid book search"));
		}
		else {
			
			return ResponseEntity.ok(bookLogoFile);
		}
		
	}
	*/
	
	@GetMapping("/search")
	public ResponseEntity<?> searchBooks(@Nullable @RequestParam(value = "category",required = false) String category,
			@Nullable @RequestParam(value = "title",required = false) String title,
			@Nullable @RequestParam(value = "author",required = false) String author) {
		if (ObjectUtils.isEmpty(category) && ObjectUtils.isEmpty(title) && ObjectUtils.isEmpty(author))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid search request"));
		
		return userService.searchBooks(category, title, author);
	}
	
	
	@PostMapping("/{book-id}/subscribe")
	public ResponseEntity<?> subscribeABook(@RequestBody SubscriptionPayload subscriptionRequest,
			@PathVariable("book-id") Long bookId) {
		System.out.println("Inside subscribeABook = ");
		if (Objects.isNull(bookId) || bookId <= 0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Book id"));
		
		return userService.subscribeBook(subscriptionRequest, bookId);
	}
	
	
	@GetMapping("/readers/{user-id}/books/{subscription-id}")
	public ResponseEntity<?> fetchSubscribedBook(@PathVariable("user-id") Long userId, @PathVariable("subscription-id") Long subscriptionId) {
		if (ObjectUtils.isEmpty(userId) || !userRepository.existsById(userId))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid user id"));
		if (ObjectUtils.isEmpty(subscriptionId) || !subscriptionRepository.existsById(subscriptionId))
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid subscription id"));

		return userService.fetchSubscribedBook(userId,subscriptionId);
	}
	
	
	@GetMapping("/readers/{user-id}/books")
	public ResponseEntity<?> fetchAllSubscribedBooks(@PathVariable("user-id") Long userId) {
		if (Objects.isNull(userId) || userId<=0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid User id"));
		
		return userService.fetchAllSubscribedBooks(userId);
	}

	
	@GetMapping("/author/{author-id}/getAllBooks")
	public ResponseEntity<?> getAuthorBooks(@PathVariable("author-id") Long authorId) {
		if (Objects.isNull(authorId) || authorId<=0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid author id"));
		
		return userService.getAuthorBooks(authorId);
	}
	
	
	@PutMapping("/author/{author-id}/updateBook/{book-id}")
	public ResponseEntity<?> updateABook(@RequestBody Books book, @PathVariable("author-id") Long authorId, @PathVariable("book-id") Long bookId) {
		if (Objects.isNull(authorId) || authorId<=0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid author id "));
		if (Objects.isNull(bookId) || bookId<=0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Book id"));
		
		
		return userService.updateBook(authorId,bookId, book);
	}
	
	
	@PostMapping("/author/{author-id}/books/{book-id}")
	public ResponseEntity<?> blockABook(@PathVariable("author-id") Long authorId, @PathVariable("book-id") Long bookId, @RequestParam("block") boolean block) {
		if (Objects.isNull(authorId) || authorId<=0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Author id"));
		if (Objects.isNull(bookId) || bookId<=0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Book id"));
		
		return userService.blockBook(bookId,authorId,block);
	}
	
	
	@PostMapping("/readers/{user-id}/books/{subscription-id}/cancel-subscription")
	public ResponseEntity<?> cancelSubscription(@PathVariable("user-id") Long userId, @PathVariable("subscription-id") Long subscriptionId) {
		if (Objects.isNull(userId) || userId==0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid user id"));
		if (Objects.isNull(subscriptionId) || subscriptionId==0)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid subscription id"));
		
		
		return userService.cancelSubscription(userId, subscriptionId);
	}
	
}
