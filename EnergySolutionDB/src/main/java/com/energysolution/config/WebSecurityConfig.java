package com.energysolution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.energysolution.security.AccountProvider;
import com.energysolution.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity 	// Spring Security 지원 : SpringSecurityFilterChain 포함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().disable()
        .csrf().disable()
        .headers().frameOptions().disable();
		
		//권한
		http.authorizeRequests()			//요청에 대한 권한 지정
			.antMatchers("/","/Main/**")
			.permitAll()
			.antMatchers("/User/**").hasRole("USER")
			.anyRequest().authenticated();
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
