package net.javaf.SecurityOauth2.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	GUSET("ROLE_GUEST", "손님"), 
	USER("ROLE_USER", "일반 사용자");

	private final String key;
	private final String title;
}
