package org.ballcat.business.log.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访问日志展示对象
 *
 * @author hccake 2019-10-16 16:09:25
 */
@Data
@Schema(title = "访问日志分页展示对象")
public class AccessLogPageVO {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@Schema(title = "编号")
	private Long id;

	/**
	 * 追踪ID
	 */
	@Schema(title = "追踪ID")
	private String traceId;

	/**
	 * 用户ID
	 */
	@Schema(title = "用户ID")
	private Long userId;

	/**
	 * 用户名
	 */
	@Schema(title = "用户名")
	private String username;

	/**
	 * 客户端IP地址
	 */
	@Schema(title = "客户端IP地址")
	private String clientIp;

	/**
	 * 用户代理
	 */
	@Schema(title = "用户代理")
	private String userAgent;

	/**
	 * 请求URI
	 */
	@Schema(title = "请求URI")
	private String requestUri;

	/**
	 * 请求映射地址
	 */
	@Schema(title = "请求映射地址")
	private String matchingPattern;

	/**
	 * 请求方法
	 */
	@Schema(title = "请求方法")
	private String requestMethod;

	/**
	 * 查询参数
	 */
	@Schema(title = "查询参数")
	private String queryString;

	/**
	 * 请求体
	 */
	@Schema(title = "请求体")
	private String requestBody;

	/**
	 * 响应状态码
	 */
	@Schema(title = "响应状态码")
	private Integer responseStatus;

	/**
	 * 响应体
	 */
	@Schema(title = "响应体")
	private String responseBody;

	/**
	 * 错误消息
	 */
	@Schema(title = "错误消息")
	private String errorMessage;

	/**
	 * 执行时长
	 */
	@Schema(title = "执行时长")
	private Long executionTime;

	/**
	 * 创建时间
	 */
	@Schema(title = "创建时间")
	private LocalDateTime createTime;

}
