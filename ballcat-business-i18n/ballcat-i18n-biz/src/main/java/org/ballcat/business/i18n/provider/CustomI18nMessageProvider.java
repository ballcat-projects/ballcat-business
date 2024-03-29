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

package org.ballcat.business.i18n.provider;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.RandomUtil;
import org.ballcat.business.i18n.constant.I18nRedisKeyConstants;
import org.ballcat.business.i18n.model.dto.I18nDataUnique;
import org.ballcat.business.i18n.model.entity.I18nData;
import org.ballcat.business.i18n.service.I18nDataService;
import org.ballcat.i18n.I18nMessage;
import org.ballcat.i18n.I18nMessageProvider;
import org.ballcat.redis.listener.AbstractMessageEventListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;

/**
 * 自定义的 I18nMessageProvider，数据存储在数据库中，使用内存缓存提高查询效率
 *
 * @author hccake
 */
public class CustomI18nMessageProvider extends AbstractMessageEventListener<I18nDataUnique>
		implements I18nMessageProvider {

	private final I18nDataService i18nDataService;

	private final TimedCache<String, I18nMessage> cache;

	private static final int MILLISECONDS_OF_HOUR = 1000 * 60 * 60;

	private static final int MIN_TIMEOUT = (int) (MILLISECONDS_OF_HOUR * 0.9);

	private static final int MAX_TIMEOUT = (int) (MILLISECONDS_OF_HOUR * 1.1);

	public CustomI18nMessageProvider(I18nDataService i18nDataService) {
		this.i18nDataService = i18nDataService;
		// 默认过期时间设置为 1 小时
		this.cache = new TimedCache<>(MILLISECONDS_OF_HOUR, new ConcurrentHashMap<>());
		// 每秒检查一次过期
		this.cache.schedulePrune(1000);
	}

	@Override
	public I18nMessage getI18nMessage(String code, Locale locale) {
		String languageTag = locale.toLanguageTag();

		// 缓存 key
		String cacheKey = getCacheKey(code, languageTag);
		I18nMessage i18nMessage = this.cache.get(cacheKey);

		// 如果缓存没有，则查询数据库（这里数据库查询其实也加了 redis 缓存）
		if (i18nMessage == null) {
			I18nData i18nData = this.i18nDataService.getByCodeAndLanguageTag(code, languageTag);
			if (i18nData != null) {
				i18nMessage = converterToI18nMessage(i18nData);
				// 错峰过期
				int timeout = RandomUtil.randomInt(MIN_TIMEOUT, MAX_TIMEOUT);
				this.cache.put(cacheKey, i18nMessage, timeout);
			}
		}

		return i18nMessage;
	}

	/**
	 * 缓存 key
	 * @param code i18n 数据的唯一标识
	 * @param languageTag 语言标签
	 * @return String cacheKey
	 */
	private String getCacheKey(String code, String languageTag) {
		return code + ":" + languageTag;
	}

	/**
	 * 转换 i18nData to I18nMessage
	 * @param i18nData 数据库存储对象
	 * @return I18nMessage
	 */
	private I18nMessage converterToI18nMessage(I18nData i18nData) {
		I18nMessage i18nMessage = new I18nMessage();
		i18nMessage.setMessage(i18nData.getMessage());
		i18nMessage.setCode(i18nData.getCode());
		i18nMessage.setLanguageTag(i18nData.getLanguageTag());
		return i18nMessage;
	}

	@Override
	protected void handleMessage(I18nDataUnique i18nDataUnique) {
		String cacheKey = getCacheKey(i18nDataUnique.getCode(), i18nDataUnique.getLanguageTag());
		this.cache.remove(cacheKey);
	}

	@Override
	public Topic topic() {
		return new ChannelTopic(I18nRedisKeyConstants.CHANNEL_I18N_DATA_UPDATED);
	}

}
