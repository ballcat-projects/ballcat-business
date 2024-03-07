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

package org.ballcat.business.log.service.impl;

import org.ballcat.business.log.mapper.OperationLogMapper;
import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.model.qo.OperationLogQO;
import org.ballcat.business.log.model.vo.OperationLogPageVO;
import org.ballcat.business.log.service.OperationLogService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.impl.ExtendServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 操作日志
 *
 * @author hccake
 * @date 2019-10-15 20:42:32
 */
@Service
public class OperationLogServiceImpl extends ExtendServiceImpl<OperationLogMapper, OperationLog>
		implements OperationLogService {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<LoginLogVO> 分页数据
	 */
	@Override
	public PageResult<OperationLogPageVO> queryPage(PageParam pageParam, OperationLogQO qo) {
		return baseMapper.queryPage(pageParam, qo);
	}

	/**
	 * 异步保存操作日志
	 * @param operationLog 操作日志
	 */
	@Async
	@Override
	public void saveAsync(OperationLog operationLog) {
		baseMapper.insert(operationLog);
	}

}
