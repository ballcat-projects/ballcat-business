package org.ballcat.business.notify.converter;

import org.ballcat.business.notify.model.domain.AnnouncementNotifyInfo;
import org.ballcat.business.notify.model.entity.Announcement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Hccake 2020/12/23
 * @version 1.0
 */
@Mapper
public interface NotifyInfoConverter {

	NotifyInfoConverter INSTANCE = Mappers.getMapper(NotifyInfoConverter.class);

	/**
	 * 公告转通知实体
	 * @param announcement 公告信息
	 * @return 通知信息
	 */
	AnnouncementNotifyInfo fromAnnouncement(Announcement announcement);

}
