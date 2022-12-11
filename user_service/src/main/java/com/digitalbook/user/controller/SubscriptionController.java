package com.digitalbook.user.controller;

import java.sql.Blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.digitalbook.user.dto.BookContentResponse;
import com.digitalbook.user.dto.BookLogoFile;
import com.digitalbook.user.dto.Books;
import com.digitalbook.user.model.Subscription;
import com.digitalbook.user.service.SubscriptionService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/digitalbooks")
public class SubscriptionController {


	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	RestTemplate restTemplate;

	String bookUrl = "http://localhost:8082/digitalbooks/getBook/";
	
	public static final String USER_NOT_FOUND = "User is not found";
	public static final String BOOK_NOT_FOUND = "This book is currently not available";
	public static final String SUBSCRIPTION_NOT_FOUND ="No subscription found";
	
	@GetMapping("readers/{emailId}/books/{subscription-id}")
	public ResponseEntity<?> fetchBookContentBySubscriptionId(@PathVariable("emailId") String email,
			@PathVariable("subscription-id") String subscriptionId) throws Exception {
		Books responseBook = null;
		BookLogoFile bookLogo = null;
		int userId = subscriptionService.getUserIdByEmail(email);
		Subscription subscription = subscriptionService.fetchSubscriptionById(subscriptionId);
		if (userId == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
		}

		if (subscription == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SUBSCRIPTION_NOT_FOUND);
		}
		if (subscription.getUser().getId() != userId) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("The user does not have subscription for this book");
		} else {

			byte[] logo = restTemplate.getForObject(bookUrl + "subscribed/logo/" + subscription.getBookId(),
					byte[].class);
			responseBook = restTemplate.getForObject(bookUrl + "subscribed/" + subscription.getBookId(), Books.class);

			try {
				Blob blob = subscriptionService.fetchBlob(logo);
				bookLogo = new BookLogoFile(blob, responseBook);
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
			if (responseBook == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BOOK_NOT_FOUND);
			} else {
				return ResponseEntity.ok(bookLogo);
			}

		}

	}
	
	//Reader read subscribed books
	@GetMapping("readers/{emailId}/books/{subscription-id}/read")
	public ResponseEntity<?> fetchBookContent(@PathVariable("emailId") String email,
			@PathVariable("subscription-id") String subscriptionId) throws Exception {

		BookContentResponse responseBook = null;
		int userId = subscriptionService.getUserIdByEmail(email);
		Subscription subscription = subscriptionService.fetchSubscriptionById(subscriptionId);
		if (userId == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
		}

		if (subscription == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(SUBSCRIPTION_NOT_FOUND);
		}
		if (subscription.getUser().getId() != userId) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("User doesn't have any subscription for this book");
		} else {

			responseBook = restTemplate.getForObject(bookUrl + "subscribed/content/" + subscription.getBookId(),
					BookContentResponse.class);

			if (responseBook == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BOOK_NOT_FOUND);
			} else if (!responseBook.isActive()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This book is blocked");
			} else {
				return ResponseEntity.ok(responseBook);
			}

		}

	}
}
