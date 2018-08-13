package com.vnpt.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

//	@Autowired
//	private TestService testService;

	@GetMapping("/test")
	public String test(Model model) {
//		String response = testService.getListPatient();

		return "list-patient";
	}

//	@GetMapping("/login")
//	public String login(Model model) {
//
//		return "login";
//	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
                @RequestParam(value = "error", required = false) String error,
                HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			System.out.println("===========" + getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		model.setViewName("login");

		return model;

	}
	
	private String getErrorMessage(HttpServletRequest request, String key){
		
		Exception exception = 
                   (Exception) request.getSession().getAttribute(key);
		
		String error = "";
		
		
		if(exception instanceof InternalAuthenticationServiceException) {
			error = exception.getMessage();
		}else{
			System.out.println("vao day xxxx");
			error = "Invalid username and password!";
		}
		
		return error;
	}

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/welcomemb")
	public String welcomMember(Model model){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		return "index-member";
	}

	@GetMapping("/admin")
	public String admin() {
		return "list";
	}

	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/";
	}
}
