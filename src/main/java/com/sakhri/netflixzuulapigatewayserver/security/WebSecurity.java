package com.sakhri.netflixzuulapigatewayserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Value("${api.users.actuator.url.path}")
	private String usersactuatorUrlPath;
	
	@Value("${api.zuul.actuator.url.path}")
	private String zuulactuatorUrlPath;
	
	@Value("${api.registration.url.path}")
	private String registrationUrlPath;
	
	@Value("${api.login.url.path}")
	private String loginUrlPath;
	
	@Autowired
	private Environment environment;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http
    	.csrf().disable()
    	.authorizeRequests()
	    	.antMatchers(usersactuatorUrlPath).permitAll()
	    	.antMatchers(zuulactuatorUrlPath).permitAll()
	    	.antMatchers(HttpMethod.GET, "/exercice-service/exercices/verify/*").permitAll()
	    	.antMatchers(HttpMethod.POST, registrationUrlPath).permitAll()
	    	.antMatchers(HttpMethod.POST, loginUrlPath).permitAll()
	    	.antMatchers(HttpMethod.OPTIONS).permitAll()
	    .anyRequest().authenticated()
	    .and()
	    .addFilter(new AuthorizationFilter(authenticationManager(), environment))
	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    }	
}