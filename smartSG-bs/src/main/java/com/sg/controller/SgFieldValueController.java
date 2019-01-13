package com.sg.controller;

import javax.inject.Inject;

import javacommon.base.BaseBsController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.model.SgFieldValue;
import com.sg.service.SgFieldValueManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

/**
 *
 * @author 超享
 * @date 2018-12-24 08:49:23
 *
 */
@Controller
@RequestMapping("/sgFieldValue")
public class SgFieldValueController extends BaseBsController<SgFieldValue> {

	@Inject
	private SgFieldValueManager sgFieldValueManager;
	@Override
	protected BaseManager<SgFieldValue> getManager() {
		return sgFieldValueManager;
	}

}