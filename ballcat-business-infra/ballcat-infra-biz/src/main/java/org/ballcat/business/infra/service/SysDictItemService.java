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

import java.util.List;

import org.ballcat.business.infra.model.entity.SysDictItem;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * 字典项
 *
 * @author hccake 2020-03-26 18:40:20
 */
public interface SysDictItemService extends ExtendService<SysDictItem> {

	/**
	 * 根据QueryObject查询分页数据
	 * @param pageParam 分页参数
	 * @param dictCode 查询参数对象
	 * @return 分页数据
	 */
	PageResult<SysDictItemPageVO> queryPage(PageParam pageParam, String dictCode);

	/**
	 * 根据Code查询对应字典项数据
	 * @param dictCode 字典标识
	 * @return 该字典对应的字典项集合
	 */
	List<SysDictItem> listByDictCode(String dictCode);

	/**
	 * 根据字典标识删除对应字典项
	 * @param dictCode 字典标识
	 * @return 是否删除成功
	 */
	boolean removeByDictCode(String dictCode);

	/**
	 * 根据字典标识判断是否存在对应字典项
	 * @param dictCode 字典标识
	 * @return boolean 存在返回 true
	 */
	boolean exist(String dictCode);

}
