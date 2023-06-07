package org.ballcat.business.log.service;

import org.ballcat.business.log.model.entity.LoginLog;
import org.ballcat.business.log.model.qo.LoginLogQO;
import org.ballcat.business.log.model.vo.LoginLogPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.service.ExtendService;

/**
 * 登录日志
 *
 * @author hccake 2020-09-16 20:21:10
 */
public interface LoginLogService extends ExtendService<LoginLog> {

	/**
	 * 根据QueryObject查询分页数据
	 * @param page 分页参数
	 * @param qo 查询参数对象
	 * @return PageResult<LoginLogVO> 分页数据
	 */
	PageResult<LoginLogPageVO> queryPage(PageParam page, LoginLogQO qo);

}