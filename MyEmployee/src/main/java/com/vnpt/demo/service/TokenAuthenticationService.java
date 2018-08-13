package com.vnpt.demo.service;



import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security
        .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 300000; // 1 min = 60000;   5 min = 300000
    static final String SECRET = "hjdWKlDvKAiJfM9y";
    static final String TOKEN_PREFIX = "VNPT";
    static final String HEADER_STRING = "Authorization";
    static final String AUTHORITIES_KEY ="scopes";

    //Create "Authorization string" add to Header then sent to client
    public static void addAuthentication(HttpServletResponse res, String username, Collection<? extends GrantedAuthority> authorities) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
//        res.addHeader(HEADER_STRING, JWT);
    }

    // Check Authorization string from Client
    @SuppressWarnings("unchecked")
    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        Set<GrantedAuthority> authorities =  new HashSet<>();
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            
	        Object scopes = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody().get(AUTHORITIES_KEY);
            
			List<Map<String, Object>> listScope = 
	        	    (List<Map<String, Object>>) (List<?>) scopes;
	        
	        for (Map<String, Object> map : listScope) {
	            for (Map.Entry<String, Object> entry : map.entrySet()) {
	                System.out.println("ROLE: "+ entry.getValue());
	                authorities.add(new SimpleGrantedAuthority(entry.getValue().toString()));
	            }
	        }
	        

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }

}