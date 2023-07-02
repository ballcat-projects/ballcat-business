package org.ballcat.admin.upms.log;

import org.ballcat.springsecurity.configuer.SpringSecurityConfigurerCustomizer;
import org.ballcat.web.accesslog.AbstractAccessLogFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.util.Assert;

/**
 * @author hccake
 * @since 2.0.0
 */
public class AccessLogSpringSecurityConfigurerCustomizer implements SpringSecurityConfigurerCustomizer {

	private final AbstractAccessLogFilter accessLogFilter;

	public AccessLogSpringSecurityConfigurerCustomizer(AbstractAccessLogFilter accessLogFilter) {
		Assert.notNull(accessLogFilter, "accessLogFilter can not be null");
		this.accessLogFilter = accessLogFilter;
	}

	@Override
	public void customize(HttpSecurity httpSecurity) {
		httpSecurity.addFilterAfter(this.accessLogFilter, SecurityContextPersistenceFilter.class);
	}

	@Override
	public int getOrder() {
		return 10000;
	}

}
