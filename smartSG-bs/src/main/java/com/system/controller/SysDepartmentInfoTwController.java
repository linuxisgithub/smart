package com.system.controller;

import javax.inject.Inject;

import javacommon.base.BaseBsController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.model.SysDepartmentInfoTw;
import com.system.service.SysDepartmentInfoTwManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:20:26
 *
 */
@RestController
@RequestMapping("/sysDepartmentInfoTw")
public class SysDepartmentInfoTwController extends BaseBsController<SysDepartmentInfoTw> {

	@Inject
	private SysDepartmentInfoTwManager sysDepartmentInfoTwManager;
	@Override
	protected BaseManager<SysDepartmentInfoTw> getManager() {
		return sysDepartmentInfoTwManager;
	}

}