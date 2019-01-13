package com.system.controller;

import javax.inject.Inject;

import javacommon.base.BaseBsController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.model.SysDepartmentInfoSgyyz;
import com.system.service.SysDepartmentInfoSgyyzManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:56
 *
 */
@RestController
@RequestMapping("/sysDepartmentInfoSgyyz")
public class SysDepartmentInfoSgyyzController extends BaseBsController<SysDepartmentInfoSgyyz> {

	@Inject
	private SysDepartmentInfoSgyyzManager sysDepartmentInfoSgyyzManager;
	@Override
	protected BaseManager<SysDepartmentInfoSgyyz> getManager() {
		return sysDepartmentInfoSgyyzManager;
	}

}