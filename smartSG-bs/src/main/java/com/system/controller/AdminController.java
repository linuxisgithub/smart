package com.system.controller;

import com.sg.service.AdminManager;
import com.system.model.LoginUser;
import com.system.model.SysUser;
import com.system.service.SysMenuManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import javacommon.util.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseBsController<BaseModel> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private AdminManager adminManager;


	@Inject
	private SysMenuManager sysMenuManager;

	@RequestMapping("/system/settings")
	public String toSystemSet( Model model,HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		/*int result = sysMenuManager.hasRightByCode("cjyh_cjsq", loginUser);
		if(result == 0) {
			return "/home/right_less";
		}*/
		return "/admin/index";
	}

	@RequestMapping(value = "/menu/first", method = RequestMethod.GET)
	public void getFirstMenus(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUser(request);
			SysUser user = loginUser.getUser();
			if(user.getUserType() != 0) {
				Map<String, Object> data = adminManager.getSysSetFirstMenus(loginUser);
				gzipCompress.data(data, response);
			} else {
				gzipCompress.failWithMsg(HttpStatusCode.ILLEGAL_ACTION, "非法操作！", response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("getIndexMenus error", e);
		}
	}

	/**
	 * 根据父菜单获取子菜单
	 * @param pid
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/menu/{pid}", method = RequestMethod.POST)
	public void getMenusByPid(@PathVariable Long pid,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUser(request);
			SysUser user = loginUser.getUser();
			if(user.getUserType() != 0) {
				List<Map<String, Object>> data = adminManager.getSysSetMenusByPid(pid, loginUser);
				gzipCompress.data(data, response);
			} else {
				gzipCompress.failWithMsg(HttpStatusCode.ILLEGAL_ACTION, "非法操作！", response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("getMenusByPid error", e);
		}
	}

	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}
}
