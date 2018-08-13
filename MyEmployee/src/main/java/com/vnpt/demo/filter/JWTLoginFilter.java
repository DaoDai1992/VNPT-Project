package com.vnpt.demo.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vnpt.demo.model.AccountCredentials;
import com.vnpt.demo.service.TokenAuthenticationService;



public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		AccountCredentials credentials = new AccountCredentials(request.getParameter("username"),
				request.getParameter("password"));
		System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s", credentials.getUsername(),
				credentials.getPassword());

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("JWTLoginFilter.successfulAuthentication:");
		//Create "Authorization string" add to Header then sent to client
		TokenAuthenticationService.addAuthentication(response, authResult.getName(), authResult.getAuthorities());
		
		System.out.println("Authorization String=" + response.getHeader("Authorization"));
	}
}
