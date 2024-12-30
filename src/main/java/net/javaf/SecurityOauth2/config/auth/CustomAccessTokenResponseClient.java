package net.javaf.SecurityOauth2.config.auth;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomAccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
	private RestTemplate restTemplate = new RestTemplate(Arrays.asList(
	        new FormHttpMessageConverter(),
	        new OAuth2AccessTokenResponseHttpMessageConverter()));
	
	@Value("${feign.emp.oauth.app-key}")
	private String lmpAappKey;

	public CustomAccessTokenResponseClient() {
		super();
		log.info("CustomAccessTokenResponseClient START");
		log.info("CustomAccessTokenResponseClient START");
		log.info("CustomAccessTokenResponseClient START");
		log.info("CustomAccessTokenResponseClient START");
		log.info("CustomAccessTokenResponseClient START");
		log.info("CustomAccessTokenResponseClient START");
		log.info("CustomAccessTokenResponseClient START");
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
	}

	@Override
	public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
		
		ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
        HttpHeaders headers = generateHeaders();
        MultiValueMap<String, String> param = generateParam(clientRegistration, authorizationGrantRequest);
        URI uri = getUri(authorizationGrantRequest);
        RequestEntity<?> requestEntity = new RequestEntity<>(param, headers, HttpMethod.POST, uri);
        ResponseEntity<OAuth2AccessTokenResponse> response = getResponse(requestEntity);
        return response.getBody();
	}
	
	private URI getUri(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        return UriComponentsBuilder
                .fromUriString(authorizationGrantRequest.getClientRegistration().getProviderDetails().getTokenUri())
                .build().toUri();
    }
	
	private MultiValueMap<String, String> generateParam(
            ClientRegistration clientRegistration,
            OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest
    ) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(OAuth2ParameterNames.GRANT_TYPE, clientRegistration.getAuthorizationGrantType().getValue());
        params.add(OAuth2ParameterNames.CLIENT_ID, clientRegistration.getClientId());
        params.add(OAuth2ParameterNames.CLIENT_SECRET, clientRegistration.getClientSecret());
        params.add(OAuth2ParameterNames.CODE, authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        params.add("svc_code", "609");
        params.add("country", "KR");
        params.add("language", "ko");
        params.add("callback_url", "http%3A%2F%2Fdev.openapi.developer.lge.com%3A8989%2FSsoCallBack");
        params.add("redirect_url", "http%3A%2F%2Fdev.openapi.developer.lge.com%3A8989%2FSsoCallBack");
        return params;
    }
	
	private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Signature", getSignature(lmpAappKey, String.valueOf(System.currentTimeMillis()).substring(0, 10), "HmacSHA256"));
        return headers;
    }
	
	private ResponseEntity<OAuth2AccessTokenResponse> getResponse(RequestEntity<?> request) {
        try {
            return this.restTemplate.exchange(request, OAuth2AccessTokenResponse.class);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error("INVALID_TOKEN_RESPONSE_ERROR_CODE",
                    "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: "
                            + ex.getMessage(),
                    null);
            throw new OAuth2AuthorizationException(oauth2Error, ex);
        }
    }
	
	public String getSignature(String key, String data, String algmType) {
		String hash = "";
		try {
			Mac hMAC = Mac.getInstance(algmType);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), algmType);
			hMAC.init(secretKey);
			hash = Base64.getEncoder().encodeToString(hMAC.doFinal(data.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException : {}", e);
		} catch (InvalidKeyException e) {
			log.error("InvalidKeyException : {}", e);
		}
		
		return hash;
	}
}
