package com.store.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.store.model.CustomUserDetail;
import com.store.model.User;
import com.store.repository.UserRepository;
@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String emal) throws UsernameNotFoundException{
		Optional<User> user = userRepository.findUserByEmail(emal);
		user.orElseThrow(() -> new UsernameNotFoundException("user not found"));
		return user.map(CustomUserDetail::new).get();
		
		
	}
}
