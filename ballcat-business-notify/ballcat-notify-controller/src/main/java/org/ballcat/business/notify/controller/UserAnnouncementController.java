package org.ballcat.business.notify.controller;

import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.R;
import org.ballcat.security.annotation.Authorize;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.ballcat.business.notify.model.qo.UserAnnouncementQO;
import org.ballcat.business.notify.model.vo.UserAnnouncementPageVO;
import org.ballcat.business.notify.service.UserAnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户公告表
 *
 * @author hccake 2020-12-25 08:04:53
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/user-announcement")
@Tag(name = "用户公告表管理")
public class UserAnnouncementController {

	private final UserAnnouncementService userAnnouncementService;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param userAnnouncementQO 用户公告表查询对象
	 * @return R 通用返回体
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('notify:userannouncement:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public R<PageResult<UserAnnouncementPageVO>> getUserAnnouncementPage(@Validated PageParam pageParam,
			UserAnnouncementQO userAnnouncementQO) {
		return R.ok(userAnnouncementService.queryPage(pageParam, userAnnouncementQO));
	}

	@PatchMapping("/read/{announcementId}")
	@Authorize("hasPermission('notify:userannouncement:read')")
	@Operation(summary = "用户公告已读上报", description = "用户公告已读上报")
	public R<Void> readAnnouncement(@PathVariable("announcementId") Long announcementId) {
		Long userId = principalAttributeAccessor.getUserId();
		userAnnouncementService.readAnnouncement(userId, announcementId);
		return R.ok();
	}

}