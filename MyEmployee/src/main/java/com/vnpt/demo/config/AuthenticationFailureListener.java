package com.vnpt.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.vnpt.demo.repository.UserAttemptsRepository;
import com.vnpt.demo.service.LoginAttemptService;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@Autowired
	private UserAttemptsRepository userAttemptsRepository;
	
	@Autowired
	HttpServletRequest request;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		System.out.println("ip: " + request.getRemoteAddr());
		loginAttemptService.loginFailed(request.getRemoteAddr());
		
		String name = event.getAuthentication().getName();
		System.out.println("dai.dv================" + name);
		//userAttemptsRepository.getUserAttempts(name);
		userAttemptsRepository.updateStatusUserAttempts(name);				
	}

}
