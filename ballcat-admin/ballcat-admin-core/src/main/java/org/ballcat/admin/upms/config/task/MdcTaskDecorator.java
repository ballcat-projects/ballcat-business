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

package org.ballcat.admin.upms.config.task;

import java.util.Map;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.util.ObjectUtils;

/**
 * 使async异步任务支持traceId
 *
 * @author huyuanzhi 2021-11-06 23:14:27
 */
public class MdcTaskDecorator implements TaskDecorator {

	@Override
	public Runnable decorate(Runnable runnable) {
		final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
		return () -> {
			if (!ObjectUtils.isEmpty(copyOfContextMap)) {
				// 现在：@Async线程上下文！ 恢复Web线程上下文的MDC数据
				MDC.setContextMap(copyOfContextMap);
			}

			try {
				runnable.run();
			}
			finally {
				MDC.clear();
			}
		};
	}

}
