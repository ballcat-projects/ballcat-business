package org.ballcat.admin.springsecurity.oauth2;

import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.springsecurity.oauth2.userdetails.DefaultOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class OAuth2SpringSecurityPrincipalAttributeAccessor implements PrincipalAttributeAccessor {

	@Override
	@SuppressWarnings("unchecked")
	public <A> A getAttribute(String name) {
		DefaultOAuth2User user = getUser();
		if (user != null) {
			return (A) user.getAttributes().get(name);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Long getUserId() {
		DefaultOAuth2User user = getUser();
		if (user != null) {
			return user.getUserId();
		}
		return null;
	}

	@Override
	public String getUsername() {
		DefaultOAuth2User user = getUser();
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}

	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private static DefaultOAuth2User getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof DefaultOAuth2User) {
			return (DefaultOAuth2User) principal;
		}
		else {
			return null;
		}
	}

}
