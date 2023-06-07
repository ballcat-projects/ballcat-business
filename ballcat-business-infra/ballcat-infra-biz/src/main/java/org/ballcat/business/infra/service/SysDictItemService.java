package org.ballcat.business.infra.service;

import org.ballcat.business.infra.model.entity.SysDictItem;
import org.ballcat.business.infra.model.vo.SysDictItemPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.ExtendService;

import java.util.List;

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
