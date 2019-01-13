package com.system.controller;

import javax.inject.Inject;

import javacommon.base.BaseBsController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.model.SysDepartmentInfoSg;
import com.system.service.SysDepartmentInfoSgManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:06
 *
 */
@RestController
@RequestMapping("/sysDepartmentInfoSg")
public class SysDepartmentInfoSgController extends BaseBsController<SysDepartmentInfoSg> {

	@Inject
	private SysDepartmentInfoSgManager sysDepartmentInfoSgManager;
	@Override
	protected BaseManager<SysDepartmentInfoSg> getManager() {
		return sysDepartmentInfoSgManager;
	}

}