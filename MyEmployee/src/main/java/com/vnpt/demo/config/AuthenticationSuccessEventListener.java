package com.vnpt.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.vnpt.demo.repository.UserAttemptsRepository;
import com.vnpt.demo.service.LoginAttemptService;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private LoginAttemptService loginAttemptService;
	
	
	@Autowired
	private UserAttemptsRepository userAttemptsRepository;
	
	@Autowired
	HttpServletRequest request;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		
		loginAttemptService.loginSucceeded(request.getRemoteAddr());
		
		String name = event.getAuthentication().getName();
		System.out.println("dai.dv success================" + name);
		
		userAttemptsRepository.resetUserAttempts(name);
	}

}
