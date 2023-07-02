package org.ballcat.admin.springsecurity;

import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.springsecurity.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityPrincipalAttributeAccessor implements PrincipalAttributeAccessor {

	@Override
	@SuppressWarnings("unchecked")
	public <A> A getAttribute(String name) {
		User user = getUser();
		if (user != null) {
			return (A) user.getAttributes().get(name);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Long getUserId() {
		User user = getUser();
		if (user != null) {
			return user.getUserId();
		}
		return null;
	}

	@Override
	public String getUsername() {
		User user = getUser();
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}

	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private static User getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		}
		else {
			return null;
		}
	}

}
