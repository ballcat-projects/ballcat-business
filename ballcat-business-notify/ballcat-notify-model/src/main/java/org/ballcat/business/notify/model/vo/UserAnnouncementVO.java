package org.ballcat.business.notify.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户公告表
 *
 * @author evil0th 2024-1-30 16:04:53
 */
@Data
@Schema(title = "用户公告分页VO")
public class UserAnnouncementVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 公告ID
	 */
	@Schema(title = "ID")
	private Long id;

	/**
	 * 标题
	 */
	@Schema(title = "标题")
	private String title;

	/**
	 * 内容
	 */
	@Schema(title = "内容")
	private String content;

	/**
	 * 状态，已读(1)|未读(0)
	 */
	@Schema(title = "状态，已读(1)|未读(0)")
	private Integer state;

	/**
	 * 创建人ID
	 */
	@Schema(title = "创建人ID")
	private Integer createBy;

	/**
	 * 创建人名称
	 */
	@Schema(title = "创建人名称")
	private String createUsername;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(title = "更新时间")
	private LocalDateTime updateTime;

}