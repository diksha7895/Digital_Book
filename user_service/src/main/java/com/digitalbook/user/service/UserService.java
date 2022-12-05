package com.digitalbook.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.user.model.User;
import com.digitalbook.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository uesrRepo;
	
	public void saveUser(User user) {
		userRepo.save(user);
	}
	
	public String findByName(String username) {
		return userRepo.findByName(username).getId();
	}
	
	
}
