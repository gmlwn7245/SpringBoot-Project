package com.energysolution.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{
	private final JwtUtils jwtUtils;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Header에서 JWT 받기
		String token = jwtUtils.resolveToken((HttpServletRequest)request);
		
		// 토큰 유효한지 확인
		if(token != null && jwtUtils.checkValid(token)) {
			//유저 정보 
			Authentication auth = jwtUtils.getAuthentication(token);
			
			// SecurityContext에 Authentication 객체 저장
			SecurityContextHolder.getContext().setAuthentication(auth);
			System.out.println("유효함");
		}
		chain.doFilter(request, response);
	}
	

}
