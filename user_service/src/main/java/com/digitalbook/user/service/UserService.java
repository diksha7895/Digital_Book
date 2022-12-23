package com.digitalbook.user.service;

import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.digitalbook.book.response.BookResponse;
import com.digitalbook.user.dto.Books;
import com.digitalbook.user.dto.UserDTO;
import com.digitalbook.user.model.Role;
import com.digitalbook.user.model.Subscription;
import com.digitalbook.user.model.User;
import com.digitalbook.user.payload.request.SubscriptionPayload;
import com.digitalbook.user.repository.SubscriptionRepository;
import com.digitalbook.user.repository.UserRepository;
import com.digitalbooks.user.payload.response.MessageResponse;
import com.digitalbooks.user.payload.response.SubscriptionResponse;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Value("${bookservice.host}")
	private String bookServiceHost;
	
//	public String findRoleName(String roleName) {
//		String role = roleRepository.findByRoleName(roleName);
//		return role;
//	}
	
	public Long findByName(String userName) {
		User user = userRepository.findByUserName(userName);
		return user == null ? 0 : user.getId();
	}
	
	
	public User duplicateUserNameAndEmail(String userName, String email) {
		return userRepository.findByUserNameAndEmail(userName, email);
	}
	
	
	public boolean checkAuthorExist(int authorId) {
		Optional<User> user = userRepository.findById(authorId);
		if(!user.isEmpty() && user.get().getRole().getId()==1) {
			return true;
		}
		return false;
	}
	
	
	public static Blob fetchBlob(byte[] logo) throws Exception{
		Blob blob = null;
		if(logo!=null) {
			try {
				blob = new javax.sql.rowset.serial.SerialBlob(logo);
			}catch(Exception e) {
				throw new Exception("Issue in book logo");
			}
			return blob;
		}
		else {
			return blob;
		}
	}

	
	
	public void saveUser(UserDTO userdto) {
		System.out.println("In_User_Service saveuser userdto: "+userdto);
		User user = new User();
		Role role = new Role(userdto.getRole().getId(),userdto.getRole().getRoleName());
		user.setUserName(userdto.getUserName());
		user.setPassword(userdto.getPassword());
		user.setEmail(userdto.getEmail());
		user.setRole(role);
		System.out.println("User Service saveuser user: "+user);
		userRepository.save(user);
	}
	
	
	public ResponseEntity<?> createBook( Books book, Long id) {
		String uri = bookServiceHost +"/author/"+ id + "/createBook";
		System.out.println("inside createbook in userService uri : "+uri);
		ResponseEntity<?> result = restTemplate.postForObject(uri,book, ResponseEntity.class);
		System.out.println("create book result :" +result);
		return result;
	}
	
	
	public ResponseEntity<?> updateBook(Long authorId, Long bookId, Books book) {
		String uri = bookServiceHost + "/author/" + authorId + "/updateBook/" + bookId;
		restTemplate.put(uri,book);
		return ResponseEntity.ok().build();
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<?> searchBooks(String category, String title, String author) {
		String uri = bookServiceHost + "/book/searchBooks?category="+category+"&title="+title+"&author="+author;
		ResponseEntity<?> result = restTemplate.getForEntity(uri, List.class);
		if(result.getBody() == null)
			return ResponseEntity.badRequest().body(new MessageResponse("Invalid Request"));
		List<Books> books = (List<Books>) result.getBody();
		if(books == null || books.isEmpty()) return ResponseEntity.badRequest().body(new MessageResponse("Invalid Request"));
		return ResponseEntity.ok(books);
	}
	
	
	
	public ResponseEntity<?> getAuthorBooks(Long authorId) {
		String url = bookServiceHost +  "/author/" + authorId + "/getAuthorBooks";

		return restTemplate.getForEntity(url, List.class);
	}
	
	
	
	public Subscription getSubscription(Long userId, Long subscriptionId) {
		Set<Subscription> subscriptions = getSubscriptions(userId);
		if(subscriptions == null) return null;
		Subscription subscription = subscriptions.stream().filter(sub -> sub.getId().equals(subscriptionId) && sub.isActive())
				.findAny().orElse(null);
		if (subscription != null) {
			return subscription;
		}

		return null;
	}
	
	
	
	public Set<Subscription> getSubscriptions(Long userId) {
		
		Set<Subscription> subscriptionsList = null;
		User user = verifyAndGetUser(userId);
		if(user != null)
			subscriptionsList = user.getSubscriptions();

		return subscriptionsList;
	}
	
	
	
	public User verifyAndGetUser(Long userId) {
		Optional<User> isUserAvailable = userRepository.findById(userId);

		return isUserAvailable.isPresent() ? isUserAvailable.get() : null;
	}
	
	
	
	public ResponseEntity<?> blockBook(Long bookId, Long authorId, boolean block) {
		String uri = bookServiceHost + "/author/" + authorId + "/blockBook/" + bookId +"?block=" + block;
		ResponseEntity<?> result = restTemplate.getForObject(uri, ResponseEntity.class);
		return result;
	}
	
	
	public ResponseEntity<?> subscribeBook(SubscriptionPayload subscriptionRequest, Long bookId) {
		System.out.println("Inside subscribeBook in UserService ");
		Subscription subscription = new Subscription();
		subscription.setActive(subscriptionRequest.isActive());
		subscription.setBookId(bookId);
		subscription.setSubscriptionTime(subscriptionRequest.getSubscriptionTime());
		subscription.setUserId(subscriptionRequest.getUserId());
		System.out.println("subscription : " +subscription);
		Optional<User> isUserPresent = userRepository.findById(subscription.getUserId());
		System.out.println("isUserPresent : " +isUserPresent);
		if (isUserPresent.isPresent()) {
			List<Subscription> subscriptionsList = subscriptionRepository.findByBookIdAndUserId(bookId, subscriptionRequest.getUserId());
			System.out.println("subscriptionsList : " +subscriptionsList);
			Set<Subscription> activeSubscription = subscriptionsList.stream().filter(Subscription::isActive).collect(Collectors.toSet());
			System.out.println("activeSubscription : " +activeSubscription);
			if(activeSubscription.isEmpty()) {
				String uri = bookServiceHost + "/book/" + bookId + "/checkBook";
				
				String value= restTemplate.getForObject(uri, String.class);
				if(Boolean.TRUE.equals("BookFound".equalsIgnoreCase(value))) {
					
					User user = isUserPresent.get();
					Set<Subscription> subscriptions = user.getSubscriptions();
					System.out.println("subscriptions :"+subscriptions);
					subscriptions.add(subscription);
					System.out.println("After adding subscriptions :"+subscriptions);
					user = userRepository.save(user);
					Subscription savedSubscription= user.getSubscriptions().stream().filter(sub -> (subscription.getBookId().equals(sub.getBookId()) && sub.isActive()))
					.findFirst().orElse(null);
					System.out.println("savedSubscription :"+savedSubscription);
					if(savedSubscription != null) {
						System.out.println("Subscription suucessful");
						
						return ResponseEntity.ok(new SubscriptionResponse(savedSubscription.getId(), savedSubscription.getSubscriptionTime()));
					}
				}
			}
		}
		
		return ResponseEntity.badRequest().body(new MessageResponse("Invalid Request"));

	}
	
	
	public ResponseEntity<?> fetchSubscribedBook(Long userId, Long subscriptionId) {
		Subscription subscription = getSubscription(userId, subscriptionId);
		if(subscription != null && !ObjectUtils.isEmpty(subscription.getBookId())) {
			String url = bookServiceHost + "/book/" + subscription.getBookId() + "/getSubscribedBook";
			ResponseEntity<?> result = restTemplate.getForEntity(url, BookResponse.class);
			if(result.getStatusCode().equals(HttpStatus.OK))
				return ResponseEntity.ok(result.getBody()); 
			
			return ResponseEntity.badRequest().body(result.getBody());
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Invalid Request"));
	}
	
	
	
	public ResponseEntity<?> fetchAllSubscribedBooks(Long userId) {
		
		Set<Subscription> subscriptionsList = getSubscriptions(userId);
		
		if(subscriptionsList != null && !subscriptionsList.isEmpty()) {
			List<Long> bookIds = subscriptionsList.stream().filter(Subscription::isActive)
					.map(Subscription::getBookId).collect(Collectors.toList());
			
			String uri = bookServiceHost + "/book/getSubscribedBooks";
			ResponseEntity<?> result = restTemplate.postForEntity(uri, bookIds, List.class);
			
			return ResponseEntity.ok(result.getBody());
		}
		
		return ResponseEntity.badRequest().body(new MessageResponse("Invalid Request"));
	}
	
	
	
	public ResponseEntity<?> cancelSubscription(Long userId, Long subscriptionId) {
		Subscription subscription = getSubscription(userId, subscriptionId);
		if( subscription != null) {
			int twentyFourHours = 24 * 60 * 60 * 1000;
			if (System.currentTimeMillis() - subscription.getSubscriptionTime().getTime() > twentyFourHours )
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid Request"));
			 
			subscription.setActive(false);
			subscriptionRepository.save(subscription);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Invalid Subscription"));
	}
	
	
	
}
