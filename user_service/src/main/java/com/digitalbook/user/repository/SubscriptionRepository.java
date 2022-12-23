package com.digitalbook.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.user.model.Subscription;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

//	public Subscription findByBookId(int bookId);
//	
//	public Subscription findByBookIdAndUser(int bookId, User user);
//	
//	@Query(value = "Select U.id from Users U where U.email=:email",nativeQuery=true)
//	public Optional<Integer> fetchUserByEmail(String email);
//
//	@Query(value = "Select S.ID, S.USER_FID, S.BOOK_ID, S.DATE_OF_SUBSCRIPTION, S.CANCELLED, S.DATE_OF_CANCEL from Subscription S where S.USER_FID=:userId and cancelled=0",nativeQuery=true)
//	public Optional<List<Subscription>> fetchSubscriptionByUser(int userId);
	

	Boolean existsByBookIdAndUserId(Long bookId, Long userId);
	
	List<Subscription> findByBookIdAndUserId(Long bookId, Long userId);

}
