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

package org.ballcat.admin.springsecurity;

import java.util.Map;

import org.ballcat.business.system.model.dto.UserInfoDTO;

/**
 * 用户附属属性协调
 *
 * @author author.zero
 * @version 1.0 2021/11/16 20:02
 */
public interface UserInfoCoordinator {

	/**
	 * 对于不同类型的用户，可能在业务上需要获取到不同的用户属性 实现此接口，进行用户属性的增强
	 * @param userInfoDTO 系统用户信息
	 * @param attribute 用户属性，默认添加了 roles 和 permissions 属性
	 * @return attribute
	 */
	Map<String, Object> coordinateAttribute(UserInfoDTO userInfoDTO, Map<String, Object> attribute);

}
