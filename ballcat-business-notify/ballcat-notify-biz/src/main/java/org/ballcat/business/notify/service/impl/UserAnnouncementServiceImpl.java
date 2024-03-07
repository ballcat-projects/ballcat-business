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

package org.ballcat.business.notify.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.ballcat.business.notify.enums.UserAnnouncementStateEnum;
import org.ballcat.business.notify.mapper.UserAnnouncementMapper;
import org.ballcat.business.notify.model.entity.UserAnnouncement;
import org.ballcat.business.notify.model.qo.UserAnnouncementQO;
import org.ballcat.business.notify.model.vo.UserAnnouncementPageVO;
import org.ballcat.business.notify.model.vo.UserAnnouncementVO;
import org.ballcat.business.notify.service.UserAnnouncementService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户公告表
 *
 * @author hccake 2020-12-25 08:04:53
 */
@Service
public class UserAnnouncementServiceImpl extends ExtendServiceImpl<UserAnnouncementMapper, UserAnnouncement>
		implements UserAnnouncementService {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<UserAnnouncementVO> 分页数据
	 */
	@Override
	public PageResult<UserAnnouncementPageVO> queryPage(PageParam pageParam, UserAnnouncementQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 获取用户拉取过的发布中，且满足失效时间的公告信息
	 * @param userId 用户id
	 * @return List<Announcement>
	 */
	@Override
	public List<UserAnnouncementVO> listActiveAnnouncements(Long userId) {
		return baseMapper.listUserAnnouncements(userId);
	}

	/**
	 * 根据用户ID和公告id初始化一个新的用户公告关联对象
	 * @param userId 用户ID
	 * @param announcementId 公告ID
	 * @return UserAnnouncement
	 */
	@Override
	public UserAnnouncement prodUserAnnouncement(Long userId, Long announcementId) {
		UserAnnouncement userAnnouncement = new UserAnnouncement();
		userAnnouncement.setUserId(userId);
		userAnnouncement.setAnnouncementId(announcementId);
		userAnnouncement.setCreateTime(LocalDateTime.now());
		userAnnouncement.setState(UserAnnouncementStateEnum.UNREAD.getValue());
		return userAnnouncement;
	}

	/**
	 * 对用户公告进行已读标记
	 * @param userId 用户id
	 * @param announcementId 公告id
	 */
	@Override
	public void readAnnouncement(Long userId, Long announcementId) {
		baseMapper.updateToReadState(userId, announcementId);
	}

}
