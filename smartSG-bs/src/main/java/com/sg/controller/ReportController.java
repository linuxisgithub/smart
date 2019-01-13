package com.sg.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sg.model.ReportField;
import com.sg.service.FieldManager;
import com.sg.service.ReportFieldManager;
import com.system.model.LoginUser;
import javacommon.base.BaseBsController;
import javacommon.util.Paginator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sg.model.Report;
import com.sg.service.ReportManager;

import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:58:03
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseBsController<Report> {

	@Inject
	private ReportManager reportManager;
	@Inject
	private ReportFieldManager reportFieldManager;
	@Override
	protected BaseManager<Report> getManager() {
		return reportManager;
	}


	/**
	 * 报表下拉框数据
	 * @param kind type=1查APP顾客、type=2查摊位摊位、type=3查食阁、type=4查菜品
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{kind}/data", method= RequestMethod.POST)
	public void findData(@PathVariable Integer kind, HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> data = reportManager.findKingData(user.getCompany().getEid(),kind);
		gzipCompress.data(data, response);
	}

	@RequestMapping(path = "/{type}/list" , method = RequestMethod.GET)
	public String toReportList(@PathVariable String type, Model model, HttpServletRequest request){
		String view = "/platformManagement/report/list";
		model.addAttribute("type",type);
		return view;
	}


	@RequestMapping(value = "/{type}/data/list", method=RequestMethod.POST)
	public void getReportDataList(int pageNumber,@PathVariable String type,
								  HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		criteria.put("s_type", type);
		Paginator<Report> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		reportManager.findPage(paginator);
		gzipCompress.pages(paginator, response);
	}

	@RequestMapping(path = "/load" , method = RequestMethod.GET)
	public String reportLoad(Long eid,String type, Model model, HttpServletRequest request){
		String view = "/platformManagement/report/create";
		Report report;
		if (eid != null && eid != 0L){
			report = reportManager.get(eid);
			List<Long> liOptions = reportFieldManager.findByReportId(eid);
			report.setLiOption(liOptions);
		}else{
			report = new Report();
		}
		model.addAttribute("type",type);
		model.addAttribute(MODEL_KEY,report);
		return view;
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public void save(Report report,
					 HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		reportManager.doSave(report,user);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public void updateImStatus(Report report, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		reportManager.update(report);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		Report report = new Report();
		report.setEid(Long.parseLong(eid));
		report.setIsDel(1);
		reportManager.update(report);
		gzipCompress.success(response);
	}

	@RequestMapping(path = "/preView" , method = RequestMethod.GET)
	public String preView(Long eid,Model model, HttpServletRequest request){
		String view = "/platformManagement/report/preview";
		Report report = reportManager.get(eid);
		List<Map<String,Object>> fields = reportFieldManager.findFieldNameByReportId(eid);
		model.addAttribute("fields",fields);
		model.addAttribute("reportName",report.getName());
		return view;
	}

}