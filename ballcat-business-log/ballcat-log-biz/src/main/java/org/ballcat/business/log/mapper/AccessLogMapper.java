package org.ballcat.business.log.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.ballcat.business.log.converter.AccessLogConverter;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.business.log.model.entity.AccessLog;
import org.ballcat.business.log.model.qo.AccessLogQO;
import org.ballcat.business.log.model.vo.AccessLogPageVO;
import org.ballcat.mybatisplus.conditions.query.LambdaQueryWrapperX;
import org.ballcat.mybatisplus.mapper.ExtendMapper;
import org.ballcat.mybatisplus.toolkit.WrappersX;

/**
 * 后台访问日志
 *
 * @author hccake 2019-10-16 16:09:25
 */
public interface AccessLogMapper extends ExtendMapper<AccessLog> {

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param qo 查询对象
	 * @return 分页结果数据 PageResult
	 */
	default PageResult<AccessLogPageVO> queryPage(PageParam pageParam, AccessLogQO qo) {
		IPage<AccessLog> page = this.prodPage(pageParam);
		LambdaQueryWrapperX<AccessLog> wrapperX = WrappersX.lambdaQueryX(AccessLog.class)
			.eqIfPresent(AccessLog::getUserId, qo.getUserId())
			.eqIfPresent(AccessLog::getTraceId, qo.getTraceId())
			.eqIfPresent(AccessLog::getMatchingPattern, qo.getMatchingPattern())
			.eqIfPresent(AccessLog::getRequestUri, qo.getRequestUri())
			.eqIfPresent(AccessLog::getResponseStatus, qo.getResponseStatus())
			.eqIfPresent(AccessLog::getClientIp, qo.getClientIp())
			.gtIfPresent(AccessLog::getCreateTime, qo.getStartTime())
			.ltIfPresent(AccessLog::getCreateTime, qo.getEndTime());
		this.selectPage(page, wrapperX);
		IPage<AccessLogPageVO> voPage = page.convert(AccessLogConverter.INSTANCE::poToPageVo);
		return new PageResult<>(voPage.getRecords(), voPage.getTotal());
	}

}
