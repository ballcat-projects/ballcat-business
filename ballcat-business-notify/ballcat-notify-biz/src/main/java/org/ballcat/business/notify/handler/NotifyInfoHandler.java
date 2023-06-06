package org.ballcat.business.notify.handler;

import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;

import java.util.List;

/**
 * event消息处理接口
 *
 * @author huyuanzhi
 */
public interface NotifyInfoHandler<T extends NotifyInfo> {

	/**
	 * 处理消息
	 * @param userList 发送用户列表
	 * @param info 消息
	 */
	void handle(List<SysUser> userList, T info);

	/**
	 * 获取消息类型
	 * @return 消息类型
	 */
	Class<T> getNotifyClass();

}
