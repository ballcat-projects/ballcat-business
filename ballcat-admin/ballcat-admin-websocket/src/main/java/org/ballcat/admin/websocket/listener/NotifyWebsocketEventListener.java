package org.ballcat.admin.websocket.listener;

import org.ballcat.common.util.JsonUtils;
import org.ballcat.websocket.distribute.MessageDO;
import org.ballcat.websocket.distribute.MessageDistributor;
import org.ballcat.admin.websocket.message.AnnouncementCloseMessage;
import org.ballcat.business.notify.event.AnnouncementCloseEvent;
import org.ballcat.business.notify.event.StationNotifyPushEvent;
import org.ballcat.business.notify.handler.NotifyInfoDelegateHandler;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @author Hccake 2021/1/5
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class NotifyWebsocketEventListener {

	private final MessageDistributor messageDistributor;

	private final NotifyInfoDelegateHandler<? super NotifyInfo> notifyInfoDelegateHandler;

	/**
	 * 公告关闭事件监听
	 * @param event the AnnouncementCloseEvent
	 */
	@Async
	@EventListener(AnnouncementCloseEvent.class)
	public void onAnnouncementCloseEvent(AnnouncementCloseEvent event) {
		// 构建公告关闭的消息体
		AnnouncementCloseMessage message = new AnnouncementCloseMessage();
		message.setId(event.getId());
		String msg = JsonUtils.toJson(message);

		// 广播公告关闭信息
		MessageDO messageDO = new MessageDO().setMessageText(msg).setNeedBroadcast(true);
		messageDistributor.distribute(messageDO);
	}

	/**
	 * 站内通知推送事件
	 * @param event the StationNotifyPushEvent
	 */
	@Async
	@EventListener(StationNotifyPushEvent.class)
	public void onAnnouncementPublishEvent(StationNotifyPushEvent event) {
		NotifyInfo notifyInfo = event.getNotifyInfo();
		List<SysUser> userList = event.getUserList();
		notifyInfoDelegateHandler.handle(userList, notifyInfo);
	}

}
