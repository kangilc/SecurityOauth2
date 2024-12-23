package net.javaf.SecurityOauth2.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity(debug = false)
@Slf4j
public class SecurityConfig {
	
	private final CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		try {
			http
				.csrf(AbstractHttpConfigurer::disable)
				.headers(
                    headersConfigurer ->
                        headersConfigurer
                            .frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                            )
	            )
				.authorizeHttpRequests( authorize -> authorize
					.requestMatchers(
						"/", 
						"/css/**", 
						"/images/**", 
						"/js/**", 
						"/h2-console/**"
					).permitAll()
					.requestMatchers("/api/v1/**").hasRole("USER")
					.anyRequest().authenticated()
				)
				.logout(logout -> logout
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/")
				)
				.oauth2Login(login -> login
					.userInfoEndpoint(user -> user
						.userService(customOAuth2UserService) // customOAuth2UserService)
					)
				)
				.sessionManagement( session -> session
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
				)
			;
			return http.build();
		} catch (Exception e) {
			log.error("SecurityConfig Error : {}", e);
		}
		return null;
	}
}
