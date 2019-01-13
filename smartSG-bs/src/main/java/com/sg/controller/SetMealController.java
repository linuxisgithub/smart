package com.sg.controller;

import com.sg.model.SetMeal;
import com.sg.service.SetMealManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 超享
 * @date 2019-01-04 14:30:28
 *
 */
@Controller
@RequestMapping("/setMeal")
public class SetMealController extends BaseBsController<SetMeal> {

	@Inject
	private SetMealManager setMealManager;
	@Override
	protected BaseManager<SetMeal> getManager() {
		return setMealManager;
	}

	@RequestMapping(value = "/save", method= RequestMethod.POST)
	public void save(SetMeal setMeal,
					 HttpServletRequest request, HttpServletResponse response) {
		if(setMeal.getEid() == null) {
			setMealManager.save(setMeal);
		} else {
			setMealManager.update(setMeal);
		}
		gzipCompress.success(response);
	}

}