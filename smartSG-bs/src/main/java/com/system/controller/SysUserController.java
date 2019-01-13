package com.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.system.model.*;
import com.system.service.SysDepartmentManager;
import com.system.service.SysUserManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.util.DateUtil;
import javacommon.util.HttpStatusCode;
import javacommon.util.Paginator;
import javacommon.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zwt
 * @since 2017/6/24
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseBsController<SysUser> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private SysUserManager sysUserManager;
	
	@Inject
	private SysDepartmentManager sysDepartmentManager;
	
	@Inject
	private RabbitTemplate erpTemplate;
	
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@Valid SysUser sysUser, SysUserMenu sysUserMenu, BindingResult br, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String validator = getErrorMessages(br);
		if (StringUtils.isNotBlank(validator)) {
			gzipCompress.validator(validator, response);
			return;
		}
		LoginUser loginUser = getLoginUser(request);
		sysUser.setCompanyId(loginUser.getCompany().getEid());
		sysUser.setCreateUserId(loginUser.getUser().getEid());
		boolean isExist = sysUserManager.isExistAccount(loginUser.getCompany().getEid(), sysUser.getAccount(), sysUser.getEid());
		if(isExist == false) {
			if(sysUser.getEid() == null) {
				int result = sysUserManager.saveUser(loginUser, sysUser);
				if(result == 1) {
					gzipCompress.failWithMsg(HttpStatusCode.FAIL, "启用的授权账号超过限额！", response);
				} else if(result == 2) {
					gzipCompress.failWithMsg(HttpStatusCode.FAIL, "启用的IM用户超过限额！", response);
				} else if(result == 0) {
					// 推送新同事
/*					JSONObject msg = new JSONObject();
					String allImStr = sysUserManager.findAllUserImAccountStr(
							loginUser.getCompany().getEid(),sysUser.getImAccount());
					msg.put("name", sysUser.getName());
					msg.put("icon",
							sysUser.getIcon() == null ? "" : sysUser.getIcon());
					msg.put("imAccount", sysUser.getImAccount());
					msg.put("deptName", sysUser.getDeptName());
					msg.put("deptId", sysUser.getDeptId());
					msg.put("joinTime", sysUser.getEmployTime());
					msg.put("job",
							sysUser.getJob() == null ? "" : sysUser.getJob());
					pushMsg(allImStr,
							String.valueOf(OaBtype.PUSH_NEW_USER.getBtype()),
							msg);*/
					gzipCompress.data(sysUser.getEid(), response);
				}
				
			} else {
				int result = sysUserManager.updateUser(loginUser, sysUser);
				if(result == 1) {
					gzipCompress.failWithMsg(HttpStatusCode.FAIL, "启用的授权账号超过限额！", response);
				} else if(result == 2) {
					gzipCompress.failWithMsg(HttpStatusCode.FAIL, "启用的IM用户超过限额！", response);
				} else if(result == 3) {
					gzipCompress.failWithMsg(HttpStatusCode.FAIL, "不能停用管理员账号！", response);
				} else if(result == 0) {
					gzipCompress.data(sysUser.getEid(), response);
				}
			}
		} else {
			gzipCompress.failWithMsg(HttpStatusCode.EXIST_ERROR, "系统账号已经存在！", response);
		}
		
	}

	
	/**
	 * 基础信息--系统用户 --列表
	 * @param type 部门类型
	 * @param dtype 1:本公司部门 2:食阁
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{type}/{dtype}/list", method=RequestMethod.GET)
	public String toSysUserList(@PathVariable String type,@PathVariable String dtype,
			HttpServletRequest request,Model model) {
		model.addAttribute("type", type);
		model.addAttribute("dtype", dtype);
		return "/platformManagement/userManagement/sysUser/list";
	}
	
	@RequestMapping(value = "/data/{type}/{dtype}/list", method=RequestMethod.POST)
	public void getSysUserList(@PathVariable int type, @PathVariable int dtype, int pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		criteria.put("i_type", type);
		criteria.put("i_dtype", dtype);
		if (user.getUser().getUserType() == 2 && (type == 3 || type == 4 || type == 6)&& dtype == 2){
			criteria.put("pid", user.getUser().getDeptId());
		}
		Paginator<SysUser> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sysUserManager.findByType(paginator);
		gzipCompress.pages(paginator, response);
	}
	
	/**
	 * 基础信息--系统用户 --添加
	 * @param eid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/load")
	public String load(Long eid, HttpServletRequest request, Model model) {
		SysUser sysUser = null;
		String type = request.getParameter("type");
		String isDDX = request.getParameter("isDDX");
		if(eid != null && eid != 0L) {
			sysUser = sysUserManager.get(eid);
		} else{
			sysUser = new SysUser();
			sysUser.setUserType(Integer.parseInt(type));
		}

		model.addAttribute(MODEL_KEY, sysUser);
		if(!"".equals(isDDX)&&isDDX!=null){
			return "/platformManagement/userManagement/ddx/create";
		}else{
			return "/platformManagement/userManagement/sysUser/create";
		}
	}

	/**
	 * 加入黑名单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/toBlack", method=RequestMethod.POST)
	public void toBlack(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		SysUser user = new SysUser();
		user.setEid(Long.parseLong(eid));
		user.setIsBlack(1);
		user.setStatus(2);
		user.setImStatus(0);
		sysUserManager.update(user);
		gzipCompress.success(response);
	}

	/**
	 * 移出黑名单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/removeBlack", method=RequestMethod.POST)
	public void removeBlack(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		SysUser user = new SysUser();
		user.setEid(Long.parseLong(eid));
		user.setIsBlack(0);
		user.setStatus(1);
		user.setImStatus(1);
		sysUserManager.update(user);
		gzipCompress.success(response);
	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		String eid = request.getParameter("eid");
		SysUser user = new SysUser();
		user.setEid(Long.parseLong(eid));
		user.setIsDel(1);
		user.setStatus(2);
		user.setImStatus(0);
		sysUserManager.update(user);
		gzipCompress.success(response);
	}

	/**
	 * 客户端App用户
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appUser/list", method=RequestMethod.GET)
	public String toAppUserList(HttpServletRequest request,Model model) {
		return "/platformManagement/appUser/list";
	}

	/**
	 * 客户端App用户
	 * @param pageNumber
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/appUser/data/list", method=RequestMethod.POST)
	public void getappUserList(int pageNumber,HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		Paginator<SysUser> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sysUserManager.findPage(paginator,"findAppUser");
		gzipCompress.pages(paginator, response);
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public void updateStatus(SysUser user, HttpServletRequest request, HttpServletResponse response) {
		sysUserManager.update(user);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/appDdx/list", method=RequestMethod.GET)
	public String toAppDdxList(HttpServletRequest request,Model model) {
		return "/platformManagement/appUser/ddxList";
	}

	@RequestMapping(value = "/appDdx/data/list", method=RequestMethod.POST)
	public void getAppDdxList(int pageNumber,
						   HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		Paginator<SysUser> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sysUserManager.findPage(paginator,"findAppDdx");
		gzipCompress.pages(paginator, response);
	}

	public void pushMsg(String to, String type, Object msg) throws Exception {
		JSONObject pushMsg = new JSONObject();
		JSONObject data = new JSONObject();
		pushMsg.put("to", to);
		data.put(type, msg);
		pushMsg.put("datas", data);
		logger.info("推出的消息：" + pushMsg);
		erpTemplate.convertAndSend("erpMessageQueue", pushMsg.toJSONString());
	}
	@RequestMapping(value = "/approvalset/list")
	public String findApprovalset(Model model, HttpServletRequest request) {
		return "/platformManagement/userManagement/sysUser/approvalset_list";
	}
	@RequestMapping(value = "/data/level/{level}", method=RequestMethod.POST)
	public void getSysUserByLevel(@PathVariable int level,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> list = sysUserManager.getSysUserByLevel(user.getCompany().getEid(), level);
		gzipCompress.data(list, response);
	}
	
	/**
	 * 基础信息--叮当享 --列表
	 * @param type
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ddx/{type}/{dtype}/list", method=RequestMethod.GET)
	public String toDdxList(@PathVariable String type,@PathVariable String dtype,
			HttpServletRequest request,Model model) {
		model.addAttribute("type", type);
		model.addAttribute("dtype", dtype);
		return "/platformManagement/userManagement/ddx/list";
	}
	
	@RequestMapping(value = "/ddx/{type}/{dtype}/list", method=RequestMethod.POST)
	public void getDdxList(@PathVariable int type, @PathVariable int dtype,  int pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		Map<String, Object> criteria = getCriteria(request);
		criteria.put("l_companyId", user.getCompany().getEid());
		criteria.put("i_type", type);
		criteria.put("i_dtype", dtype);
		if (user.getUser().getUserType() == 2 && (type == 3 || type == 4)&& dtype == 2){
			criteria.put("pid", user.getUser().getDeptId());
		}
		Paginator<SysUser> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
		paginator.setCriteria(criteria);
		sysUserManager.findDdxByType(paginator);
		gzipCompress.pages(paginator, response);
	}
	
	@RequestMapping(value = "/updateImStatus", method = RequestMethod.POST)
	public void updateImStatus(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		SysCompany company = loginUser.getCompany();
		SysUser oldSysUser = sysUserManager.get(sysUser.getEid());
		if(sysUser.getImStatus() != null && sysUser.getImStatus() == 1 && oldSysUser.getImStatus() != 1) {
			int imUseNum = sysUserManager.countImUseNum(company.getEid());
			if(imUseNum >= company.maxImUserNum()) {
				// 启用的IM用户超过限额
				gzipCompress.failWithMsg(HttpStatusCode.FAIL, "启用的IM用户超过限额！", response);
			} else {
				oldSysUser.setImStatus(sysUser.getImStatus());
				sysUserManager.update(oldSysUser);
				gzipCompress.success(response);
			}
		} else {
			oldSysUser.setImStatus(sysUser.getImStatus());
			sysUserManager.update(oldSysUser);
			gzipCompress.success(response);
		}
	}
	
	@RequestMapping(value = "/data/list/{deptId}", method = RequestMethod.POST)
	public void getUsersByDeptId(@PathVariable Long deptId,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		String isManager = request.getParameter("isManager");
		List<Map<String, Object>> result = null;
		if(isManager != null && isManager.equals("2")) {
			result = sysUserManager.findUsersBySubDeptCode(loginUser, deptId);
		} else if(isManager != null && isManager.equals("1")) {
			result = sysUserManager.findUsersByDeptIdAndManager(loginUser, deptId);
		} else {
			result = sysUserManager.findUsersByDeptId(loginUser, deptId);
		}
		gzipCompress.data(result, response);
	}
	
	@RequestMapping(value = "/data/detail/list/{deptId}", method=RequestMethod.POST)
	public void findUser(@PathVariable Long deptId,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String, Object>> data = sysUserManager.findDetailUsers(user, deptId);
		gzipCompress.data(data, response);
	}

	@Override
	protected BaseManager<SysUser> getManager() {
		return sysUserManager;
	}
	
	@RequestMapping(value = "/self/info", method=RequestMethod.GET)
	public String getUserInfo(Long eid, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String view = "/sysUser/myInfo";
		if(eid == null) {
			LoginUser user = getLoginUser(request);
			eid = user.getUser().getEid();
		}
		SysUser user = sysUserManager.get(eid);
		model.addAttribute(MODEL_KEY, user);
		return view;
	}
	@RequestMapping(value = "/self/userICon", method=RequestMethod.GET)
	public String getUserIcon(long eid, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String view = "/sysUser/editUserIcon";
		SysUser user = sysUserManager.get(eid);
		model.addAttribute("icon", user.getIcon());
		return view;
	}
	
	@RequestMapping(value = "/self/info/save", method=RequestMethod.POST)
	public void updateUserInfo(SysUser sysUser, Model model,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*if("".equals(sysUser.getPassword())|| "******".equals(sysUser.getPassword()) ||sysUser.getPassword()==null){
			SysUser user = sysUserManager.get(sysUser.getEid());
			sysUser.setPassword(user.getPassword());
		}else{
			String password = sysUser.getPassword();
			sysUser.setPassword(MD5Util.MD5(MD5Util.MD5(password)));
		}
		SysUser old = sysUserManager.get(sysUser.getEid());
		sysUser.setImAccount(old.getImAccount());*/
		LoginUser loginUser = getLoginUser(request);
		sysUserManager.updateUser(loginUser, sysUser);
		gzipCompress.success(response);
	}

    /**
	 * 搜索
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/search")
	public String toSearchList(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
    	String s_name = request.getParameter("s_name");
    	if(s_name != null && s_name != "") {
    		s_name = URLDecoder.decode(s_name, "utf-8");
    		model.addAttribute("s_name", s_name);
    	}
    	String s_deptName = request.getParameter("s_deptName");
    	if(s_deptName != null && s_deptName != "") {
    		s_deptName = URLDecoder.decode(s_deptName, "utf-8");
    		model.addAttribute("s_deptName", s_deptName);
    	}
    	String s_jobStatus = request.getParameter("s_jobStatus");
    	if(s_jobStatus != null && s_jobStatus != "") {
    		s_jobStatus = URLDecoder.decode(s_jobStatus, "utf-8");
    		Integer i_jobStatus = Integer.parseInt(s_jobStatus);
			model.addAttribute("s_jobStatus", i_jobStatus);
    	}
		return "/sysUser/search";
	}
}
