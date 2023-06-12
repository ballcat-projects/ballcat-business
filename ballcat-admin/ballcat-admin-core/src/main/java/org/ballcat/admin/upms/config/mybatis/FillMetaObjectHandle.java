package org.ballcat.admin.upms.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.ballcat.common.core.constant.GlobalConstants;
import org.ballcat.security.core.PrincipalAttributeAccessor;

import java.time.LocalDateTime;

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
		Long userId = principalAttributeAccessor.getUserId();
		if (userId != null) {
			this.strictInsertFill(metaObject, "createBy", Long.class, userId);
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// 修改时间
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		// 修改人
		Long userId = principalAttributeAccessor.getUserId();
		if (userId != null) {
			this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
		}
	}

}
