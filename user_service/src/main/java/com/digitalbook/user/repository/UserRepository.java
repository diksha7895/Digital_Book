package com.digitalbook.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.digitalbook.user.model.User;



public interface UserRepository extends JpaRepository<User,Integer> {
	//Optional<User> findByUserName(String username);
	User findByUserNameAndEmail(String userName,String email);
	User findByEmail(String email);
	User findByUserName(String userName);

}
