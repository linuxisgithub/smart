package com.sg.controller;

import com.sg.model.DishFeaturesOption;
import com.sg.service.DishFeaturesOptionManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2019-01-04 15:59:41
 *
 */
@Controller
@RequestMapping("/dishFeaturesOption")
public class DishFeaturesOptionController extends BaseBsController<DishFeaturesOption> {

	@Inject
	private DishFeaturesOptionManager dishFeaturesOptionManager;
	@Override
	protected BaseManager<DishFeaturesOption> getManager() {
		return dishFeaturesOptionManager;
	}

	@RequestMapping(path = "/preview" , method = RequestMethod.GET)
	public String preview(Long eid,String name,Model model, HttpServletRequest request) throws Exception{
		String view = "/tw/dishManagement/previewFeatures";
		name = new String(name.getBytes("ISO-8859-1"), "utf-8");
		List<DishFeaturesOption> options = dishFeaturesOptionManager.findByBid(eid);
		model.addAttribute("name",name);
		model.addAttribute(MODELS_KEY,options);
		return view;
	}

}