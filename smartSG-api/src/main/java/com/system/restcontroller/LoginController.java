package com.system.restcontroller;

import com.system.model.LoginUser;
import com.system.model.SysUser;
import com.system.service.SysUserManager;
import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;
import javacommon.util.HttpStatusCode;
import javacommon.util.PlatformParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆接口
 * @author JQZ
 *
 */
@RestController
public class LoginController extends BaseSpringController<SysUser> {

Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private SysUserManager sysUserManager;
	
	@Override
	protected BaseManager<SysUser> getManager() {
		return sysUserManager;
	}
	
	
	/**
	 * 登陆
	 * @param user
	 * @param br
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public void login(SysUser user,BindingResult br,
			HttpServletRequest request,HttpServletResponse response) {
		String ip = PlatformParam.getRemoteIp(request);
		logger.warn("[" + user.getAccount() + "]请求登录   ip:" + ip);
		try {
			String validator = getErrors(br);
			if (StringUtils.isNotBlank(validator)) {
				gzipCompress.validator(validator, response);
				return;
			}
			LoginUser loginUser = sysUserManager.findLogin(user);
			if (loginUser != null) {
				Map<String, Object> result = new HashMap<>();
				result.put("token", loginUser.getToken());
				result.put("loginUser", loginUser.getUser());
				gzipCompress.data(result, response);
			} else {
				gzipCompress.fail(HttpStatusCode.LOGIN_OVER_ERROR,
						"login.error", response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("login error", e);
		}
	}
	
	
}
