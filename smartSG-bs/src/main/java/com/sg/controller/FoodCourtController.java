package com.sg.controller;

import com.sg.model.Img;
import com.sg.model.SgField;
import com.sg.model.SgType;
import com.sg.service.AnnexManager;
import com.sg.service.ImgManager;
import com.sg.service.SgFieldManager;
import com.sg.service.SgTypeManager;
import com.system.model.LoginUser;
import com.system.model.SysDepartmentInfoSg;
import com.system.service.SysDepartmentInfoSgManager;
import com.system.service.SysDepartmentManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import javacommon.util.HttpStatusCode;
import javacommon.util.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/sg")
public class FoodCourtController extends BaseBsController<BaseModel>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	private SgTypeManager sgTypeManager;

	@Inject
	private SysDepartmentInfoSgManager infoSgManager;
	@Inject
	private SgFieldManager sgFieldManager;
	@Inject
	private ImgManager imgManager;
	@Inject
	private AnnexManager annexManager;
	@Inject
	private SysDepartmentManager sysDepartmentManager;
	/**
	 * 食阁类型下拉框数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/type/data", method=RequestMethod.POST)
	public void findTypeData(HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> data = sgTypeManager.findTypeData(user.getCompany().getEid());
		gzipCompress.data(data, response);
	}

	@RequestMapping(path = "/to/type/list" , method = RequestMethod.GET)
	public String to(Model model, HttpServletRequest request){
		String page =  "/platformManagement/foodCourtManagement/type/list";
		return page;
	}
	
	@RequestMapping(path = "/type/data/list", method = RequestMethod.POST)
	public void typeList(int pageNumber,HttpServletRequest request,HttpServletResponse response){
		try{
			LoginUser user = getLoginUser(request);
			Map<String, Object> criteria = getCriteria(request);
			criteria.put("l_companyId", user.getCompany().getEid());
			Paginator<SgType> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
			paginator.setCriteria(criteria);
			sgTypeManager.findPage(paginator);
			gzipCompress.pages(paginator, response);
		}catch(Exception e){
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "typeList.error", response);
			logger.error("deleteFile error", e);
		}
	}

	@RequestMapping(value = "/type/updateStatus", method = RequestMethod.POST)
	public void updateImStatus(SgType sgType, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		sgTypeManager.update(sgType);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/type/remove", method=RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		SgType sgType = new SgType();
		sgType.setEid(Long.parseLong(eid));
		sgType.setIsDel(1);
		sgTypeManager.update(sgType);
		gzipCompress.success(response);
	}

	@RequestMapping(path = "/type/load" , method = RequestMethod.GET)
	public String load(Long eid, Model model, HttpServletRequest request){
		String view = "/platformManagement/foodCourtManagement/type/create";
		SgType sgType;
		if (eid != null && eid != 0L){
			sgType = sgTypeManager.get(eid);
		}else{
			sgType = new SgType();
		}
		model.addAttribute(MODEL_KEY,sgType);
		return view;
	}
	
	@RequestMapping(value = "/type/save", method=RequestMethod.POST)
	public void save(SgType sgType,
					 HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		if(sgType.getEid() == null) {
			sgType.setCompanyId(user.getCompany().getEid());
			if (user.getUser().getUserType() == 2){//食阁管理公司用户 保存自己的类型
				sgType.setDeptId(user.getUser().getDeptId());
				sgType.setDeptName(user.getUser().getDeptName());
			}
			sgTypeManager.save(sgType);
		} else {
			sgTypeManager.update(sgType);
		}
		gzipCompress.success(response);
	}


	/** 食阁装修 */
	@RequestMapping(path = "/to/renovation" , method = RequestMethod.GET)
	public String toRenovation(Model model, HttpServletRequest request){
		LoginUser user = getLoginUser(request);
		String page =  "/sgManagmentCompany/sgManagement/renovation";
		String id = request.getParameter("id");
		SysDepartmentInfoSg infoSg = infoSgManager.findByBid(Long.parseLong(id)).get(0);
		List<SgField> sgFields = sgFieldManager.findByBid(Long.parseLong(id));
		List<Img> imgs = imgManager.findByBidAndDtype(Long.parseLong(id),1);
		model.addAttribute(MODEL_KEY,infoSg);
		model.addAttribute("fields",sgFields);
		model.addAttribute("imgs",imgs);
		return page;
	}
	@RequestMapping(path = "/to/map" , method = RequestMethod.GET)
	public String toMap(Model model, HttpServletRequest request){
		String page =  "/sgManagmentCompany/sgManagement/map";
		return page;
	}
	@RequestMapping(value = "/sgInfo/save", method=RequestMethod.POST)
	public void saveSgInfo(SysDepartmentInfoSg infoSg, HttpServletRequest request, HttpServletResponse response) {
		infoSgManager.doSave(infoSg);
		gzipCompress.success(response);
	}

	/** 食阁报表 */
	@RequestMapping(path = "/to/report" , method = RequestMethod.GET)
	public String toReport(Model model, HttpServletRequest request){
		LoginUser user = getLoginUser(request);
		String page =  "/sgManagmentCompany/sgManagement/report";
		String sgid = request.getParameter("id");
		model.addAttribute("sgId",sgid);
		return page;
	}

	@RequestMapping(path = "/to/apply" , method = RequestMethod.GET)
	public String toApply(Long bid,String type, Model model, HttpServletRequest request){
		String page =  "/sgManagmentCompany/sgManagement/apply";
		model.addAttribute("bid",bid);
		model.addAttribute("type",type);
		return page;
	}

	@RequestMapping(value = "/apply/save", method=RequestMethod.POST)
	public void saveApply(HttpServletRequest request, HttpServletResponse response) {
		String currentId = request.getParameter("currentId");
		String deptIdsStr = request.getParameter("deptIdsStr");
		String type = request.getParameter("type");
		String[] deptIdsArray = deptIdsStr.split(",");
		infoSgManager.doApply(Long.parseLong(currentId),deptIdsArray,type);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/twData", method=RequestMethod.POST)
	public void findDeptByPid(HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String,Object>> data = sysDepartmentManager.findByPid(user.getUser().getDeptId());
		gzipCompress.data(data, response);
	}
	
	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}

}
