package com.energysolution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.energysolution.security.AccountProvider;

@Configuration
@ComponentScan(basePackages={"com.energysolution.security"})
@EnableWebSecurity 	// Spring Security 지원 : SpringSecurityFilterChain 포함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.cors().disable()
		.httpBasic().disable()	// rest API 이므로 기본설정 필요 없음
        .csrf().disable()		//rest API 사용시 필요 없음
        .formLogin().disable()
      //  .headers().frameOptions().disable()
      //  .and()	//JWT token인증이므로 Session 필요 없음
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//권한
		http.authorizeRequests()			//요청에 대한 권한 지정
			.antMatchers("/","/Main/**")
			.permitAll()
			.antMatchers("/User/**").hasRole("USER")
			.anyRequest().authenticated();
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
