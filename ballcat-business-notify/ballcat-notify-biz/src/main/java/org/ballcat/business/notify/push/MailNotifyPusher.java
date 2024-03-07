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

package org.ballcat.business.notify.push;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.ballcat.business.notify.enums.NotifyChannelEnum;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import org.ballcat.mail.model.MailDetails;
import org.ballcat.mail.sender.MailSender;
import org.springframework.util.StringUtils;

/**
 * 通知邮件发布
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@RequiredArgsConstructor
public class MailNotifyPusher implements NotifyPusher {

	private final MailSender mailSender;

	/**
	 * 当前发布者的推送方式
	 * @return 推送方式
	 * @see NotifyChannelEnum
	 */
	@Override
	public Integer notifyChannel() {
		return NotifyChannelEnum.MAIL.getValue();
	}

	@Override
	public void push(NotifyInfo notifyInfo, List<SysUser> userList) {
		String[] emails = userList.stream().map(SysUser::getEmail).filter(StringUtils::hasText).toArray(String[]::new);

		// 密送群发，不展示其他收件人
		MailDetails mailDetails = new MailDetails();
		mailDetails.setShowHtml(true);
		mailDetails.setSubject(notifyInfo.getTitle());
		mailDetails.setContent(notifyInfo.getContent());
		mailDetails.setBcc(emails);
		this.mailSender.sendMail(mailDetails);
	}

}
