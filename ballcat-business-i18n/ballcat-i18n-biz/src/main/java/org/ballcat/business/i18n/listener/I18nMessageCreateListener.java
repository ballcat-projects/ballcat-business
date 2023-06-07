package org.ballcat.business.i18n.listener;

import org.ballcat.i18n.I18nMessage;
import org.ballcat.i18n.I18nMessageCreateEvent;
import org.ballcat.business.i18n.converter.I18nDataConverter;
import org.ballcat.business.i18n.model.entity.I18nData;
import org.ballcat.business.i18n.service.I18nDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
		i18nDataService.saveBatch(list);
	}

}
