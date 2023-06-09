package org.ballcat.business.log.model.qo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 后台访问日志
 *
 * @author hccake 2019-10-16 16:09:25
 */
@Data
@Schema(title = "后台访问日志查询对象")
@ParameterObject
public class AccessLogQO {

	private static final long serialVersionUID = 1L;

	/**
	 * 追踪ID
	 */
	@Parameter(description = "追踪ID")
	private String traceId;

	/**
	 * 用户ID
	 */
	@Parameter(description = "用户ID")
	private Long userId;

	/**
	 * 客户端IP地址
	 */
	@Schema(title = "客户端IP地址")
	private String clientIp;

	/**
	 * 请求URI
	 */
	@Schema(title = "请求URI")
	private String requestUri;

	/**
	 * 请求映射地址
	 */
	@Parameter(description = "请求映射地址")
	private String matchingPattern;

	/**
	 * 响应状态码
	 */
	@Schema(title = "响应状态码")
	private Integer responseStatus;

	/**
	 * 访问时间区间（开始时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "开始时间（登录时间区间）")
	private LocalDateTime startTime;

	/**
	 * 访问时间区间（结束时间）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Parameter(description = "结束时间（登录时间区间）")
	private LocalDateTime endTime;

}
