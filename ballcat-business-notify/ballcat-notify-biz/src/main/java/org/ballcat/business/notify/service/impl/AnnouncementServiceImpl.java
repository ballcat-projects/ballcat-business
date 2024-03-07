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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.infra.service.FileService;
import org.ballcat.business.notify.converter.AnnouncementConverter;
import org.ballcat.business.notify.converter.NotifyInfoConverter;
import org.ballcat.business.notify.enums.AnnouncementStatusEnum;
import org.ballcat.business.notify.event.AnnouncementCloseEvent;
import org.ballcat.business.notify.event.NotifyPublishEvent;
import org.ballcat.business.notify.mapper.AnnouncementMapper;
import org.ballcat.business.notify.model.domain.NotifyInfo;
import org.ballcat.business.notify.model.dto.AnnouncementDTO;
import org.ballcat.business.notify.model.entity.Announcement;
import org.ballcat.business.notify.model.qo.AnnouncementQO;
import org.ballcat.business.notify.model.vo.AnnouncementPageVO;
import org.ballcat.business.notify.service.AnnouncementService;
import org.ballcat.common.constant.Symbol;
import org.ballcat.common.core.constant.enums.BooleanEnum;
import org.ballcat.common.core.exception.BusinessException;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.BaseResultCode;
import org.ballcat.common.model.result.SystemResultCode;
import org.ballcat.common.util.FileUtils;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公告信息
 *
 * @author hccake 2020-12-15 17:01:15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl extends ExtendServiceImpl<AnnouncementMapper, Announcement>
		implements AnnouncementService {

	private final ApplicationEventPublisher publisher;

	private final FileService fileService;

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<AnnouncementVO> 分页数据
	 */
	@Override
	public PageResult<AnnouncementPageVO> queryPage(PageParam pageParam, AnnouncementQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 创建公告
	 * @param announcementDTO 公告信息
	 * @return boolean
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addAnnouncement(AnnouncementDTO announcementDTO) {
		Announcement announcement = AnnouncementConverter.INSTANCE.dtoToPo(announcementDTO);
		announcement.setId(null);
		int flag = baseMapper.insert(announcement);
		boolean inserted = SqlHelper.retBool(flag);
		// 公告发布事件
		boolean isPublishStatus = announcement.getStatus() == AnnouncementStatusEnum.ENABLED.getValue();
		if (inserted && isPublishStatus) {
			this.onAnnouncementPublish(announcement);
		}
		return inserted;
	}

	/**
	 * 更新公告信息
	 * @param announcementDTO announcementDTO
	 * @return boolean
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateAnnouncement(AnnouncementDTO announcementDTO) {
		Announcement oldAnnouncement = baseMapper.selectById(announcementDTO.getId());
		if (oldAnnouncement.getStatus() != AnnouncementStatusEnum.UNPUBLISHED.getValue()) {
			throw new BusinessException(SystemResultCode.BAD_REQUEST.getCode(), "不允许修改已经发布过的公告！");
		}

		Announcement announcement = AnnouncementConverter.INSTANCE.dtoToPo(announcementDTO);
		// 不允许修改为《发布中》以外的状态
		boolean isPublishStatus = announcement.getStatus() == AnnouncementStatusEnum.ENABLED.getValue();
		if (!isPublishStatus) {
			announcement.setStatus(null);
		}
		// 保证当前状态未被修改过
		boolean isUpdated = baseMapper.updateIfUnpublished(announcement);
		// 公告发布事件
		if (isUpdated && isPublishStatus) {
			this.onAnnouncementPublish(announcement);
		}
		return isUpdated;
	}

	/**
	 * 发布公告信息
	 * @param announcementId 公告ID
	 * @return boolean
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean publish(Long announcementId) {
		Announcement announcement = baseMapper.selectById(announcementId);
		if (announcement.getStatus() != AnnouncementStatusEnum.UNPUBLISHED.getValue()) {
			throw new BusinessException(SystemResultCode.BAD_REQUEST.getCode(), "不允许修改已经发布过的公告！");
		}
		if (BooleanEnum.FALSE.intValue().equals(announcement.getImmortal())
				&& LocalDateTime.now().isAfter(announcement.getDeadline())) {
			throw new BusinessException(SystemResultCode.BAD_REQUEST.getCode(), "公告失效时间必须迟于当前时间！");
		}

		// 更新公共至发布状态
		Announcement entity = new Announcement();
		entity.setId(announcementId);
		entity.setStatus(AnnouncementStatusEnum.ENABLED.getValue());
		boolean isUpdated = baseMapper.updateIfUnpublished(entity);
		if (isUpdated) {
			announcement.setStatus(AnnouncementStatusEnum.ENABLED.getValue());
			this.onAnnouncementPublish(announcement);
		}
		return isUpdated;
	}

	/**
	 * 关闭公告信息
	 * @param announcementId 公告ID
	 * @return boolean
	 */
	@Override
	public boolean close(Long announcementId) {
		Announcement announcement = new Announcement();
		announcement.setId(announcementId);
		announcement.setStatus(AnnouncementStatusEnum.DISABLED.getValue());
		int flag = baseMapper.updateById(announcement);
		boolean isUpdated = SqlHelper.retBool(flag);
		if (isUpdated) {
			this.publisher.publishEvent(new AnnouncementCloseEvent(announcementId));
		}
		return isUpdated;
	}

	/**
	 * 批量上传公告图片
	 * @param files 图片文件
	 * @return 上传后的图片相对路径集合
	 */
	@Override
	public List<String> uploadImages(List<MultipartFile> files) {
		List<String> objectNames = new ArrayList<>();
		for (MultipartFile file : files) {
			String objectName = "announcement/" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
					+ Symbol.SLASH + ObjectId.get() + Symbol.DOT + FileUtils.getExtension(file.getOriginalFilename());
			try {
				objectName = this.fileService.upload(file.getInputStream(), objectName, file.getSize());
				objectNames.add(objectName);
			}
			catch (IOException e) {
				// TODO 删除无效文件
				throw new BusinessException(BaseResultCode.FILE_UPLOAD_ERROR.getCode(), "图片上传失败！", e);
			}
		}
		return objectNames;
	}

	/**
	 * 当前用户未拉取过的发布中，且满足失效时间的公告信息
	 * @return List<Announcement>
	 */
	@Override
	public List<Announcement> listUnPulled(Long userId) {
		return baseMapper.listUnPulledUserAnnouncements(userId);
	}

	/**
	 * 公告发布事件
	 * @param announcement 公告信息
	 */
	private void onAnnouncementPublish(Announcement announcement) {
		NotifyInfo notifyInfo = NotifyInfoConverter.INSTANCE.fromAnnouncement(announcement);
		this.publisher.publishEvent(new NotifyPublishEvent(notifyInfo));
	}

}
