package org.ballcat.business.notify.mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Param;
import org.ballcat.business.notify.converter.UserAnnouncementConverter;
import org.ballcat.business.notify.enums.UserAnnouncementStateEnum;
import org.ballcat.business.notify.model.entity.UserAnnouncement;
import org.ballcat.business.notify.model.qo.UserAnnouncementQO;
import org.ballcat.business.notify.model.vo.UserAnnouncementPageVO;
import org.ballcat.business.notify.model.vo.UserAnnouncementVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.conditions.query.LambdaQueryWrapperX;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * 用户公告表
 *
 * @author hccake 2020-12-25 08:04:53
 */
public interface UserAnnouncementMapper extends ExtendMapper<UserAnnouncement> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询对象
	 * @return 分页结果数据 PageResult
	 */
	default PageResult<UserAnnouncementPageVO> queryPage(PageParam pageParam, UserAnnouncementQO qo) {
		IPage<UserAnnouncement> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<UserAnnouncement> wrapperX = WrappersX.lambdaAliasQueryX(UserAnnouncement.class)
			.eqIfPresent(UserAnnouncement::getId, qo.getId());
		this.selectPage(page, wrapperX);
		IPage<UserAnnouncementPageVO> voPage = page.convert(UserAnnouncementConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

	/**
	 * 更新用户公共信息至已读状态
	 * @param userId 用户ID
	 * @param announcementId 公告ID
	 */
	default void updateToReadState(Long userId, Long announcementId) {
		LambdaUpdateWrapper<UserAnnouncement> wrapper = Wrappers.<UserAnnouncement>lambdaUpdate()
			.set(UserAnnouncement::getState, UserAnnouncementStateEnum.READ.getValue())
			.set(UserAnnouncement::getReadTime, LocalDateTime.now())
			.eq(UserAnnouncement::getAnnouncementId, announcementId)
			.eq(UserAnnouncement::getUserId, userId);
		this.update(null, wrapper);
	}

	/**
	 * 根据参数获取当前用户拉取过的有效的公告信息
	 * @param userId 用户ID
	 * @return 公告信息列表
	 */
	List<UserAnnouncementVO> listUserAnnouncements(@Param("userId") Long userId);

}