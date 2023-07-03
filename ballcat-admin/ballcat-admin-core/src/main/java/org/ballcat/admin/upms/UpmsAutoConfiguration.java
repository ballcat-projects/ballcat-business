package org.ballcat.admin.upms;

import org.ballcat.admin.springsecurity.*;
import org.ballcat.admin.springsecurity.oauth2.BallcatOAuth2TokenResponseEnhancer;
import org.ballcat.admin.upms.log.BallcatLogConfiguration;
import org.ballcat.business.system.component.PasswordHelper;
import org.ballcat.business.system.properties.SystemProperties;
import org.ballcat.business.system.service.SysUserService;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.security.properties.SecurityProperties;
import org.ballcat.springsecurity.configuer.SpringSecurityConfigurerCustomizer;
import org.ballcat.springsecurity.oauth2.server.authorization.web.authentication.OAuth2TokenResponseEnhancer;
import org.ballcat.springsecurity.oauth2.server.resource.introspection.SpringAuthorizationServerSharedStoredOpaqueTokenIntrospector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @author Hccake 2020/5/25 21:01
 */
@EnableAsync
@AutoConfiguration
@MapperScan("org.ballcat.business.**.mapper")
@ComponentScan({ "org.ballcat.admin.upms", "org.ballcat.business.system", "org.ballcat.business.log",
		"org.ballcat.business.infra", "org.ballcat.business.notify" })
@EnableConfigurationProperties({ SystemProperties.class, SecurityProperties.class })
@Import(BallcatLogConfiguration.class)
public class UpmsAutoConfiguration {

	/**
	 * 用户详情处理类
	 *
	 * @author hccake
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ UserDetailsService.class, SysUserService.class })
	static class UserDetailsServiceConfiguration {

		/**
		 * 用户信息协调者
		 * @return UserInfoCoordinator
		 */
		@Bean
		@ConditionalOnMissingBean({ UserDetailsService.class, UserInfoCoordinator.class })
		public UserInfoCoordinator userInfoCoordinator() {
			return new DefaultUserInfoCoordinatorImpl();
		}

		/**
		 * 用户详情处理类
		 * @return SysUserDetailsServiceImpl
		 */
		@Bean
		@ConditionalOnMissingBean
		public UserDetailsService userDetailsService(SysUserService sysUserService,
				UserInfoCoordinator userInfoCoordinator) {
			return new SysUserDetailsServiceImpl(sysUserService, userInfoCoordinator);
		}

	}

	/**
	 * 新版本 spring-security-oauth2-authorization-server 使用配置类
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(OAuth2Authorization.class)
	static class SpringOAuth2AuthorizationServerConfiguration {

		/**
		 * token 端点响应增强，追加一些自定义信息
		 * @return TokenEnhancer Token增强处理器
		 */
		@Bean
		@ConditionalOnMissingBean
		public OAuth2TokenResponseEnhancer oAuth2TokenResponseEnhancer() {
			return new BallcatOAuth2TokenResponseEnhancer();
		}

		/**
		 * 当资源服务器和授权服务器的 token 共享存储时，直接使用 OAuth2AuthorizationService 读取 token 信息
		 * @return SpringAuthorizationServerSharedStoredOpaqueTokenIntrospector
		 */
		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnProperty(prefix = "ballcat.springsecurity.oauth2.resourceserver", name = "shared-stored-token",
				havingValue = "true")
		public OpaqueTokenIntrospector sharedStoredOpaqueTokenIntrospector(
				OAuth2AuthorizationService authorizationService) {
			return new SpringAuthorizationServerSharedStoredOpaqueTokenIntrospector(authorizationService);
		}

	}

	@ConditionalOnClass(SpringSecurityConfigurerCustomizer.class)
	@Configuration(proxyBeanMethods = false)
	static class SpringSecurityConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public PasswordHelper passwordHelper(SecurityProperties securityProperties, SystemProperties systemProperties,
				PasswordEncoder passwordEncoder) {
			return new SpringSecurityPasswordHelper(securityProperties, systemProperties, passwordEncoder);
		}

		@Bean
		@ConditionalOnMissingBean
		public PrincipalAttributeAccessor principalAttributeAccessor() {
			return new SpringSecurityPrincipalAttributeAccessor();
		}

		@Bean
		@ConditionalOnMissingBean
		public SpringSecurityAuthenticationSuccessHandler springSecurityAuthenticationSuccessHandler() {
			return new SpringSecurityAuthenticationSuccessHandler();
		}

	}

}
