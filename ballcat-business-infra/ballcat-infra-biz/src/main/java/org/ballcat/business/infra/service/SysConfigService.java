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

package org.ballcat.business.infra.service;

import org.ballcat.business.infra.model.entity.SysConfig;
import org.ballcat.business.infra.model.qo.SysConfigQO;
import org.ballcat.business.infra.model.vo.SysConfigPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * 系统配置表
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
public interface SysConfigService extends ExtendService<SysConfig> {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param sysConfigQO 查询参数对象
	 * @return 分页数据
	 */
	PageResult<SysConfigPageVO> queryPage(PageParam pageParam, SysConfigQO sysConfigQO);

	/**
	 * 根据配置key获取对应value
	 * @param confKey 配置key
	 * @return confValue
	 */
	String getConfValueByKey(String confKey);

	/**
	 * 根据 confKey 进行更新
	 * @param sysConfig 系统配置
	 * @return 更新是否成功
	 */
	boolean updateByKey(SysConfig sysConfig);

	/**
	 * 根据 confKey 进行删除
	 * @param confKey 配置key
	 * @return 删除是否成功
	 */
	boolean removeByKey(String confKey);

}
