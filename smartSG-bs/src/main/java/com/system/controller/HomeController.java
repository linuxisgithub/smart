package com.system.controller;

import com.alibaba.fastjson.JSON;
import com.system.model.*;
import com.system.service.SysCompanyManager;
import com.system.service.SysDepartmentManager;
import com.system.service.SysMenuManager;
import com.system.service.SysUserManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import javacommon.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController extends BaseBsController<BaseModel> {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	private SysUserManager sysUserManager;

	@Inject
	private SysCompanyManager companyManager;

	@Inject
	private SysDepartmentManager departmentManager;

	@Inject
	private SysMenuManager sysMenuManager;

	@SuppressWarnings("rawtypes")
	@Inject
	protected RedisTemplate redisTemplate;

	@Inject
	private Properties propertySetting;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(String account, String password, Boolean rememberMe,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String ip = PlatformParam.getRemoteIp(request);
		logger.warn("请求登录：" + account + "，ip：" + ip);
		SysCompany company;
		SysUser sysUser = sysUserManager.findByAccount(account);
		if (sysUser != null) {
			String _password = sysUser.getPassword();
			if (password.equals(_password)) {
				LoginUser loginUser = new LoginUser();
				loginUser.setUser(sysUser);
				company = companyManager.get(sysUser.getCompanyId());
				String dnsAddress = request.getServerName();
				Integer status = sysUser.getStatus();
				if(1 != status/* && 1 != imStatus*/) {
					result.put("result", 6);
					result.put("msg", "该账号已经停用！");
				} else {
					/*String url = propertySetting.getProperty("api_url");
					url += "/local/project/expireTime.json";
					Map<String, Object> param = new HashMap<>();
					String appId = propertySetting.getProperty("local_project_appid").toString();
					String appKey = propertySetting.getProperty("local_project_appkey").toString();
					param.put("appId", appId);
					param.put("appKey", appKey);
					String post = HttpClientUtil.post(url, param);
					JSONObject checkResponse = JSON.parseObject(post);*/
					String checkStatus = "200";/*checkResponse.get("status").toString();*/
					if(!"200".equals(checkStatus)){
						result.put("result", 4);
						result.put("msg", "您购买的版本已经到期，请续费！");
					} else {
						loginUser.setCompany(company);
						SysDepartment department = departmentManager.get(sysUser
								.getDeptId());
						loginUser.setDepartment(department);
						HttpSession session = request.getSession();
						session.setAttribute(PlatformParam.USER_IN_SESSION,
								loginUser);
						result.put("result", 0);// 登录成功
						result.put("msg", "登录成功！");
						if (true == rememberMe) {
							keepLogin(loginUser, request, response);
						}
					}
				}
			} else {
				result.put("msg", "账号与密码不匹配！");
				result.put("result", 3);// 密码不存在
			}
		} else {
			result.put("result", 2);// 账号不存在
			result.put("msg", "账号与密码不匹配！");
		}
		gzipCompress.data(result, response);
	}

	/**
	 * 设置登录cookie
	 *
	 * @param loginUser
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public void keepLogin(LoginUser loginUser, HttpServletRequest request,
						  HttpServletResponse response) {
		try {
			String ip = PlatformParam.getRemoteIp(request);
			String uuid = UUID.getUUID();
			String token = MD5Util.base64Encode(uuid);
			String redisToken = MD5Util.MD5(ip + "_i_p_" + token);
			int FNVHash1Token = MD5Util.FNVHash1(redisToken);
			redisTemplate.opsForValue().set(
					PlatformParam.REDIS_LOGIN_KEY
							+ String.valueOf(FNVHash1Token),
					JSON.toJSONString(loginUser), 7L, TimeUnit.DAYS);
			CookieUtil.setCookie(PlatformParam.cookie_l_token, token, response);
		} catch (Exception e) {
			logger.error("设置登录cookie失败：" + e.getMessage());
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		String view = "/home/login";
		String l_token = CookieUtil.getValue(PlatformParam.cookie_l_token,
				request);
		boolean hasToken = false;
		String localProject = propertySetting.getProperty("local_project");
		HttpSession session = request.getSession();
		session.setAttribute("localProject", localProject);
		if (l_token != null) {
			String ip = PlatformParam.getRemoteIp(request);
			String redisToken = MD5Util.MD5(ip + "_i_p_" + l_token);
			int FNVHash1Token = MD5Util.FNVHash1(redisToken);
			String redisResult = (String) redisTemplate.opsForValue().get(
					PlatformParam.REDIS_LOGIN_KEY
							+ String.valueOf(FNVHash1Token));
			if (StringUtils.isNoneBlank(redisResult)) {
				LoginUser loginUser = JSON.parseObject(redisResult,
						LoginUser.class);
				SysUser oldUser = loginUser.getUser();
				if(oldUser != null){
					SysUser sysUser = sysUserManager.findByAccount(loginUser.getUser().getAccount());
					if(oldUser.getPassword().equals(sysUser.getPassword())) {
						loginUser.setUser(sysUser);
						logger.warn("登录：" + loginUser.getUser().getAccount() + "，ip：" + ip);

						session.setAttribute(PlatformParam.USER_IN_SESSION, loginUser);
						hasToken = true;
						view = "/home/index";
					} else {
						CookieUtil.removeCookie(PlatformParam.cookie_l_token, request, response);
					}
				}
			}
		}
		return view;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
						 HttpServletResponse response, Model model) {
		String view = "/home/login";
		HttpSession session = request.getSession();
		session.invalidate();
		CookieUtil.removeCookie(PlatformParam.cookie_l_token, request, response);
		String localProject = propertySetting.getProperty("local_project");
		model.addAttribute("localProject", localProject);
		return view;
	}



	@RequestMapping("/im/index")
	public String toImIndex(HttpServletRequest request, Model model) {
		return "/home/im_index";
	}
	@RequestMapping("/index")
	public String toIndex(HttpServletRequest request, Model model) {
		String view = "/home/index";
		LoginUser loginUser = getLoginUser(request);
		int isSpec = companyManager.findIsSpec(loginUser.getCompany().getEid());
		if(1 != loginUser.getUser().getStatus() && 1 == loginUser.getUser().getImStatus()) {
			view = "/home/im_index";
		}
		if(isSpec == 1){
			model.addAttribute("isSpec", 1);
		}
		return view;
	}

	/**
	 * 加密锁超级用户跳转
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/vipLogin")
	public String toVipIndex(HttpServletRequest request, Model model) {
		return "/home/vip_login";
	}

/*
	*/
/**
	 * 获取公司信息过期时间
	 *
	 * @param t
	 * @param request
	 * @param response
	 *//*

	@RequestMapping(value = "/getRoKeyData", method = RequestMethod.GET)
	public void save(Sysuserdevice t, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			Map<String, Object> data = sysuserdeviceManager.findByHardNo(t);

			gzipCompress.data(data, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("getRoKeyData error", e);
		}
	}
*/



	/**
	 * 加密锁超级用户登录 TODO
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/superLogin", method = RequestMethod.POST)
	public void vipLogin(HttpServletRequest request,
                         HttpServletResponse response, SysCompany sysCompany, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysCompany company = companyManager.get(sysCompany.getEid());
		if (company.getAdminPassword().equals(
				sysCompany.getAdminPassword().trim())) {
			SysUser user = sysUserManager.findSuperUserByCompanyId(company
					.getEid());
			LoginUser loginUser = new LoginUser();
			loginUser.setCompany(company);
			loginUser.setUser(user);
			SysDepartment department = departmentManager.get(user.getDeptId());
			loginUser.setDepartment(department);
			CookieUtil.removeCookie(PlatformParam.cookie_l_token, request, response);
			HttpSession session = request.getSession();
			session.setAttribute(PlatformParam.USER_IN_SESSION, loginUser);
			result.put("result", 0);
			result.put("msg", "登录成功！");
		} else {
			result.put("result", 1);
			result.put("msg", "密码错误！");
		}
		gzipCompress.data(result, response);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "/home/register";
	}

	@RequestMapping(value = "/forgetPass", method = RequestMethod.GET)
	public String forgetPass(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String view = "/home/forgetPass";
		String dnsAddress = request.getServerName();
		SysCompany company = companyManager.findByDndAddress(dnsAddress);
		if(company != null) {
			String expire = company.getExpireTime();
			if(expire != null) {
				expire = expire.substring(0, 10);
				company.setExpireTime(expire);
			}
			model.addAttribute("company", company);
			view = "/home/forgetVip";
		}
		return view;
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String toError(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "/home/login";
	}

	@RequestMapping(value = "/right/less", method = RequestMethod.GET)
	public String toRightLedd(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "/home/right_less";
	}

	/**
	 * 获取用户是否有某个菜单或者功能的权限
	 *
	 * @param code
	 *            菜单或功能的code
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/special/right/{code}", method = RequestMethod.POST)
	public void getSpecialRight(@PathVariable String code,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		Map<String, SysMenu> menus = sysMenuManager.getAllWidthCodeKey();
		SysMenu menu = menus.get(code);
		if (menu != null) {
			int result = 0;
			int companyLevel = loginUser.getCompany().getLevel();
			if (companyLevel < menu.getCompanyLevel()) {
				result = 0;
			} else {
				HttpSession session = request.getSession();
				Map<String, Object> special = (Map<String, Object>) session
						.getAttribute("special_right");
				Long rightId = menu.getEid();
				if (special != null) {
					if (special.get(code) == null) {
						int num = sysMenuManager.getSpecialRight(loginUser,
								rightId);
						if (num > 0) {
							result = 1;
						} else {
							result = 0;
						}
						special.put(code, result);
						session.setAttribute("special_right", special);
					} else {
						result = Integer.parseInt(special.get(code).toString());
					}
				} else {
					special = new HashMap<>();
					int num = sysMenuManager
							.getSpecialRight(loginUser, rightId);
					if (num > 0) {
						result = 1;
					} else {
						result = 0;
					}
					special.put(code, result);
					session.setAttribute("special_right", special);
				}
				/*
				 * Integer userType = loginUser.getUser().getUserType();
				 * if(userType > 1) { result = 1; } else { }
				 */
			}
			if(loginUser.getUser().getUserType() == 2){
				result = 1;
			}
			gzipCompress.data(result, response);
		} else {
			gzipCompress.data(0, response);
		}
	}

	@RequestMapping(value = "/super/{type}", method = RequestMethod.GET)
	public String toSuper(@PathVariable String type,
			HttpServletRequest request, Model model) {
		model.addAttribute("type", type);
		return "/home/super_page";
	}

	@RequestMapping(value = "/super/{type}/list", method = RequestMethod.POST)
	public void superData(@PathVariable String type, int pageNumber,
			HttpServletRequest request, HttpServletResponse response) {

	}

	@RequestMapping(value = "/super/busMenu/{type}", method = RequestMethod.POST)
	public void getBusMenu(@PathVariable String type,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String superType = request.getParameter("superType");
			LoginUser loginUser = getLoginUser(request);
			List<Map<String, String>> data = sysMenuManager.getMenusByType(
					type, loginUser, superType);
			gzipCompress.data(data, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("getMenusByPid error", e);
		}
	}

	@RequestMapping(value = "/admin/settings/befor/check", method = RequestMethod.POST)
	public void checkAdminPassword(String password, HttpServletRequest request,
			HttpServletResponse response) {
		LoginUser loginUser = getLoginUser(request);
		SysCompany company = companyManager
				.get(loginUser.getCompany().getEid());
		if (company.getAdminPassword().equals(password)) {
			gzipCompress.data(1, response);
		} else {
			gzipCompress.data(0, response);
		}
	}

	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}

	/**
	 * 忘记密码
	 *
	 * @param t
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/forget", method = RequestMethod.POST)
	public void forGetPassword(SysUser t, HttpServletRequest request,
                               HttpServletResponse response) {
		try {

			boolean flag = true;
			SysUser sysuser = sysUserManager.findByAccount(t.getAccount());
			if (null == sysuser) {
				flag = false;
			} else {
				sysUserManager.updatePwdByPhone(t);
			}

			gzipCompress.data(flag, response);

		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("forGetPassword  error", e);
		}

	}

	/**
	 * download.htm?bz=qd_lock
	 */
	@RequestMapping("/download")
	public void software(String bz, HttpServletRequest request,
			HttpServletResponse response) {
		String file = getCJName(bz);
		InputStream inStream = null;
		OutputStream out = null;
		try {
			String path = request.getSession().getServletContext()
					.getRealPath("/")
					+ "download/" + file;
			int index = path.lastIndexOf("/");
			String fileName = file;
			if (index != -1) {
				fileName = path.substring(index + 1);
			}
			inStream = new FileInputStream(path);
			// 设置输出的格式
			response.reset();
			response.setContentType("application/octet-stream;charset=GBK");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "utf-8"));
			out = response.getOutputStream();
			// 循环取出流中的数据
			final byte[] content = new byte[1024];
			int len;
			while ((len = inStream.read(content)) > 0) {
				out.write(content, 0, len);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private String getCJName(String bz) {
		String result = "";
		if ("grbsctl5".equals(bz)) {
			result = "grbsctl5.exe";
		} else if ("SDRockeyCheck".equals(bz)) {
			result = "SDRockeyCheck.exe";
		} else if ("qd_lock".equals(bz)) {
			result = "qd_lock.rar";
		}
		return result;
	}

	@RequestMapping(value = "/setUnLead", method = RequestMethod.POST)
	public void setUnLead(HttpServletRequest request, Model model, HttpServletResponse response) {

		LoginUser loginUser = getLoginUser(request);
		HttpSession session = request.getSession();
		if(loginUser.getUser().getUserType().intValue() == 2) {
			companyManager.setUnLead(loginUser.getCompany().getEid());
		}
		loginUser.getCompany().setNeedLead(0);
		session.removeAttribute("USER_IN_SESSION");
		session.setAttribute("USER_IN_SESSION", loginUser);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String toHome(HttpServletRequest request, Model model, HttpServletResponse response) {

		String view = "/home/home";

		return view;
	}

	/**
	 * 跳转oa的app唤醒页
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/oaCrmWp", method = RequestMethod.GET)
	public String wpOa(HttpServletRequest request, Model model, HttpServletResponse response) {
		String view = "/wakeUp/oa";
		return view;
	}
	/**
	 * 跳转oa+(营销管理)的app唤醒页
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/oaPlusWp", method = RequestMethod.GET)
	public String wpPlus(HttpServletRequest request, Model model, HttpServletResponse response) {
		String view = "/wakeUp/oa_plus";
		return view;
	}

	/**
	 * 跳转oa+(营销管理)的app唤醒页
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/wpCrm", method = RequestMethod.GET)
	public String wpCrm(HttpServletRequest request, Model model, HttpServletResponse response) {
		String view = "/wakeUp/crm";
		return view;
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/home/notRole")
	public ModelAndView notRole(HttpServletRequest request,
								HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/home/right_less");
		return mv;
	}
}
