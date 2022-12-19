package com.digitalbook.user.service;

import java.sql.Blob;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbook.user.dto.UserDTO;
import com.digitalbook.user.model.Role;
import com.digitalbook.user.model.User;
import com.digitalbook.user.payload.request.SignupRequest;
import com.digitalbook.user.repository.RoleRepository;
import com.digitalbook.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	
//	public String findRoleName(String roleName) {
//		String role = roleRepository.findByRoleName(roleName);
//		return role;
//	}
	
	public int findByName(String userName) {
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
	
}
