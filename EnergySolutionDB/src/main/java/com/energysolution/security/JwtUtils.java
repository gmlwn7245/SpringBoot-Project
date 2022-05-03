package com.energysolution.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private String key = "asfasdfasdf";
	private long exp = 1000L * 60 * 60; 	//1시간

	
	//jwt 토큰 생성
	public String createJwt(Account account) {
		//Header 부분 설정
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		//payload 부분 설정
		Map<String, Object> payloads = new HashMap<>();
		payloads.put("UserId", account.getUsername());
		payloads.put("Email", account.getEmail());
		payloads.put("Name", account.getUserId());
		
		//Token Builder
		String jwt = Jwts.builder()
				.setSubject("TestToken")
				.setHeader(headers)
				.setClaims(payloads)
				.setExpiration(new Date(System.currentTimeMillis()+exp))
				.signWith(SignatureAlgorithm.HS256, key.getBytes())
				.compact();
		
		return jwt;
	}
	
	//jwt 토큰 검증
	public Map<String, Object> checkJwt(String token) throws UnsupportedEncodingException {
		Map<String, Object> claimMap = null;
		
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(key.getBytes("UTF-8"))	//키설정
					.parseClaimsJws(token)	//검증
					.getBody();
			
			claimMap = claims;
		} catch (ExpiredJwtException e) {	//만료 예외
			System.out.println(e);
		} catch (Exception e) {				//이외의 예외
			System.out.println(e);
		}
		return claimMap;
	}
	
	// interceptor에서 토큰 유효성을 검증하기 위한 메서드
	public void checkValid(String token) {
		Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token);
	}
}
