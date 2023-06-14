package org.ballcat.admin.upms.log;

import org.ballcat.log.operation.handler.OperationLogHandler;
import org.ballcat.business.log.handler.CustomAccessLogHandler;
import org.ballcat.business.log.handler.CustomOperationLogHandler;
import org.ballcat.business.log.model.entity.AccessLog;
import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.service.AccessLogService;
import org.ballcat.business.log.service.LoginLogService;
import org.ballcat.business.log.service.OperationLogService;
import org.ballcat.business.log.thread.AccessLogSaveThread;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.web.accesslog.AccessLogHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

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
	@ConditionalOnBean(AccessLogService.class)
	@ConditionalOnMissingBean(AccessLogHandler.class)
	public AccessLogHandler<AccessLog> customAccessLogHandler(AccessLogService accessLogService) {
		return new CustomAccessLogHandler(new AccessLogSaveThread(accessLogService), principalAttributeAccessor);
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

	@ConditionalOnClass(OAuth2AuthorizationServerConfigurer.class)
	@ConditionalOnBean(LoginLogService.class)
	@ConditionalOnMissingBean(LoginLogHandler.class)
	@Configuration(proxyBeanMethods = false)
	static class SpringAuthorizationServerLoginLogConfiguration {

		/**
		 * Spring Authorization Server 的登录日志处理，监听登录事件记录登录登出
		 * @param loginLogService 操作日志Service
		 * @param authorizationServerSettings 授权服务器设置
		 * @return SpringAuthorizationServerLoginLogHandler
		 */
		@Bean
		public LoginLogHandler springAuthorizationServerLoginLogHandler(LoginLogService loginLogService,
				AuthorizationServerSettings authorizationServerSettings) {
			return new SpringAuthorizationServerLoginLogHandler(loginLogService, authorizationServerSettings);
		}

	}

}