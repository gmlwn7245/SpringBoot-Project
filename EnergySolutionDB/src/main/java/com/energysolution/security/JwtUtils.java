package com.energysolution.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private String secretKey = "asfasdfasdf";
	private long exp = 1000L * 60 * 60; 	//1시간

	private AccountService accountService;
	//jwt 토큰 생성
	//인자로 key받아서 넣어주는거 구현해보기
	public String createJwt(Account account) {
		//Header 부분 설정
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		//payload 부분 설정
		Map<String, Object> payloads = new HashMap<>();
		payloads.put("UserId", account.getUsername());
		payloads.put("Email", account.getEmail());
		payloads.put("Name", account.getName());
		payloads.put("role", accountService.loadUserAuthorities(account.getUsername()));
		
		//토큰 유효시간 2시간
		Long expiredTime = 1000 * 60L * 60L * 2L;
		
		//토큰 만료 시간
		Date ext = new Date();
		ext.setTime(ext.getTime()+expiredTime);
		
		//정보 저장
		
		//Token Builder
		/* 등록된 클레임은 토큰 정보를 표현하기 위해 이미 정해진 정류의 데이터들
		 * 모두 선택적으로 작성이 가능하며 사용할 것을 권장
		 */
		String jwt = Jwts.builder()
				.setSubject(account.getUsername())
				.setHeader(headers)
				.setClaims(payloads)
				.setExpiration(ext)
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
				.compact();
		
		return jwt;
	}
	
	//jwt 토큰 검증
	public Map<String, Object> checkJwt(String auth) throws UnsupportedEncodingException {
		Map<String, Object> claimMap = null;
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secretKey.getBytes("UTF-8"))	//키설정
					.parseClaimsJws(auth)	//검증
					.getBody();
			
			claimMap = claims;
		} catch (ExpiredJwtException e) {	//만료 예외
			System.out.println("#expir");
		} catch (Exception e) {				//이외의 예외
			System.out.println("#error");
		}
		return claimMap;
	}
	
	// 토큰의 유효성 + 만료일자 확인
	public boolean checkValid(String token) {
		//Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token);
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		}catch(Exception e) {
			return false;
		}
	}
	
	// Header에서 token값 가져옴
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("jwt-auth-token");
	}
	
	// Token에서 Subject 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	// Token 인증번호 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = accountService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
}
