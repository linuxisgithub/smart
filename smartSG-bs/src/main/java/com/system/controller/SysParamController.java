package com.system.controller;

import com.system.model.SysParam;
import com.system.service.SysParamManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

/**
 *
 * @author chaoxiang
 * @date 2017-06-30 15:16:35
 *
 */
@Controller
@RequestMapping("/sysParam")
public class SysParamController extends BaseBsController<SysParam> {
	
	@Inject
	private SysParamManager sysParamManager;

	@Override
	protected BaseManager<SysParam> getManager() {
		return sysParamManager;
	}
	
	
}