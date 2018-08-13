package com.vnpt.demo.service;

import com.vnpt.demo.model.UserAttempts;

public interface UserService {
//	void updateFailAttempts(String username);
//
//	void resetFailAttempts(String username);

	UserAttempts getUserAttempts(String username);
	
	void updateStatusUserAttempts(String username);
}
