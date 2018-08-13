package com.vnpt.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vnpt.demo.model.UserAttempts;
import com.vnpt.demo.repository.UserAttemptsRepository;
import com.vnpt.demo.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserAttemptsRepository userAttemptsRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserAttempts getUserAttempts(String username) {
		// TODO Auto-generated method stub
		return userAttemptsRepository.getUserAttempts(username);
	}

	@Override
	public void updateStatusUserAttempts(String username) {
		// TODO Auto-generated method stub
		userAttemptsRepository.updateStatusUserAttempts(username);
	}

}
