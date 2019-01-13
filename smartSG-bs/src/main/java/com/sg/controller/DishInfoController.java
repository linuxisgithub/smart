package com.sg.controller;

import com.sg.model.DishInfo;
import com.sg.model.Img;
import com.sg.service.DishInfoManager;
import com.sg.service.ImgManager;
import com.system.model.LoginUser;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.util.Paginator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2019-01-03 14:35:10
 *
 */
@Controller
@RequestMapping("/dishInfo")
public class DishInfoController extends BaseBsController<DishInfo> {

	@Inject
	private DishInfoManager dishInfoManager;
	@Inject
	private ImgManager imgManager;
	@Override
	protected BaseManager<DishInfo> getManager() {
		return dishInfoManager;
	}

	@RequestMapping(value = "/data/list/{deptId}", method= RequestMethod.POST)
	public void getReportDataList(int pageNumber, @PathVariable Long deptId, HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_deptId", deptId);
		Paginator<DishInfo> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		dishInfoManager.findPage(paginator);
		gzipCompress.pages(paginator, response);
	}

	@RequestMapping(path = "/load" , method = RequestMethod.GET)
	public String load(Long eid, Long bid, Model model, HttpServletRequest request){
		String view = "/tw/dishManagement/create";
		DishInfo dishInfo;
		if (eid != null && eid != 0L){
			dishInfo = dishInfoManager.get(eid);
		}else{
			dishInfo = new DishInfo();
			dishInfo.setDeptId(bid);
		}
		model.addAttribute(MODEL_KEY,dishInfo);
		return view;
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public void save(DishInfo dishInfo,
					 HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		if(dishInfo.getEid() == null) {
			dishInfoManager.save(dishInfo);
		} else {
			dishInfoManager.update(dishInfo);
		}
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		dishInfoManager.doRemove(Long.parseLong(eid));
		gzipCompress.success(response);
	}

	@RequestMapping(path = "/img" , method = RequestMethod.GET)
	public String img(Long eid, Model model, HttpServletRequest request){
		String view = "/tw/dishManagement/img";
		model.addAttribute("eid",eid);
		List<Img> imgs = imgManager.findByBidAndDtype(eid,3);
		model.addAttribute("imgs",imgs);
		return view;
	}

}