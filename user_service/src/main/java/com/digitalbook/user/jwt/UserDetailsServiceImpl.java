package com.digitalbook.user.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.digitalbook.user.model.User;
import com.digitalbook.user.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName){
		User users = userRepository.findByUserName(userName);
					//.orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + username));
//		if(user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
		//return new.org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),new ArrayList<>());
		return UserDetailsImpl.build(users);
	}
	
	

}
