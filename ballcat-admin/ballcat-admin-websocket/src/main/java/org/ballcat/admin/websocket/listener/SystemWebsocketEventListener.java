package org.ballcat.admin.websocket.listener;

import org.ballcat.common.util.JsonUtils;
import org.ballcat.websocket.distribute.MessageDO;
import org.ballcat.websocket.distribute.MessageDistributor;
import org.ballcat.admin.websocket.message.DictChangeMessage;
import org.ballcat.business.infra.event.DictChangeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class SystemWebsocketEventListener {

	private final MessageDistributor messageDistributor;

	/**
	 * 字典修改事件监听
	 * @param event the `DictChangeEvent`
	 */
	@Async
	@EventListener(DictChangeEvent.class)
	public void onDictChangeEvent(DictChangeEvent event) {
		// 构建字典修改的消息体
		DictChangeMessage dictChangeMessage = new DictChangeMessage();
		dictChangeMessage.setDictCode(event.getDictCode());
		String msg = JsonUtils.toJson(dictChangeMessage);

		// 广播修改信息
		MessageDO messageDO = new MessageDO().setMessageText(msg).setNeedBroadcast(true);
		messageDistributor.distribute(messageDO);
	}

}
