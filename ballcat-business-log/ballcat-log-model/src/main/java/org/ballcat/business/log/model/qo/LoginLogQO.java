package org.ballcat.business.log.model.qo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.ballcat.business.log.enums.LoginEventTypeEnum;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 登录日志 查询对象
 *
 * @author hccake 2020-09-16 20:21:10
 */
@Data
@Schema(title = "登录日志查询对象")
@ParameterObject
public class LoginLogQO {

	private static final long serialVersionUID = 1L;

	/**
	 * 追踪ID
	 */
	@Parameter(description = "追踪ID")
	private String traceId;

	/**
	 * 用户名
	 */
	@Parameter(description = "用户名")
	private String username;

	/**
	 * 操作信息
	 */
	@Parameter(description = "请求IP")
	private String ip;

	/**
	 * 状态
	 */
	@Parameter(description = "状态")
	private Integer status;

	/**
	 * 事件类型 登录/登出
	 *
	 * @see LoginEventTypeEnum
	 */
	@Parameter(description = "事件类型")
	private Integer eventType;

	/**
	 * 登录时间区间（开始时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "开始时间（登录时间区间）")
	private LocalDateTime startTime;

	/**
	 * 登录时间区间（结束时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "结束时间（登录时间区间）")
	private LocalDateTime endTime;

}