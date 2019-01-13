package com.system.controller;

import javax.inject.Inject;

import javacommon.base.BaseBsController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.model.SysDepartmentInfoContacts;
import com.system.service.SysDepartmentInfoContactsManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:18:13
 *
 */
@RestController
@RequestMapping("/sysDepartmentInfoContacts")
public class SysDepartmentInfoContactsController extends BaseBsController<SysDepartmentInfoContacts> {

	@Inject
	private SysDepartmentInfoContactsManager sysDepartmentInfoContactsManager;
	@Override
	protected BaseManager<SysDepartmentInfoContacts> getManager() {
		return sysDepartmentInfoContactsManager;
	}

}