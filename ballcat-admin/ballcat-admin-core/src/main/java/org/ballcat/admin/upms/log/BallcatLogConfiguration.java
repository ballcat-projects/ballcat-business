package org.ballcat.admin.upms.log;

import lombok.RequiredArgsConstructor;
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
import org.ballcat.springsecurity.configuer.SpringSecurityConfigurerCustomizer;
import org.ballcat.springsecurity.oauth2.server.authorization.config.configurer.OAuth2AuthorizationServerConfigurerExtension;
import org.ballcat.web.accesslog.AbstractAccessLogFilter;
import org.ballcat.web.accesslog.AccessLogRecordOptions;
import org.ballcat.web.accesslog.AccessLogRule;
import org.ballcat.web.accesslog.annotation.AccessLogRuleFinder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * @author hccake
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(LoginLogService.class)
public class BallcatLogConfiguration {

	@RequiredArgsConstructor
	@Configuration(proxyBeanMethods = false)
	static class LogConfiguration {

		private final RequestMappingHandlerMapping requestMappingHandlerMapping;

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
			// 合并 annotationRules 和 propertiesRules, 注解高于配置
			List<AccessLogRule> annotationRules = AccessLogRuleFinder
				.findRulesFormAnnotation(requestMappingHandlerMapping);
			List<AccessLogRule> propertiesRules = accessLogProperties.getAccessLogRules();
			List<AccessLogRule> accessLogRules = AccessLogRuleFinder.mergeRules(annotationRules, propertiesRules);

			AccessLogRecordOptions defaultRecordOptions = accessLogProperties.getDefaultAccessLogRecordOptions();

			BusinessAccessLogFilter businessAccessLogFilter = new BusinessAccessLogFilter(defaultRecordOptions,
					accessLogRules, new AccessLogSaveThread(accessLogService), principalAttributeAccessor);
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
		public OperationLogHandler<OperationLog> customOperationLogHandler(OperationLogService operationLogService,
				PrincipalAttributeAccessor principalAttributeAccessor) {
			return new CustomOperationLogHandler(operationLogService, principalAttributeAccessor);
		}

	}

	@ConditionalOnClass(SpringSecurityConfigurerCustomizer.class)
	@Configuration(proxyBeanMethods = false)
	static class SpringSecurityLogConfiguration {

		/**
		 * spring security 过滤器链中的访问日志过滤器配置
		 */
		@Bean
		@ConditionalOnProperty(prefix = AccessLogProperties.PREFIX, name = "enabled", havingValue = "true")
		@ConditionalOnBean(AbstractAccessLogFilter.class)
		public SpringSecurityConfigurerCustomizer accessLogFilterConfigurerCustomizer(
				AbstractAccessLogFilter accessLogFilter) {
			return new AccessLogSpringSecurityConfigurerCustomizer(accessLogFilter);
		}

	}

	@ConditionalOnClass(OAuth2AuthorizationServerConfigurerExtension.class)
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

	}

}