package com.example.demo.service.security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.model.Task;
import com.example.demo.service.TaskServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private TaskServiceImpl serviceImpl;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/user/**").hasRole("USER")
						.requestMatchers("/usertask/**").hasRole("USER")
						.requestMatchers("/**").permitAll())
				.formLogin((form) -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/", true)
						.successHandler((request, response, authentication) -> {
							// Customize success URL based on role
							request.getSession().setAttribute("successMessage", "Login Successful");

							Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
							authorities.forEach(authority -> {
								if (authority.getAuthority().equals("ROLE_ADMIN")) {
									try {
										response.sendRedirect("/admin/index");
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else if (authority.getAuthority().equals("ROLE_USER")) {
									try {
										response.sendRedirect("/user/index");
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							});
						}).permitAll())
				.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
		return httpSecurity.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
