package com.system.controller;

import javax.inject.Inject;

import javacommon.base.BaseBsController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.model.SysDepartmentInfoSggl;
import com.system.service.SysDepartmentInfoSgglManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:34
 *
 */
@RestController
@RequestMapping("/sysDepartmentInfoSggl")
public class SysDepartmentInfoSgglController extends BaseBsController<SysDepartmentInfoSggl> {

	@Inject
	private SysDepartmentInfoSgglManager sysDepartmentInfoSgglManager;
	@Override
	protected BaseManager<SysDepartmentInfoSggl> getManager() {
		return sysDepartmentInfoSgglManager;
	}

}