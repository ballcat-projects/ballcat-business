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

package org.ballcat.admin.i18n.config;

import org.ballcat.autoconfigure.i18n.I18nMessageSourceAutoConfiguration;
import org.ballcat.autoconfigure.redis.MessageEventListenerAutoConfiguration;
import org.ballcat.business.i18n.provider.CustomI18nMessageProvider;
import org.ballcat.business.i18n.service.I18nDataService;
import org.ballcat.i18n.I18nMessageProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 注册一个 I18nMessageProvider
 *
 * @author hccake
 */
@AutoConfiguration(before = { I18nMessageSourceAutoConfiguration.class, MessageEventListenerAutoConfiguration.class })
@MapperScan("org.ballcat.business.i18n.mapper")
@ComponentScan("org.ballcat.business.i18n")
public class AdminI18nAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(I18nMessageProvider.class)
	public CustomI18nMessageProvider i18nMessageProvider(I18nDataService i18nDataService) {
		return new CustomI18nMessageProvider(i18nDataService);
	}

}
