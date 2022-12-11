package com.digitalbook.user.service;

import java.sql.Blob;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbook.user.dto.Books;
import com.digitalbook.user.model.Subscription;
import com.digitalbook.user.model.User;
import com.digitalbook.user.payload.request.SubscriptionPayload;
import com.digitalbook.user.repository.SubscriptionRepository;
import com.digitalbook.user.repository.UserRepository;
import com.digitalbooks.user.payload.response.MessageResponse;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public int getUserIdByEmail(String email) {
		Optional<Integer> userId = subscriptionRepository.fetchUserByEmail(email);
		
		return userId.isEmpty()? 0 : userId.get();
		}
	
	public Optional<List<Subscription>> fetchSubscribedBooksForUser(int userId) {
		
		return subscriptionRepository.fetchSubscriptionByUser(userId);
		
	}
	
	public Subscription fetchSubscriptionById(String subscriptionId) {
		Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);
		if(!subscription.isEmpty()) {
			if( !subscription.get().isCancelled()) {
				return subscription.get();
			}
			else 
				return null;
		}
		else {
			return null;
		}
		
	}

	public ResponseEntity<?> subscribe(int bookId,Books responseBook, SubscriptionPayload subscribe) {
	       if(responseBook!=null) {
	    	   
	    	   User user =userRepository.findByEmail(subscribe.getEmail());

	    		   Subscription subscription = new Subscription();
	    		   subscription.setId(UUID.randomUUID().toString());
	    		   subscription.setBookId(bookId);
	    		   subscription.setUser(user);
	    		   subscription.setCancelled(false);
	    		   subscription.setDateOfCancellation(null);
	    		   subscription.setDateOfSubscription(new Date());
	    		   Subscription savedSubscription = subscriptionRepository.save(subscription);
	    		   
	    		   return ResponseEntity.ok(new com.digitalbooks.user.payload.response.MessageResponse("This book is successfully subscribed with subscription id: "+ savedSubscription.getId())); 	
			}
			else {
				return ResponseEntity.badRequest().body(new com.digitalbooks.user.payload.response.MessageResponse("Book does not exist"));
			}
		}
	
	public boolean checkduplicateSubscription(int bookId, SubscriptionPayload subscribe) {
		User user =userRepository.findByEmail(subscribe.getEmail());
		Subscription subscription = subscriptionRepository.findByBookIdAndUser(bookId,user);
		boolean isDuplicate = false;
		
		if(subscription!=null && subscription.getUser().getId()==user.getId() &&  subscription.getBookId()==bookId
				&& !subscription.isCancelled()) {
			isDuplicate=true;
		}
		 
		return isDuplicate;
	}
	
	public ResponseEntity<MessageResponse> cancelSubscription(String subscriptionId) {
		Optional<Subscription> subscription = subscriptionRepository.findById(subscriptionId);
		if(!subscription.isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, -24);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
			Date twentyfourHoursAgo = calendar.getTime();
			if(subscription.get().getDateOfSubscription().before(twentyfourHoursAgo)) {
				return ResponseEntity.ok(new MessageResponse("Cancellation time period is over."));
			}
			else {
				subscription.get().setCancelled(true); 
				subscriptionRepository.save(subscription.get());
				return ResponseEntity.ok(new MessageResponse("Your book cancellation request is processed."));
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("No book is subscribed for you."));
		}
			
	}
	
	public boolean checkUser(SubscriptionPayload subscribe) {
		 User user =userRepository.findByEmail(subscribe.getEmail());
		 return user.getRole().getId()==2;
		}
	
	public Blob fetchBlob(byte[] logo) throws Exception {
		Blob blob= null;
		if(logo!=null) {
			try {
				blob = new javax.sql.rowset.serial.SerialBlob(logo);
					
			} catch (Exception e) {
				throw new Exception("Some issue in fetching book logo");
				 }
		
		return blob;
		}
		else {
			return blob;
		}
	}
}
