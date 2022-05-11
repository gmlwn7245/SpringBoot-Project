package com.energysolution.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.energysolution.security.AccountProvider;
import com.energysolution.security.JwtAuthenticationFilter;
import com.energysolution.security.JwtUtils;

@Configuration
@ComponentScan(basePackages={"com.energysolution.security"})
@EnableWebSecurity 	// Spring Security 지원 : SpringSecurityFilterChain 포함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	/*@Autowired
	private LoginAuthenticationSuccessHandler LoginAuthSuccessHandler;
	@Autowired
	private LoginAuthenticationFailureHandler LoginAuthFailureHandler;
	@Autowired
	private AccountProvider accountProvider;
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;
*/
	@Autowired
	private JwtUtils jwtUtils;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().disable()
		.httpBasic().disable()	// rest API 이므로 기본설정 필요 없음
        .csrf().disable()		//rest API 사용시 필요 없음
        .formLogin().disable();
      //  .headers().frameOptions().disable()
      //  .and()	//JWT token인증이므로 Session 필요 없음
      //  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//권한
		http.authorizeRequests()			//요청에 대한 권한 지정
			.antMatchers("/","/Main/**")
			.permitAll()
			.antMatchers("/User/**","/Test/**")
			.permitAll();
			//.and()
			//.addFilterBefore(new JwtAuthenticationFilter(jwtUtils),
			//		UsernamePasswordAuthenticationFilter.class);
		
		//원래 여기까지 주석 풀었음!
		
			//.hasRole("USER").anyRequest().authenticated();
			//.and()
            //.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}
	
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AccountProvider();
    }
	
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
}
