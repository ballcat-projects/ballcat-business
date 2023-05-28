package org.ballcat.business.notify.converter;

import org.ballcat.business.notify.model.dto.AnnouncementDTO;
import org.ballcat.business.notify.model.entity.Announcement;
import org.ballcat.business.notify.model.vo.AnnouncementPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Hccake 2020/12/16
 * @version 1.0
 */
@Mapper
public interface AnnouncementConverter {

	AnnouncementConverter INSTANCE = Mappers.getMapper(AnnouncementConverter.class);

	/**
	 * PO 转 PageVO
	 * @param announcement 公告表
	 * @return AnnouncementPageVO 公告表PageVO
	 */
	AnnouncementPageVO poToPageVo(Announcement announcement);

	/**
	 * AnnouncementDTO 转 Announcement实体
	 * @param dto AnnouncementDTO
	 * @return Announcement
	 */
	@Mapping(target = "updateTime", ignore = true)
	@Mapping(target = "createTime", ignore = true)
	@Mapping(target = "createBy", ignore = true)
	Announcement dtoToPo(AnnouncementDTO dto);

}
