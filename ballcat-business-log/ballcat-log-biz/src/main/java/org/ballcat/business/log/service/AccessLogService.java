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

package org.ballcat.business.log.service;

import org.ballcat.business.log.model.entity.AccessLog;
import org.ballcat.business.log.model.qo.AccessLogQO;
import org.ballcat.business.log.model.vo.AccessLogPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * 后台访问日志
 *
 * @author hccake 2019-10-16 16:09:25
 */
public interface AccessLogService extends ExtendService<AccessLog> {

	/**
	 * 根据QueryObject查询分页数据
	 * @param page 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<LoginLogVO> 分页数据
	 */
	PageResult<AccessLogPageVO> queryPage(PageParam page, AccessLogQO qo);

}
