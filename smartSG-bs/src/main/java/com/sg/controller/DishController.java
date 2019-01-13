package com.sg.controller;

import com.sg.model.DishFeatures;
import com.sg.model.DishGroup;
import com.sg.model.SetMeal;
import com.sg.service.DishFeaturesManager;
import com.sg.service.DishFeaturesOptionManager;
import com.sg.service.DishGroupManager;
import com.sg.service.SetMealManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 摊位管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(path = "/dish")
public class DishController extends BaseBsController<BaseModel>{

	@Inject
	private DishGroupManager dishGroupManager;
	@Inject
	private SetMealManager setMealManager;
	@Inject
	private DishFeaturesManager dishFeaturesManager;
	@Inject
	private DishFeaturesOptionManager dishFeaturesOptionManager;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(path = "/to/list" , method = RequestMethod.GET)
	public String to(Model model, HttpServletRequest request){
		String page =  "/tw/dishManagement/list";
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		model.addAttribute("twId",id);
		model.addAttribute("type",type);
		return page;
	}

	@RequestMapping(path = "/setting" , method = RequestMethod.GET)
	public String toSetting(Long bid,Integer type, Model model, HttpServletRequest request){
		String view = "/tw/dishManagement/setting";
		List<DishGroup> dgList = dishGroupManager.findByBid(bid);
		List<SetMeal> setMeals = setMealManager.findByBid(bid);
		SetMeal setMeal;
		if (setMeals.isEmpty()){
			setMeal = new SetMeal();
		}else{
			setMeal = setMeals.get(0);
		}
		List<DishFeatures> dishFeaturesList = dishFeaturesManager.findByBidAndType(bid,type);
		dishFeaturesList = dishFeaturesManager.handleDishFeaturesList(dishFeaturesList);
		model.addAttribute("dgList",dgList);
		model.addAttribute("bid",bid);
		model.addAttribute("setMeal",setMeal);
		model.addAttribute("dfList",dishFeaturesList);
		return view;
	}

	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}

}
