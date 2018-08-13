package com.vnpt.demo.repository;

import com.vnpt.demo.model.UserAttempts;

public interface UserAttemptsRepository {
	UserAttempts getUserAttempts(String name);
	
	void updateStatusUserAttempts(String username);
	
	void resetUserAttempts(String name);
}
