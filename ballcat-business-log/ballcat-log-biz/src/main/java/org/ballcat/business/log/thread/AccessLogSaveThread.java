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

package org.ballcat.business.log.thread;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.log.model.entity.AccessLog;
import org.ballcat.business.log.service.AccessLogService;
import org.ballcat.common.core.thread.AbstractBlockingQueueThread;

/**
 * @author Hccake 2019/10/16 15:30
 */
@Slf4j
@RequiredArgsConstructor
public class AccessLogSaveThread extends AbstractBlockingQueueThread<AccessLog> {

	private final AccessLogService accessLogService;

	/**
	 * 线程启动时的日志打印
	 */
	@Override
	public void init() {
		log.info("后台访问日志存储线程已启动===");
	}

	/**
	 * 错误日志打印
	 * @param e 错误堆栈
	 * @param list 后台访问日志列表
	 */
	@Override
	public void error(Throwable e, List<AccessLog> list) {
		log.error("后台访问日志记录异常, [msg]:{}, [data]:{}", e.getMessage(), list);
	}

	/**
	 * 数据保存
	 * @param list 后台访问日志列表
	 */
	@Override
	public void process(List<AccessLog> list) throws Exception {
		this.accessLogService.saveBatch(list);
	}

}
