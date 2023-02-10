package com.maan.Madison.basic.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(value = 100)
public class CustomAuthentication extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyBasicAuthenticationEntryPoint basicAuthenticationPoint;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers("/")/* .hasAnyRole("USER") */.fullyAuthenticated().and().httpBasic();
		http.authorizeRequests().antMatchers("/", "/api/**").permitAll().anyRequest().authenticated().and().httpBasic();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.httpBasic().authenticationEntryPoint(basicAuthenticationPoint);
	}

	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("MadisonApi")
				.password(passwordEncoder().encode("MadisonApi@123#"))
				.roles("USER");
				
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "/resources/**", "/styles/**", "/static/**", "/jasper/**", "/public/**",
				"/webui/**", "/h2-console/**", "/*.jsp", "/**/*.jsp", "/configuration/**", "/swagger-ui/**", "/ui/**",
				"/swagger-resources/**", "/api-docs", "/api-docs/**", "/fonts/**", "/v2/api-docs/**", "/*.html",
				"/**/*.html", "/*.jpg", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.gif", "/**/*.svg",
				"/**/*.ico", "/**/*.ttf", "/**/*.woff", "/**/*.woff2", "/**/*.otf");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
	