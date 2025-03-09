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

import lombok.RequiredArgsConstructor;
import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.service.OperationLogService;
import org.ballcat.log.operation.domain.OperationLogInfo;
import org.ballcat.log.operation.handler.OperationLogHandler;

/**
 * @author Hccake 2020/5/25 20:38
 */
@RequiredArgsConstructor
public class CustomOperationLogHandler implements OperationLogHandler {

	private final OperationLogService operationLogService;

	@Override
	public void handle(OperationLogInfo operationLogInfo) {

		OperationLogInfo.HttpInfo httpInfo = operationLogInfo.getHttpInfo();

		OperationLog operationLog = new OperationLog().setCreateTime(LocalDateTime.now())
			.setIp(httpInfo.getClientIp())
			.setMethod(httpInfo.getRequestMethod())
			.setUserAgent(httpInfo.getUserAgent())
			.setUri(httpInfo.getRequestUri())
			.setType(0) // TODO 添加 type 和 subType
			.setMsg(operationLogInfo.getMessage())
			.setTraceId(operationLogInfo.getTraceId());

		String params = operationLog.getParams();
		operationLog.setParams(params);
		operationLog.setOperator(operationLogInfo.getOperator());

		operationLog.setTime(operationLogInfo.getExecutionTime());
		operationLog.setStatus(operationLogInfo.getStatus());
		operationLog.setResult(operationLogInfo.getMethodResult());

		this.operationLogService.saveAsync(operationLog);
	}

}
