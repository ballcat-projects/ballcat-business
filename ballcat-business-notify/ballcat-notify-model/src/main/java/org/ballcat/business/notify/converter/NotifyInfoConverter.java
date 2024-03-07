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
