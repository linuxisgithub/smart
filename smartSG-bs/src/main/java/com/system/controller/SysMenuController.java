package com.system.controller;

import com.system.model.LoginUser;
import com.system.model.SysMenu;
import com.system.model.SysUser;
import com.system.service.SysMenuManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.util.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: SysMenuController
 * @author duwufeng
 * @date 2017-06-27 11:14:50
 *
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseBsController<SysMenu> {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private SysMenuManager sysMenuManager;

	/**
	 * 测试三级菜单用
	 * @return
	 */
	@RequestMapping(value = "/firstThree", method = RequestMethod.GET)
	public void getFirstMenus(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUser(request);
			SysUser user = loginUser.getUser();
			if(user.getUserType() != 0) {
				Map<String, Object> data = sysMenuManager.getSysSetFirstMenus_three
						(loginUser);
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
	 * 获取一级菜单
	 * @param request
	 * @param response
	 */
	@RequestMapping("/first")
	public void getFristMenus(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUser(request);
			List<Map<String, String>> data = sysMenuManager.getFristMenus(loginUser);
			gzipCompress.data(data, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("getFristMenus error", e);
		}
	}

	/**
	 * 根据父菜单获取子菜单
	 * @param pid
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{pid}")
	public void getMenusByPid(@PathVariable Long pid,
							  HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUser(request);
			List<Map<String, String>> data = sysMenuManager.getMenusByPid(pid, loginUser);
			gzipCompress.data(data, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("getMenusByPid error", e);
		}
	}

	/**
	 * 获取考勤菜单
	 * @param /pid
	 * @param request
	 * @param response
	 */
	@RequestMapping("/work")
	public void getWorkMenu(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUser(request);
			List<Map<String, String>> data = sysMenuManager.getWorkMenu(loginUser);
			gzipCompress.data(data, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("getWorkMenu error", e);
		}
	}

	@Override
	protected BaseManager<SysMenu> getManager() {
		return sysMenuManager;
	}
}