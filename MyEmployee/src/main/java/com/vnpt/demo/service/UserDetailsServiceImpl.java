package com.vnpt.demo.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vnpt.demo.model.Role;
import com.vnpt.demo.model.User;
import com.vnpt.demo.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
        	System.out.println("IP is blocked");
            throw new RuntimeException("Account is blocked for 1 minute");
        }
		
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		System.out.println("dvd: "+user.getAccountNonLocked() + ", " + user.getUser_name());
		if(user.getAccountNonLocked() == 0){
			throw new LockedException("Your Account is blocked. Please contact to admin for re-opening");
		}
		
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole_name()));
		}

		return new org.springframework.security.core.userdetails.User(user.getUser_name(), user.getPassword(),
				grantedAuthorities);
	}
	
	private String getClientIP() {
	    String xfHeader = request.getHeader("X-Forwarded-For");
	    if (xfHeader == null){
	        return request.getRemoteAddr();
	    }
	    return xfHeader.split(",")[0];
	}
}
