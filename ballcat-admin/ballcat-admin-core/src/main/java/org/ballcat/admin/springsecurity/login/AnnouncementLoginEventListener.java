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

package org.ballcat.admin.springsecurity.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.notify.enums.NotifyChannelEnum;
import org.ballcat.business.notify.model.entity.Announcement;
import org.ballcat.business.notify.model.entity.UserAnnouncement;
import org.ballcat.business.notify.recipient.RecipientHandler;
import org.ballcat.business.notify.service.AnnouncementService;
import org.ballcat.business.notify.service.UserAnnouncementService;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.springsecurity.userdetails.User;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
 * @author Hccake 2020/12/23
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class AnnouncementLoginEventListener {

	private final AnnouncementService announcementService;

	private final RecipientHandler recipientHandler;

	private final UserAnnouncementService userAnnouncementService;

	/**
	 * 登录成功时监听 用户未读公告生成
	 * @param event 登录成功 event
	 */
	@EventListener(AuthenticationSuccessEvent.class)
	public void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {

		AbstractAuthenticationToken source = (AbstractAuthenticationToken) event.getSource();
		Object details = source.getDetails();
		if (!(details instanceof HashMap)) {
			return;
		}
		// https://github.com/spring-projects-experimental/spring-authorization-server
		if ("password".equals(((HashMap) details).get("grant_type"))) {
			User user = (User) source.getPrincipal();
			SysUser sysUser = getSysUser(user);

			// 获取当前用户未拉取过的公告信息
			Long userId = sysUser.getUserId();
			List<Announcement> announcements = this.announcementService.listUnPulled(userId);
			// 获取当前用户的各个过滤属性
			Map<Integer, Object> filterAttrs = this.recipientHandler.getFilterAttrs(sysUser);
			// 获取符合当前用户条件的，且接收类型包含站内的公告，保存其关联关系
			List<UserAnnouncement> userAnnouncements = announcements.stream()
				.filter(x -> x.getReceiveMode().contains(NotifyChannelEnum.STATION.getValue()))
				.filter(x -> filterMatched(x, filterAttrs))
				.map(Announcement::getId)
				.map(id -> this.userAnnouncementService.prodUserAnnouncement(userId, id))
				.collect(Collectors.toList());
			try {
				this.userAnnouncementService.saveBatch(userAnnouncements);
			}
			catch (Exception exception) {
				log.error("用户公告保存失败：[{}]", userAnnouncements, exception);
			}
		}
	}

	private SysUser getSysUser(User user) {
		SysUser sysUser = new SysUser();
		sysUser.setUserId(user.getUserId());
		sysUser.setUsername(user.getUsername());
		sysUser.setNickname(user.getNickname());
		sysUser.setAvatar(user.getAvatar());
		sysUser.setOrganizationId(user.getOrganizationId());
		sysUser.setType(user.getType());
		return sysUser;
	}

	private boolean filterMatched(Announcement announ, Map<Integer, Object> filterAttrs) {
		Integer type = announ.getRecipientFilterType();
		return this.recipientHandler.match(type, filterAttrs.get(type), announ.getRecipientFilterCondition());
	}

}
