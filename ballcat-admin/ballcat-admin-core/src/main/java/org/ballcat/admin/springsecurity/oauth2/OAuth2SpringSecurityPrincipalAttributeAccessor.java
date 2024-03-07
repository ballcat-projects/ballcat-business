/*
 * Copyright 2023-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
