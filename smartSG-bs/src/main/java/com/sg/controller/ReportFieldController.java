package com.sg.controller;

import javax.inject.Inject;

import javacommon.base.BaseBsController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sg.model.ReportField;
import com.sg.service.ReportFieldManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:59:22
 *
 */
@Controller
@RequestMapping("/reportField")
public class ReportFieldController extends BaseBsController<ReportField> {

	@Inject
	private ReportFieldManager reportFieldManager;
	@Override
	protected BaseManager<ReportField> getManager() {
		return reportFieldManager;
	}

}