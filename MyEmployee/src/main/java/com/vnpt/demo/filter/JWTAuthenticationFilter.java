package com.vnpt.demo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.vnpt.demo.service.TokenAuthenticationService;

/**
 * Check Authorization string from Client. True -> Controller
 */
public class JWTAuthenticationFilter extends GenericFilterBean {
	public JWTAuthenticationFilter(List<String> urlPatterns){
		super();
		this.setUrlPatterns(urlPatterns);
	}
	public JWTAuthenticationFilter(String urlPattern){
		super();
		addUrlPattern(urlPattern);
	}
	public JWTAuthenticationFilter(String[] urlPatterns){
		super();
		if(urlPatterns!=null&&urlPatterns.length>0) {
			for(String urlPattern : urlPatterns) {
				addUrlPattern(urlPattern);
			}
		}
	}
	private List<String> urlPatterns = null;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	String uri = ((HttpServletRequest) servletRequest).getRequestURI();
    	if(urlPatterns!=null) {
    		for(String urlPattern : urlPatterns) {
    			if(isMatched(urlPattern, uri)) {
    				Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) servletRequest);
    		        SecurityContextHolder.getContext().setAuthentication(authentication);
    				filterChain.doFilter(servletRequest, servletResponse);
    				return;
    			}
    		}
    	}
        filterChain.doFilter(servletRequest, servletResponse);
    }
	public List<String> getUrlPatterns() {
		return urlPatterns;
	}
	public void setUrlPatterns(List<String> urlPatterns) {
		this.urlPatterns = urlPatterns;
	}
	public void addUrlPattern(String url) {
		if(url==null)
			return;
		if(url!=null&&!url.startsWith("/"))
			url = "/"+url;
		if(urlPatterns==null)
			urlPatterns = new ArrayList<String>();
		urlPatterns.add(url);
	}

	public boolean isMatched(String pattern, String line) {
		boolean result = false;
		try {
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(line);
			result = m.find();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
