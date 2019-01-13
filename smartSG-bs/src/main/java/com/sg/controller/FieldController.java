package com.sg.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.system.model.LoginUser;
import javacommon.base.BaseBsController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sg.model.Field;
import com.sg.service.FieldManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:57:33
 *
 */
@Controller
@RequestMapping("/field")
public class FieldController extends BaseBsController<Field> {

	@Inject
	private FieldManager fieldManager;
	@Override
	protected BaseManager<Field> getManager() {
		return fieldManager;
	}

	/**
	 * 报表字段下拉框数据
	 * @param type type=1查APP顾客、type=2查摊位摊位、type=3查食阁、type=4查菜品
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{type}/data", method= RequestMethod.POST)
	public void findData(@PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> data = fieldManager.findTypeData(user.getCompany().getEid(),type);
		gzipCompress.data(data, response);
	}

}