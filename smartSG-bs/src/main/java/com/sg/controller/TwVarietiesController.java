package com.sg.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sg.model.Report;
import com.sg.service.ReportManager;
import com.system.model.LoginUser;
import javacommon.base.BaseBsController;
import javacommon.util.Paginator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sg.model.TwVarieties;
import com.sg.service.TwVarietiesManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-14 14:27:12
 *
 */
@Controller
@RequestMapping("/twVarieties")
public class TwVarietiesController extends BaseBsController<TwVarieties> {

	@Inject
	private TwVarietiesManager twVarietiesManager;

	@Override
	protected BaseManager<TwVarieties> getManager() {
		return twVarietiesManager;
	}

	/**
	 * 食阁类型下拉框数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/data", method=RequestMethod.POST)
	public void findVarietiesData(HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> data = twVarietiesManager.findVarietiesData(user.getCompany().getEid());
		gzipCompress.data(data, response);
	}

	@RequestMapping(path = "/list" , method = RequestMethod.GET)
	public String toList(Model model, HttpServletRequest request){
		String view = "/platformManagement/boothManagement/varieties/list";
		return view;
	}

	@RequestMapping(value = "/data/list", method=RequestMethod.POST)
	public void getDataList(int pageNumber,
						   HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		Paginator<TwVarieties> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		twVarietiesManager.findPage(paginator);
		gzipCompress.pages(paginator, response);
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public void updateImStatus(TwVarieties twVarieties, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		twVarietiesManager.update(twVarieties);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		TwVarieties twVarieties = new TwVarieties();
		twVarieties.setEid(Long.parseLong(eid));
		twVarieties.setIsDel(1);
		twVarietiesManager.update(twVarieties);
		gzipCompress.success(response);
	}

	@RequestMapping(path = "/load" , method = RequestMethod.GET)
	public String load(Long eid, Model model, HttpServletRequest request){
		String view = "/platformManagement/boothManagement/varieties/create";
		TwVarieties twVarieties;
		if (eid != null && eid != 0L){
			twVarieties = twVarietiesManager.get(eid);
		}else{
			twVarieties = new TwVarieties();
		}
		model.addAttribute(MODEL_KEY,twVarieties);
		return view;
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public void save(TwVarieties twVarieties,
					 HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		if(twVarieties.getEid() == null) {
			twVarieties.setCompanyId(user.getCompany().getEid());
			twVarietiesManager.save(twVarieties);
		} else {
			twVarietiesManager.update(twVarieties);
		}
		gzipCompress.success(response);
	}

}