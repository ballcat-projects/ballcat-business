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

package org.ballcat.business.i18n.listener;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.i18n.converter.I18nDataConverter;
import org.ballcat.business.i18n.model.entity.I18nData;
import org.ballcat.business.i18n.service.I18nDataService;
import org.ballcat.i18n.I18nMessage;
import org.ballcat.i18n.I18nMessageCreateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * I18nMessage 创建事件的监听者
 *
 * @author hccake
 */
@Component
@RequiredArgsConstructor
public class I18nMessageCreateListener {

	private final I18nDataService i18nDataService;

	/**
	 * 监听 I18nMessageCreateEvent 事件，保存对应的 I18nMessage
	 * @param event the event
	 */
	@EventListener(I18nMessageCreateEvent.class)
	public void saveOnI18nMessageCreateEvent(I18nMessageCreateEvent event) {
		List<I18nMessage> i18nMessages = event.getI18nMessages();
		List<I18nData> list = i18nMessages.stream()
			.map(I18nDataConverter.INSTANCE::messageToPo)
			.collect(Collectors.toList());
		this.i18nDataService.saveBatch(list);
	}

}
