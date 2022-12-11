package com.digitalbook.user.service;

import java.sql.Blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.user.model.User;
import com.digitalbook.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public int findByName(String userName) {
		User user = userRepository.findByUserName(userName);
		return user == null ? 0 : user.getId();
	}
	
	public User duplicateUserNameAndEmail(String userName, String email) {
		return userRepository.findByUserNameAndEmail(userName, email);
	}
	
	public Blob fetchBlob(byte[] logo) throws Exception{
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
	
}
