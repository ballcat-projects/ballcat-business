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

package org.ballcat.admin.upms.config.mybatis;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.ballcat.common.core.constant.GlobalConstants;
import org.ballcat.security.core.PrincipalAttributeAccessor;

/**
 * @author Hccake 2019/7/26 14:41
 */
@Slf4j
public class FillMetaObjectHandle implements MetaObjectHandler {

	private final PrincipalAttributeAccessor principalAttributeAccessor;

	public FillMetaObjectHandle(PrincipalAttributeAccessor principalAttributeAccessor) {
		this.principalAttributeAccessor = principalAttributeAccessor;
	}

	@Override
	public void insertFill(MetaObject metaObject) {
		// 逻辑删除标识
		this.strictInsertFill(metaObject, "deleted", Long.class, GlobalConstants.NOT_DELETED_FLAG);
		// 创建时间
		this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
		// 创建人
		Long userId = this.principalAttributeAccessor.getUserId();
		if (userId != null) {
			this.strictInsertFill(metaObject, "createBy", Long.class, userId);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// 修改时间
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		// 修改人
		Long userId = this.principalAttributeAccessor.getUserId();
		if (userId != null) {
			this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
		}
	}

}
