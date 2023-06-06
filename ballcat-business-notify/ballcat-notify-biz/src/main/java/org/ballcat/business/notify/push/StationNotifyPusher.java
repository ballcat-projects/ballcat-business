package org.ballcat.business.notify.push;

import org.ballcat.business.notify.enums.NotifyChannelEnum;
import org.ballcat.business.notify.event.StationNotifyPushEvent;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息通知站内推送
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class StationNotifyPusher implements NotifyPusher {

	private final ApplicationEventPublisher publisher;

	/**
	 * 当前发布者对应的接收方式
	 * @see NotifyChannelEnum
	 * @return 推送方式
	 */
	@Override
	public Integer notifyChannel() {
		return NotifyChannelEnum.STATION.getValue();
	}

	@Override
	public void push(NotifyInfo notifyInfo, List<SysUser> userList) {
		// 发布事件，监听者进行实际的 websocket 推送
		publisher.publishEvent(new StationNotifyPushEvent(notifyInfo, userList));
	}

}
