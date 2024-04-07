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
import java.util.stream.Collectors;

import org.ballcat.business.notify.enums.NotifyChannelEnum;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.system.model.entity.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 短信通知发布
 *
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Component
public class SmsNotifyPusher implements NotifyPusher {

	/**
	 * 当前发布者对应的接收方式
	 * @return 推送方式
	 * @see NotifyChannelEnum
	 */
	@Override
	public Integer notifyChannel() {
		return NotifyChannelEnum.SMS.getValue();
	}

	@Override
	public void push(NotifyInfo notifyInfo, List<SysUser> userList) {
		List<String> phoneList = userList.stream()
			.map(SysUser::getPhoneNumber)
			.filter(StringUtils::hasText)
			.collect(Collectors.toList());
		// 短信文本去除 html 标签
		// String content = HtmlUtils.toText(notifyInfo.getContent());
		// TODO 对接短信发送平台
		System.out.println("短信推送");
	}

}
