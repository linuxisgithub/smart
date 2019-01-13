package com.sg.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.system.model.LoginUser;
import javacommon.base.BaseBsController;
import javacommon.util.Paginator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sg.model.SgField;
import com.sg.service.SgFieldManager;

import javacommon.base.BaseManager;

import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-24 08:50:35
 *
 */
@Controller
@RequestMapping("/sgField")
public class SgFieldController extends BaseBsController<SgField> {

	@Inject
	private SgFieldManager sgFieldManager;
	@Override
	protected BaseManager<SgField> getManager() {
		return sgFieldManager;
	}

	@RequestMapping(path = "/list" , method = RequestMethod.GET)
	public String toList(Long bid, Model model, HttpServletRequest request){
		String view = "/sgManagmentCompany/sgManagement/fieldList";
		model.addAttribute("bid",bid);
		return view;
	}

	@RequestMapping(value = "/data/list", method=RequestMethod.POST)
	public void getDataList(int pageNumber,
							HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Long bid = Long.parseLong(request.getParameter("bid"));
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		criteria.put("bid", bid);
		Paginator<SgField> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sgFieldManager.findPage(paginator);
		gzipCompress.pages(paginator, response);
	}

	@RequestMapping(path = "/load" , method = RequestMethod.GET)
	public String load(Long eid, Model model, HttpServletRequest request){
		String page =  "/sgManagmentCompany/sgManagement/createField";
		SgField sgField;
		if (eid != null && eid != 0L){
			sgField = sgFieldManager.get(eid);
		}else{
			Long bid = Long.parseLong(request.getParameter("bid"));
			sgField = new SgField();
			sgField.setBid(bid);
		}
		model.addAttribute(MODEL_KEY,sgField);
		return page;
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public void save(SgField sgField,
					 HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		if(sgField.getEid() == null) {
			sgFieldManager.save(sgField);
		} else {
			sgFieldManager.update(sgField);
		}
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		SgField sgField = new SgField();
		sgField.setEid(Long.parseLong(eid));
		sgField.setIsDel(1);
		sgFieldManager.update(sgField);
		gzipCompress.success(response);
	}

}