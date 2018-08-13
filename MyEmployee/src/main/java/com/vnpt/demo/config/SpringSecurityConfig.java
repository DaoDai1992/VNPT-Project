package com.vnpt.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vnpt.demo.filter.JWTAuthenticationFilter;
import com.vnpt.demo.filter.JWTLoginFilter;
import com.vnpt.demo.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				 .authorizeRequests()
				 .antMatchers("/","/admin").permitAll()
				 .antMatchers("/employee/**").hasRole("ADMIN")
				 .antMatchers("/employee-list/**","/employee/search").hasRole("MEMBER")
				 .antMatchers("/api/employee").authenticated()
				 .and()
				 .addFilterBefore(new JWTLoginFilter("/loginapi", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				 .addFilterBefore(new JWTAuthenticationFilter("/api/*"), UsernamePasswordAuthenticationFilter.class)
				 .formLogin()
				 .loginPage("/login")
				 .usernameParameter("username")
				 .passwordParameter("password")
				 .defaultSuccessUrl("/welcomemb").failureUrl("/login?error").and().exceptionHandling().accessDeniedPage("/403");

	}

}
