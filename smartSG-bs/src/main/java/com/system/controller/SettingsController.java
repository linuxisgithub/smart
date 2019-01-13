package com.system.controller;

import com.system.model.LoginUser;
import com.system.model.SysCompany;
import com.system.model.SysUserMenu;
import com.system.service.SysCompanyManager;
import com.system.service.SysMenuManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import javacommon.util.MD5Util;
import javacommon.util.OaBtype;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/settings")
public class SettingsController extends BaseBsController<BaseModel> {

	@Inject
	private SysMenuManager sysMenuManager;
	@Inject
	private SysCompanyManager sysCompanyManager;
	/**
	 * 跳转
	 * @param pid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/right/list/{pid}", method = RequestMethod.GET)
	public String toRightList(@PathVariable Long pid,
			HttpServletRequest request, Model model) {
		model.addAttribute("pid", pid);
		return "/settings/right/list";
	}

	/**
	 * 加载权限列表数据
	 * @param pid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/right/list/{pid}", method = RequestMethod.POST)
	public void getRightList(@PathVariable Long pid,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		SysCompany company = loginUser.getCompany();
		Map<String, List<Map<String, Object>>> result = new LinkedHashMap<>();
		List<Map<String, Object>> lists = sysMenuManager.getSettingsRightList(company.getEid(), pid, company.getLevel());
		for (Map<String, Object> map : lists) {
			String name = map.get("name").toString();
			if(result.containsKey(name)) {
				result.get(name).add(map);
			} else {
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(map);
				result.put(name, list);
			}
		}
		gzipCompress.data(result, response);
	}

	@RequestMapping(value = "/right/set", method = RequestMethod.GET)
	public String setRight(Long eid, Model model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		LoginUser user = getLoginUser(request);
		String title = URLDecoder.decode(request.getParameter("title"), "UTF-8");
		String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
		model.addAttribute("title", title);
		model.addAttribute("menuId", eid);
		model.addAttribute("menuName", name);
		model.addAttribute("companyId", user.getCompany().getEid());
		model.addAttribute("kind", request.getParameter("kind"));
		model.addAttribute("useIds", request.getParameter("useIds"));
		return "/settings/right/set_right";
	}

	@RequestMapping(value = "/right/save", method = RequestMethod.POST)
	public void saveRight(@Valid SysUserMenu sysUserMenu, BindingResult br,
                          HttpServletRequest request, HttpServletResponse response) {
		String validator = getErrorMessages(br);
		if (StringUtils.isNotBlank(validator)) {
			gzipCompress.validator(validator, response);
			return;
		}
		LoginUser user = getLoginUser(request);
		sysMenuManager.saveRight(user, sysUserMenu);
		gzipCompress.success(response);
	}
	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}

	@RequestMapping(value = "/company/info", method = RequestMethod.GET)
	public String getComInfo(Long eid, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String view = "/settings/company/comInfo";
		if(eid == null) {
			LoginUser user = getLoginUser(request);
			eid = user.getCompany().getEid();
		}
		SysCompany company = sysCompanyManager.get(eid);
		model.addAttribute(MODEL_KEY, company);
		return view;
	}

	@RequestMapping(value = "/company/info/save", method = RequestMethod.POST)
	public void saveComInfo(SysCompany company, Model model,
                            HttpServletRequest request, HttpServletResponse response) {
		if("".equals(company.getAdminPassword())|| "******".equals(company.getAdminPassword()) ||company.getAdminPassword()==null){
			company.setAdminPassword(null);
		}else{
			String password = company.getAdminPassword();
			company.setAdminPassword(MD5Util.MD5(MD5Util.MD5(password)));
		}
		sysCompanyManager.update(company);
		gzipCompress.success(response);
	}
}
