package org.ballcat.admin.upms.log;

import org.ballcat.autoconfigure.web.accesslog.AccessLogProperties;
import org.ballcat.business.log.filter.BusinessAccessLogFilter;
import org.ballcat.business.log.handler.CustomOperationLogHandler;
import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.service.AccessLogService;
import org.ballcat.business.log.service.LoginLogService;
import org.ballcat.business.log.service.OperationLogService;
import org.ballcat.business.log.thread.AccessLogSaveThread;
import org.ballcat.log.operation.handler.OperationLogHandler;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.springsecurity.oauth2.server.authorization.config.customizer.OAuth2AuthorizationServerConfigurerCustomizer;
import org.ballcat.springsecurity.oauth2.server.resource.configurer.OAuth2ResourceServerConfigurerCustomizer;
import org.ballcat.web.accesslog.AbstractAccessLogFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(LoginLogService.class)
public class LogConfiguration {

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	public LogConfiguration(PrincipalAttributeAccessor principalAttributeAccessor) {
		this.principalAttributeAccessor = principalAttributeAccessor;
	}

	/**
	 * 访问日志保存
	 * @param accessLogService 访问日志Service
	 * @return CustomAccessLogHandler
	 */
	@Bean
	@ConditionalOnProperty(prefix = AccessLogProperties.PREFIX, name = "enabled", havingValue = "true")
	@ConditionalOnBean(AccessLogService.class)
	@ConditionalOnMissingBean(AbstractAccessLogFilter.class)
	public AbstractAccessLogFilter businessAccessLogFilter(AccessLogService accessLogService,
			AccessLogProperties accessLogProperties, PrincipalAttributeAccessor principalAttributeAccessor) {
		BusinessAccessLogFilter businessAccessLogFilter = new BusinessAccessLogFilter(accessLogProperties.getSettings(),
				new AccessLogSaveThread(accessLogService), principalAttributeAccessor);
		businessAccessLogFilter.setMaxBodyLength(accessLogProperties.getMaxBodyLength());
		businessAccessLogFilter.setOrder(accessLogProperties.getFilterOrder());
		return businessAccessLogFilter;
	}

	/**
	 * 取消 AbstractAccessLogFilter 的自动注册，需要根据不同的权限控制框架进行手动注册
	 * @param accessLogFilter 访问日志过滤器
	 * @return FilterRegistrationBean<AbstractAccessLogFilter>
	 */
	@Bean
	@ConditionalOnBean(AbstractAccessLogFilter.class)
	public FilterRegistrationBean<AbstractAccessLogFilter> accessLogFilterCloseRegistrationBean(
			AbstractAccessLogFilter accessLogFilter) {
		FilterRegistrationBean<AbstractAccessLogFilter> registrationBean = new FilterRegistrationBean<>(
				accessLogFilter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	/**
	 * 操作日志处理器
	 * @param operationLogService 操作日志Service
	 * @return CustomOperationLogHandler
	 */
	@Bean
	@ConditionalOnBean(OperationLogService.class)
	@ConditionalOnMissingBean(OperationLogHandler.class)
	public OperationLogHandler<OperationLog> customOperationLogHandler(OperationLogService operationLogService) {
		return new CustomOperationLogHandler(operationLogService, principalAttributeAccessor);
	}

	@ConditionalOnClass(OAuth2AuthorizationServerConfigurerCustomizer.class)
	@Configuration(proxyBeanMethods = false)
	static class SpringAuthorizationServerLogConfiguration {

		/**
		 * Spring Authorization Server 的登录日志处理，监听登录事件记录登录登出
		 * @param loginLogService 操作日志Service
		 * @param authorizationServerSettings 授权服务器设置
		 * @return SpringAuthorizationServerLoginLogHandler
		 */
		@ConditionalOnBean(LoginLogService.class)
		@ConditionalOnMissingBean(LoginLogHandler.class)
		@Bean
		public LoginLogHandler springAuthorizationServerLoginLogHandler(LoginLogService loginLogService,
				AuthorizationServerSettings authorizationServerSettings) {
			return new SpringAuthorizationServerLoginLogHandler(loginLogService, authorizationServerSettings);
		}

		/**
		 * 授权服务器过滤器链中的访问日志过滤器配置
		 */
		@Bean
		public OAuth2AuthorizationServerConfigurerCustomizer springAuthorizationServerAccessLogConfigurerCustomizer(
				ObjectProvider<AbstractAccessLogFilter> accessLogFilterObjectProvider) {
			return (oAuth2AuthorizationServerConfigurer, httpSecurity) -> {
				AbstractAccessLogFilter accessLogFilter = accessLogFilterObjectProvider.getIfAvailable();
				if (accessLogFilter != null) {
					// 在 SecurityContextPersistenceFilter 之后，以便可以获取到用户信息
					httpSecurity.addFilterAfter(accessLogFilter, SecurityContextPersistenceFilter.class);
				}
			};
		}

	}

	@ConditionalOnClass(OAuth2ResourceServerConfigurerCustomizer.class)
	@Configuration(proxyBeanMethods = false)
	static class SpringOAuth2ResourceServerLogConfiguration {

		/**
		 * 资源服务器过滤器链中的访问日志过滤器配置
		 */
		@Bean
		public OAuth2ResourceServerConfigurerCustomizer accessLogFilterOAuth2ResourceServerConfigurerCustomizer(
				ObjectProvider<AbstractAccessLogFilter> accessLogFilterObjectProvider) {
			return httpSecurity -> {
				AbstractAccessLogFilter accessLogFilter = accessLogFilterObjectProvider.getIfAvailable();
				if (accessLogFilter != null) {
					httpSecurity.addFilterAfter(accessLogFilter, SecurityContextPersistenceFilter.class);
				}
			};
		}

	}

}