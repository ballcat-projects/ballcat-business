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

package org.ballcat.business.notify.event;

import lombok.Getter;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 通知发布事件
 *
 * @author Hccake 2020/12/17
 * @version 1.0
 */
@Getter
public class NotifyPublishEvent extends ApplicationEvent {

	/**
	 * 通知信息
	 */
	private final NotifyInfo notifyInfo;

	public NotifyPublishEvent(NotifyInfo notifyInfo) {
		super(notifyInfo);
		this.notifyInfo = notifyInfo;
	}

}
