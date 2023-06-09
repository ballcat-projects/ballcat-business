package org.ballcat.business.system.service;

import org.ballcat.business.system.model.entity.SysRole;
import org.ballcat.business.system.model.qo.SysRoleQO;
import org.ballcat.business.system.model.vo.SysRolePageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.domain.SelectData;
import org.ballcat.mybatisplus.service.ExtendService;

import java.util.List;

/**
 * <p>
 * 系统角色服务类
 * </p>
 *
 * @author hccake
 * @since 2020-01-12
 */
public interface SysRoleService extends ExtendService<SysRole> {

	/**
	 * 查询系统角色列表
	 * @param pageParam 分页参数
	 * @param qo 查询参数
	 * @return 分页对象
	 */
	PageResult<SysRolePageVO> queryPage(PageParam pageParam, SysRoleQO qo);

	/**
	 * 角色的选择数据
	 * @return 角色下拉列表数据集合
	 */
	List<SelectData<Void>> listSelectData();

	/**
	 * 是否存在角色code
	 * @param roleCode 角色code
	 * @return boolean 是否存在
	 */
	boolean existsRoleCode(String roleCode);

}
