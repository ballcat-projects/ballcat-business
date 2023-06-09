package org.ballcat.business.log.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.ballcat.business.log.converter.LoginLogConverter;
import org.ballcat.business.log.model.entity.LoginLog;
import org.ballcat.business.log.model.qo.LoginLogQO;
import org.ballcat.business.log.model.vo.LoginLogPageVO;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.mybatisplus.conditions.query.LambdaQueryWrapperX;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * 登录日志
 *
 * @author hccake 2020-09-16 20:21:10
 */
public interface LoginLogMapper extends ExtendMapper<LoginLog> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询对象
	 * @return 分页结果数据 PageResult
	 */
	default PageResult<LoginLogPageVO> queryPage(PageParam pageParam, LoginLogQO qo) {
		IPage<LoginLog> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<LoginLog> wrapperX = WrappersX.lambdaQueryX(LoginLog.class)
			.eqIfPresent(LoginLog::getUsername, qo.getUsername())
			.eqIfPresent(LoginLog::getTraceId, qo.getTraceId())
			.eqIfPresent(LoginLog::getIp, qo.getIp())
			.eqIfPresent(LoginLog::getEventType, qo.getEventType())
			.eqIfPresent(LoginLog::getStatus, qo.getStatus())
			.gtIfPresent(LoginLog::getLoginTime, qo.getStartTime())
			.ltIfPresent(LoginLog::getLoginTime, qo.getEndTime());
		this.selectPage(page, wrapperX);
		IPage<LoginLogPageVO> voPage = page.convert(LoginLogConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

}