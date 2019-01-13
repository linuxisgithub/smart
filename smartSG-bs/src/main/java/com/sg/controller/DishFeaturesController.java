package com.sg.controller;

import com.sg.model.DishFeatures;
import com.sg.service.DishFeaturesManager;
import com.sg.service.DishFeaturesOptionManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2019-01-04 15:58:38
 *
 */
@Controller
@RequestMapping("/dishFeatures")
public class DishFeaturesController extends BaseBsController<DishFeatures> {

	@Inject
	private DishFeaturesManager dishFeaturesManager;
	@Inject
	private DishFeaturesOptionManager dishFeaturesOptionManager;
	@Override
	protected BaseManager<DishFeatures> getManager() {
		return dishFeaturesManager;
	}

	@RequestMapping(path = "/load" , method = RequestMethod.GET)
	public String load(Long eid, Long bid,Integer type, Model model, HttpServletRequest request){
		String view = "/tw/dishManagement/createFeatures";
		DishFeatures dishFeatures;
		if (eid != null && eid != 0L){
			dishFeatures = dishFeaturesManager.get(eid);
			dishFeatures.setOptions_update(dishFeaturesOptionManager.findByBid(eid));
		}else{
			dishFeatures = new DishFeatures();
			dishFeatures.setBid(bid);
			dishFeatures.setType(type);
		}
		model.addAttribute(MODEL_KEY,dishFeatures);
		return view;
	}

	@RequestMapping(path = "/data/list" , method = RequestMethod.GET)
	public String img(Long bid, Model model, HttpServletRequest request){
		String view = "/tw/dishManagement/featuresList";
		model.addAttribute("bid",bid);
			model.addAttribute("type",2);
		List<DishFeatures> dishFeaturesList = dishFeaturesManager.findByBidAndType(bid,2);
		dishFeaturesList = dishFeaturesManager.handleDishFeaturesList(dishFeaturesList);
		model.addAttribute(MODELS_KEY,dishFeaturesList);
		return view;
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public void save(DishFeatures dishFeatures,
					 HttpServletRequest request, HttpServletResponse response) {
		dishFeaturesManager.doSave(dishFeatures);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		DishFeatures dishFeatures =  new DishFeatures();
		dishFeatures.setEid(Long.parseLong(eid));
		dishFeatures.setIsDel(1);
		dishFeaturesManager.update(dishFeatures);
		gzipCompress.success(response);
	}

}