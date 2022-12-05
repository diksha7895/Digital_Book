package com.digitalbook.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbook.user.model.User;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	//Optional<User> findByUserName(String username);
	User findByUserAndEmail(String username,String email);
	User findByEmail(String email);
	User findByName(String username);

}
