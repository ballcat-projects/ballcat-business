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

package org.ballcat.business.log.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.ballcat.business.log.converter.OperationLogConverter;
import org.ballcat.business.log.model.entity.OperationLog;
import org.ballcat.business.log.model.qo.OperationLogQO;
import org.ballcat.business.log.model.vo.OperationLogPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.conditions.query.LambdaQueryWrapperX;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * 操作日志
 *
 * @author hccake
 * @date 2019-10-15 20:42:32
 */
public interface OperationLogMapper extends ExtendMapper<OperationLog> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询对象
	 * @return 分页结果数据 PageResult
	 */
	default PageResult<OperationLogPageVO> queryPage(PageParam pageParam, OperationLogQO qo) {
		IPage<OperationLog> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<OperationLog> wrapperX = WrappersX.lambdaQueryX(OperationLog.class)
			.eqIfPresent(OperationLog::getOperator, qo.getUserId())
			.eqIfPresent(OperationLog::getTraceId, qo.getTraceId())
			.eqIfPresent(OperationLog::getUri, qo.getUri())
			.eqIfPresent(OperationLog::getIp, qo.getIp())
			.eqIfPresent(OperationLog::getStatus, qo.getStatus())
			.eqIfPresent(OperationLog::getType, qo.getType())
			.likeIfPresent(OperationLog::getMsg, qo.getMsg())
			.gtIfPresent(OperationLog::getCreateTime, qo.getStartTime())
			.ltIfPresent(OperationLog::getCreateTime, qo.getEndTime());
		this.selectPage(page, wrapperX);
		IPage<OperationLogPageVO> voPage = page.convert(OperationLogConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

}
