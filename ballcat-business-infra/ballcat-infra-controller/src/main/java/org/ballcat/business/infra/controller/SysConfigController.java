package org.ballcat.business.infra.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ballcat.business.infra.model.entity.SysConfig;
import org.ballcat.business.infra.model.qo.SysConfigQO;
import org.ballcat.business.infra.model.vo.SysConfigPageVO;
import org.ballcat.business.infra.service.SysConfigService;
import org.ballcat.common.model.domain.PageParam;
import org.ballcat.common.model.domain.PageResult;
import org.ballcat.common.model.result.R;
import org.ballcat.log.operation.annotation.CreateOperationLogging;
import org.ballcat.log.operation.annotation.DeleteOperationLogging;
import org.ballcat.log.operation.annotation.UpdateOperationLogging;
import org.ballcat.security.annotation.Authorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置
 *
 * @author ballcat code generator 2019-10-14 17:42:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/config")
@Tag(name = "系统配置")
public class SysConfigController {

	private final SysConfigService sysConfigService;

	/**
	 * 分页查询
	 * @param pageParam 分页参数
	 * @param sysConfigQO 系统配置
	 * @return R<PageResult<SysConfigVO>>
	 */
	@GetMapping("/page")
	@Authorize("hasPermission('system:config:read')")
	@Operation(summary = "分页查询", description = "分页查询")
	public R<PageResult<SysConfigPageVO>> getSysConfigPage(@Validated PageParam pageParam, SysConfigQO sysConfigQO) {
		return R.ok(sysConfigService.queryPage(pageParam, sysConfigQO));
	}

	/**
	 * 新增系统配置
	 * @param sysConfig 系统配置
	 * @return R
	 */
	@CreateOperationLogging(msg = "新增系统配置")
	@PostMapping
	@Authorize("hasPermission('system:config:add')")
	@Operation(summary = "新增系统配置", description = "新增系统配置")
	public R<Boolean> save(@RequestBody SysConfig sysConfig) {
		return R.ok(sysConfigService.save(sysConfig));
	}

	/**
	 * 修改系统配置
	 * @param sysConfig 系统配置
	 * @return R
	 */
	@UpdateOperationLogging(msg = "修改系统配置")
	@PutMapping
	@Authorize("hasPermission('system:config:edit')")
	@Operation(summary = "修改系统配置")
	public R<Boolean> updateById(@RequestBody SysConfig sysConfig) {
		return R.ok(sysConfigService.updateByKey(sysConfig));
	}

	/**
	 * 删除系统配置
	 * @param confKey confKey
	 * @return R
	 */
	@DeleteOperationLogging(msg = "删除系统配置")
	@DeleteMapping
	@Authorize("hasPermission('system:config:del')")
	@Operation(summary = "删除系统配置")
	public R<Boolean> removeById(@RequestParam("confKey") String confKey) {
		return R.ok(sysConfigService.removeByKey(confKey));
	}

}
