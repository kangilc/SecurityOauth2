package net.javaf.SecurityOauth2.config.auth;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity(debug = false)
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 3600)
@Slf4j
public class SecurityConfig {
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	@Autowired
	private CustomAccessTokenResponseClient customAccessTokenResponseClient;
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	
//	private HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;
	
	private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository = new HttpSessionOAuth2AuthorizationRequestRepository();
	
	private static List<String> clients = Arrays.asList("google", "facebook");
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		try {
			http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
						"/SsoCallBack",
						"/login/oauth2/**",
						"/h2/**"
					).permitAll()
					.requestMatchers("/api/v1/**").hasRole("USER")
					.anyRequest().authenticated()
				)
				.logout(logout -> logout
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/")
				)
				.oauth2Login(oauth2 -> oauth2
//					.loginProcessingUrl("/SsoCallBack")
					// 커스텀 구간 
					.authorizationEndpoint(auth -> auth
//						.baseUri("/login/oauth2/authorization")
//						.authorizationRequestRepository(authorizationRequestRepository)
						// 등록을 하면 기존에 잘되던 google, naver 등에서도 OAuth2LoginAuthenticationFilter 부분에서 OAuth2AuthorizationRequest null 떨어지는 문제
						.authorizationRequestResolver(
							authorizationRequestResolver(this.clientRegistrationRepository)
						)
					)
//					.redirectionEndpoint(redirection -> redirection
//						.baseUri("/login/oauth2/code/*")
//					)
//					.tokenEndpoint(token -> token
//						.accessTokenResponseClient(customAccessTokenResponseClient) // customAccessTokenResponseClient(전처리용)
//					)
					.userInfoEndpoint(user -> user
						.userService(customOAuth2UserService) // customOAuth2UserService)
					)
				)
				.oauth2Client(oauth2 -> oauth2
					.authorizationCodeGrant(codeGrant -> codeGrant
						.accessTokenResponseClient(customAccessTokenResponseClient)
					)
				)
				.sessionManagement( session -> session
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
				)
			;
			return http.build();
		} catch (Exception e) {
			log.error("[SecurityConfig] :: Error : {}", e);
		}
		return null;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedHeaders(Arrays.asList("*"));
			config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
			config.setAllowedOriginPatterns(Arrays.asList(
					"http://dev.openapi.developer.lge.com:8989"
					, "http://localhost:3000"    // local test front
					, "http://localhost:8989"    // local test front					
			)); // ⭐️ 허용할 origin
			config.setAllowCredentials(true);
			//config.setExposedHeaders(Arrays.asList("*"));
			config.setExposedHeaders(Arrays.asList("*","Authorization")); //클라이언트 쪽에서 접근할 수 있는 헤더를 지정합니다.
			return config;
		};
	}
	
//	@Bean
//    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
//        return new HttpSessionOAuth2AuthorizationRequestRepository();
//    }
	
	/**
	 * 액세스 토큰 요청
	 * @return
	 */
//	@Bean
//	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
//		return new RestClientAuthorizationCodeTokenResponseClient();
//	}
	
	private OAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {

        DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, "/login/oauth2/authorization");
        authorizationRequestResolver.setAuthorizationRequestCustomizer(
                authorizationRequestCustomizer());

        return  authorizationRequestResolver;
    }

    private Consumer<OAuth2AuthorizationRequest.Builder> authorizationRequestCustomizer() {
        return customizer -> customizer
                    .additionalParameters(params -> params.put("prompt", "consent"))
                    .additionalParameters(params -> params.put("svc_code", "SVC609"))
                    .additionalParameters(params -> params.put("country", "KR"))
                    .additionalParameters(params -> params.put("language", "ko"))
                    .additionalParameters(params -> params.put("response_type", "code"))
                    .additionalParameters(params -> params.put("state", RandomString.make(13)))
                    .additionalParameters(params -> params.put("callback_url", "http://dev.openapi.developer.lge.com:8989/SsoCallBack"))
                    .additionalParameters(params -> params.put("redirect_url", "http://dev.openapi.developer.lge.com:8989/SsoCallBack"))
//                    .additionalParameters(params -> params.put("callback_url", "http%3A%2F%2Fdev.openapi.developer.lge.com%3A8989%2FSsoCallBack"))
//                    .additionalParameters(params -> params.put("redirect_url", "http%3A%2F%2Fdev.openapi.developer.lge.com%3A8989%2FSsoCallBack"))
        ;
    }
    
    private String getEndcodedUrl(String url) {
    	return URLEncoder.encode(url);
    }
    
    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return (authorities) -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

			authorities.forEach(authority -> {
				if (OidcUserAuthority.class.isInstance(authority)) {
					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;

					OidcIdToken idToken = oidcUserAuthority.getIdToken();
					OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

					// Map the claims found in idToken and/or userInfo
					// to one or more GrantedAuthority's and add it to mappedAuthorities

				} else if (OAuth2UserAuthority.class.isInstance(authority)) {
					OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

					Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

					// Map the attributes found in userAttributes
					// to one or more GrantedAuthority's and add it to mappedAuthorities

				}
			});

			return mappedAuthorities;
		};
	}
}
