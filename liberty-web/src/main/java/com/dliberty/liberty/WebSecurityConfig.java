package com.dliberty.liberty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		logger.info("configure httpsecurity...");
		httpSecurity.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests().anyRequest().permitAll();
	}
}
