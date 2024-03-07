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

package org.ballcat.business.notify.recipient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.ballcat.business.system.model.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * @author Hccake 2020/12/21
 * @version 1.0
 */
@Slf4j
@Component
public class RecipientHandler {

	private final Map<Integer, RecipientFilter> recipientFilterMap = new LinkedHashMap<>();

	public RecipientHandler(List<RecipientFilter> recipientFilterList) {
		for (RecipientFilter recipientFilter : recipientFilterList) {
			this.recipientFilterMap.put(recipientFilter.filterType(), recipientFilter);
		}
	}

	public List<SysUser> query(Integer filterType, List<String> filterCondition) {
		RecipientFilter recipientFilter = this.recipientFilterMap.get(filterType);
		if (recipientFilter == null) {
			log.error("Unknown recipient filter：[{}]，filterCondition：{}", filterType, filterCondition);
			return new ArrayList<>();
		}
		return recipientFilter.filter(filterCondition);
	}

	/**
	 * 判断当前是否匹配
	 * @param recipientFilterType 筛选类型
	 * @param filterAttr 筛选属性
	 * @param recipientFilterCondition 筛选条件
	 * @return boolean true：匹配
	 */
	public boolean match(Integer recipientFilterType, Object filterAttr, List<String> recipientFilterCondition) {
		RecipientFilter recipientFilter = this.recipientFilterMap.get(recipientFilterType);
		return recipientFilter != null && recipientFilter.match(filterAttr, recipientFilterCondition);
	}

	/**
	 * 获取当前用户的各个筛选器对应的属性
	 * @param sysUser 系统用户
	 * @return 属性Map key：filterType value: attr
	 */
	public Map<Integer, Object> getFilterAttrs(SysUser sysUser) {
		Map<Integer, Object> map = new HashMap<>(this.recipientFilterMap.size());
		for (RecipientFilter recipientFilter : this.recipientFilterMap.values()) {
			Object obj = recipientFilter.getFilterAttr(sysUser);
			map.put(recipientFilter.filterType(), obj);
		}
		return map;
	}

}
