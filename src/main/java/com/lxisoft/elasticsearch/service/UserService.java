package com.lxisoft.elasticsearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lxisoft.elasticsearch.model.User;
import com.lxisoft.elasticsearch.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired UserRepository userRepository;

	public List<User> getAllUsers() {
		
		return userRepository.getAllUsers();
	}

	public List<User> searchUsers(String searchTerm) {
		
		return userRepository.searchUsers(searchTerm);
	}

}
