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

package org.ballcat.business.notify.service;

import java.util.List;

import org.ballcat.business.notify.model.dto.AnnouncementDTO;
import org.ballcat.business.notify.model.entity.Announcement;
import org.ballcat.business.notify.model.qo.AnnouncementQO;
import org.ballcat.business.notify.model.vo.AnnouncementPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.ExtendService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公告信息
 *
 * @author hccake 2020-12-15 17:01:15
 */
public interface AnnouncementService extends ExtendService<Announcement> {

	/**
	 * 根据QueryObject查询分页数据
	 * @param page 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<AnnouncementVO> 分页数据
	 */
	PageResult<AnnouncementPageVO> queryPage(PageParam page, AnnouncementQO qo);

	/**
	 * 创建公告
	 * @param announcementDTO 公告信息
	 * @return boolean
	 */
	boolean addAnnouncement(AnnouncementDTO announcementDTO);

	/**
	 * 更新公告信息
	 * @param announcementDTO announcementDTO
	 * @return boolean
	 */
	boolean updateAnnouncement(AnnouncementDTO announcementDTO);

	/**
	 * 发布公告信息
	 * @param announcementId 公告ID
	 * @return boolean
	 */
	boolean publish(Long announcementId);

	/**
	 * 关闭公告信息
	 * @param announcementId 公告ID
	 * @return boolean
	 */
	boolean close(Long announcementId);

	/**
	 * 批量上传公告图片
	 * @param files 图片文件
	 * @return 上传后的图片相对路径集合
	 */
	List<String> uploadImages(List<MultipartFile> files);

	/**
	 * 当前用户未拉取过的发布中，且满足失效时间的公告信息
	 * @param userId 用户id
	 * @return List<Announcement>
	 */
	List<Announcement> listUnPulled(Long userId);

}
