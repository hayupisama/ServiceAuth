package com.mstradingcards.ServiceAuth.config;

import static com.mstradingcards.ServiceAuth.enums.UserRole.ADMIN;
import static com.mstradingcards.ServiceAuth.enums.UserRole.GAME_MASTER;
import static com.mstradingcards.ServiceAuth.enums.UserRole.PLAYER;
import static com.mstradingcards.ServiceAuth.enums.UserRole.GUEST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class AuthConfig {

	private static final String[] WHITE_LIST_URL = { "/api/users/createUser", "/api/users/login" };
	private static final String[] ADMIN_GM_LIST_URL = { "/api/users/getAllUsers", "/api/users/deleteUser/**",
			"/api/users/findByEmail" };
	@Autowired
	private JwtAuthFiltrer jwtAuthFilter;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	// private final LogoutHandler logoutHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req -> req.requestMatchers(WHITE_LIST_URL).permitAll()
						.requestMatchers(ADMIN_GM_LIST_URL).hasAnyRole(ADMIN.name(), GAME_MASTER.name())
								.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		/*
		 * .logout(logout ->
		 * logout.logoutUrl("/api/user/logout").addLogoutHandler(logoutHandler)
		 * .logoutSuccessHandler( (request, response, authentication) ->
		 * SecurityContextHolder.clearContext()));
		 */

		return http.build();
	}
}
