package com.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.model.LoginUser;
import com.system.model.SysDepartment;
import com.system.service.*;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.util.HttpStatusCode;
import javacommon.util.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.Map.Entry;

/**
 * 人力资源
 * 
 * @author zwt
 * @since 2017/6/24
 */
@Controller
@RequestMapping("/sysDepartment")
public class SysDepartmentController extends BaseBsController<SysDepartment> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private SysDepartmentManager sysDepartmentManager;

	@Inject
	private SysDepartmentInfoSgglManager sgglManager;
	@Inject
	private SysDepartmentInfoSgManager sgManager;
	@Inject
	private SysDepartmentInfoTwManager twManager;
	@Inject
	private SysDepartmentInfoSgyyzManager sgyyzManager;
	@Inject
	private SysDepartmentInfoContactsManager contactsManager;

	/**
	 * 获取食阁拥有者的地址
	 * @param bid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sgyyzAddress/{bid}", method=RequestMethod.POST)
	public void getSgyyzAddress(@PathVariable long bid,
								HttpServletRequest request, HttpServletResponse response) {
		String address = sgyyzManager.findByBid(bid).get(0).getAddress();

		gzipCompress.data(address,response);
	}
	
	@RequestMapping(value = "/{type}/list", method=RequestMethod.GET)
	public String toList(@PathVariable String type,
			HttpServletRequest request, Model model) {
		return "/platformManagement/userManagement/dept/" + type + "_list";
	}
	
	@RequestMapping(value = "/data/{type}/list", method=RequestMethod.POST)
	public void getDeptList(@PathVariable int type, int pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		criteria.put("i_type", type);
		Paginator<SysDepartment> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sysDepartmentManager.findByType(paginator);
		gzipCompress.pages(paginator, response);
	}

	@RequestMapping(value = "/load", method=RequestMethod.GET)
	public String load(Long eid, int type, int dtype,
			HttpServletRequest request, Model model) {
		LoginUser user = getLoginUser(request);
	    String view = "/platformManagement/userManagement/dept/";
		SysDepartment department = null;
		if(eid != null && eid != 0L) {
			department = sysDepartmentManager.get(eid);
			if (dtype == 2){
				if(type == 2){
					department.setSggl(sgglManager.findByBid(eid).get(0));
				}else if(type == 3){
					department.setSg(sgManager.findByBid(eid).get(0));
				}else if(type == 4){
					department.setTw(twManager.findByBid(eid).get(0));
				}else if(type == 5){
					department.setSgyyz(sgyyzManager.findByBid(eid).get(0));
				}
				department.setContactsList(contactsManager.findByBid(eid));
			}
		} else {
			department = new SysDepartment();
			department.setType(type);
            department.setDtype(dtype);
            if (user.getUser().getUserType() == 2 && type == 3){
            	department.setPid(user.getUser().getDeptId());
            	department.setPname(user.getUser().getDeptName());
			}
		}
		model.addAttribute(MODEL_KEY, department);
		if (dtype == 1){
		    view += "create";
        }else {
            view += "create_sg";
        }
		return view;
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public void save(@Valid SysDepartment department, BindingResult br,
                     HttpServletRequest request, HttpServletResponse response) {
		String validator = getErrorMessages(br);
		if (StringUtils.isNotBlank(validator)) {
			gzipCompress.validator(validator, response);
			return;
		}
		LoginUser user = getLoginUser(request);
		department.setCompanyId(user.getCompany().getEid());
		boolean isExist = sysDepartmentManager.isExistDeptName(user.getCompany().getEid(), department.getName(), department.getEid());
		if(isExist == false) {
			Map<String, Object> menu = new HashMap<>();
			if(department.getEid() == null) {
				if(department.getPid() == null) {
					List<Map<String, Object>> root = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 0, -1);
					Map<String, Object> map = root.get(0);
					department.setPid((Long)map.get("eid"));
					department.setPname("董事会");
				}
				sysDepartmentManager.save(department);
				if (department.getType()==3&&department.getDtype()==2&&user.getUser().getUserType()==2){//食阁管理公司平台添加食阁 返回菜单格式动态添加二级菜单
					menu.put("id","203".concat(department.getEid().toString()));
					menu.put("pid","203");
					menu.put("name",department.getName());
					menu.put("level",2);
					menu.put("menuShow",1);
					menu.put("hasRight",1);
				}
			} else {
				sysDepartmentManager.update(department);
			}
			gzipCompress.data(menu,response);
		} else {
			gzipCompress.failWithMsg(HttpStatusCode.EXIST_ERROR, "部门名称已经存在！", response);
		}
	}
	
	@RequestMapping(value = "/tree", method=RequestMethod.GET)
	public String tree(SysDepartment department, Model model,
                       HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> rootList = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 0, -1);
		Map<String, Object> root = rootList.get(0);
		
		/*Map<String, List<Map<String, Object>>> comLevel = new HashMap<>();
		List<Map<String, Object>> comLevelList = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 1);
		for (Map<String, Object> each : comLevelList) {
			String pid = each.get("pid").toString();
			if(comLevel.containsKey(pid)) {
				comLevel.get(pid).add(each);
			} else {
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(each);
				comLevel.put(pid, list);
			}
		}*/
		List<Map<String, Object>> comLevel = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 1, -1);

		List<Map<String, Object>> firstLevelList = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 2, -1);
		List<Map<String, Object>> hzLevel = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 5, -1);
		firstLevelList.addAll(hzLevel);
		Map<String, List<Map<String, Object>>> firstLevel = new HashMap<>();
		for (Map<String, Object> each : firstLevelList) {
			String pid = each.get("pid").toString();
			if(firstLevel.containsKey(pid)) {
				firstLevel.get(pid).add(each);
			} else {
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(each);
				firstLevel.put(pid, list);
			}
		}
		Map<String, List<Map<String, Object>>> secLevel = new HashMap<>();
		List<Map<String, Object>> secLevelList = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 3,-1);
		for (Map<String, Object> each : secLevelList) {
			String pid = each.get("pid").toString();
			if(secLevel.containsKey(pid)) {
				secLevel.get(pid).add(each);
			} else {
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(each);
				secLevel.put(pid, list);
			}
		}
		Map<String, List<Map<String, Object>>> thirdLevel = new HashMap<>();
		List<Map<String, Object>> thirdLevelList = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), 4, -1);
		for (Map<String, Object> each : thirdLevelList) {
			String pid = each.get("pid").toString();
			if(firstLevel.containsKey(pid)) {
				firstLevel.get(pid).add(each);
			} else if(secLevel.containsKey(pid)) {
				secLevel.get(pid).add(each);
			} else if(thirdLevel.containsKey(pid)) {
				thirdLevel.get(pid).add(each);
			} else {
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(each);
				thirdLevel.put(pid, list);
			}
		}
		int maxNum = -1;
		Map<String, Object> nums = sysDepartmentManager.getDeptLevelNum(user.getCompany().getEid());
		for (Entry<String, Object> entry : nums.entrySet()) {
			String object = entry.getValue().toString();
			if(Integer.parseInt(object) > maxNum) {
				maxNum = Integer.parseInt(object);
			}
		}
		int count = 1;
		String key = null;
		for (Map<String, Object> com : comLevel) {
			key = com.get("eid").toString();
			List<Map<String, Object>> firstList = firstLevel.get(key);
			if(firstList != null) {
				for (Map<String, Object> firstEach : firstList) {
					key = firstEach.get("eid").toString();
					List<Map<String, Object>> secondList = secLevel.get(key);
					if(secondList != null) {
						for (Map<String, Object> secondEach : secondList) {
							key = secondEach.get("pid").toString();
							List<Map<String, Object>> outList = thirdLevel.get(key);
							if(outList != null) {
								count += outList.size() - 1;
							}
						}
						count += secondList.size() - 1;
					}
				}
				count += firstList.size();
			}
		}
		if(count > maxNum) {
			maxNum = count;
		}
		model.addAttribute("maxNum", maxNum);
		model.addAttribute("comLevel", JSONArray.toJSONString(comLevel));
		model.addAttribute("firstLevel", JSONArray.toJSONString(firstLevel));
		model.addAttribute("secLevel", JSONArray.toJSONString(secLevel));
		model.addAttribute("thirdLevel", JSONArray.toJSONString(thirdLevel));
		model.addAttribute("company", JSONArray.toJSONString(user.getCompany()));
		model.addAttribute("root", JSONObject.toJSONString(root));
		return "/platformManagement/userManagement/dept/dept_tree";
	}
	
	/**
	 * 获取某个层级的部门
	 * @param type -1=查全部  -2=查公司层面包含董事会  1=查公司层面，2=查一级部门，3=查二级部门，4=查三级部门，5=查合作单位
	 * @param dtype -1=查全部   1=查本公司部门  2=查食阁
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/data/{type}/{dtype}", method=RequestMethod.POST)
	public void findDeptsByType(@PathVariable int type,@PathVariable int dtype,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> data = sysDepartmentManager.findDeptsByType(user.getCompany().getEid(), type, dtype);
		//食阁管理公司用户登录 查自己的食阁部门（二级部门）
		if (user.getUser().getUserType() == 2 && type == 3 && dtype == 2){
			Iterator<Map<String, Object>> it = data.iterator();
			while(it.hasNext()){
				Map<String,Object> dept = it.next();
				if(dept.get("pid") != user.getUser().getDeptId()){
                     it.remove();
				}
			}
		}
		gzipCompress.data(data, response);
	}


	@RequestMapping(value = "/sg/list", method=RequestMethod.GET)
	public String toSgList(HttpServletRequest request, Model model) {
		return "/sgManagmentCompany/userManagement/dept/sg_list";
	}
	@RequestMapping(value = "/sg/data/list", method=RequestMethod.POST)
	public void getSgList(int pageNumber, HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		criteria.put("pid", user.getUser().getDeptId());
		Paginator<SysDepartment> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sysDepartmentManager.findPage(paginator,"findByPid");
		gzipCompress.pages(paginator, response);
	}
	@RequestMapping(value = "/tw/list", method=RequestMethod.GET)
	public String toTwList(HttpServletRequest request, Model model) {
		return "/sgManagmentCompany/userManagement/dept/tw_list";
	}
	@RequestMapping(value = "/tw/data/list", method=RequestMethod.POST)
	public void getTwList(int pageNumber, HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		criteria.put("pid", user.getUser().getDeptId());
		Paginator<SysDepartment> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sysDepartmentManager.findPage(paginator,"findByPpid");
		gzipCompress.pages(paginator, response);
	}
	
	@Override
	protected BaseManager<SysDepartment> getManager() {
		return sysDepartmentManager;
	}
	
}
