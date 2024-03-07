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

package org.ballcat.business.log.handler;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.service.OperationLogService;
import org.ballcat.common.core.constant.MDCConstants;
import org.ballcat.common.util.IpUtils;
import org.ballcat.common.util.JsonUtils;
import org.ballcat.log.operation.annotation.OperationLogging;
import org.ballcat.log.operation.enums.LogStatusEnum;
import org.ballcat.log.operation.handler.AbstractOperationLogHandler;
import org.ballcat.security.core.PrincipalAttributeAccessor;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Hccake 2020/5/25 20:38
 */
@RequiredArgsConstructor
public class CustomOperationLogHandler extends AbstractOperationLogHandler<OperationLog> {

	private final OperationLogService operationLogService;

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	@Override
	public OperationLog buildLog(OperationLogging operationLogging, ProceedingJoinPoint joinPoint) {
		// 获取 Request
		HttpServletRequest request = ((ServletRequestAttributes) Objects
			.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		// @formatter:off
		OperationLog operationLog = new OperationLog()
				.setCreateTime(LocalDateTime.now())
				.setIp(IpUtils.getIpAddr(request))
				.setMethod(request.getMethod())
				.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT))
				.setUri(request.getRequestURI())
				.setType(operationLogging.type())
				.setMsg(operationLogging.msg())
				.setTraceId(MDC.get(MDCConstants.TRACE_ID_KEY));
		// @formatter:on

		// 请求参数
		if (operationLogging.recordParams()) {
			operationLog.setParams(getParams(joinPoint));
		}

		// 操作用户
		operationLog.setOperator(this.principalAttributeAccessor.getUsername());

		return operationLog;
	}

	@Override
	public OperationLog recordExecutionInfo(OperationLog operationLog, ProceedingJoinPoint joinPoint,
			long executionTime, Throwable throwable, boolean isSaveResult, Object result) {
		// 执行时长
		operationLog.setTime(executionTime);
		// 执行状态
		LogStatusEnum logStatusEnum = throwable == null ? LogStatusEnum.SUCCESS : LogStatusEnum.FAIL;
		operationLog.setStatus(logStatusEnum.getValue());
		// 执行结果
		if (isSaveResult) {
			Optional.ofNullable(result).ifPresent(x -> operationLog.setResult(JsonUtils.toJson(x)));
		}
		return operationLog;
	}

	@Override
	public void handleLog(OperationLog operationLog) {
		// 异步保存
		this.operationLogService.saveAsync(operationLog);
	}

}
