package com.system.controller;

import com.system.model.LoginUser;
import com.system.model.SysSettings;
import com.system.service.SysSettingsManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author duwufeng
 * @date 2017-07-14 15:29:37
 *
 */
@Controller
@RequestMapping("/sysSettings")
public class SysSettingsController extends BaseBsController<SysSettings> {

	@Inject
	private SysSettingsManager sysSettingsManager;
	
	@RequestMapping("/menu/confirm")
	public void menuConfirm(Long confirmId, Long confirmPid,
			HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		int result = sysSettingsManager.menuConfirm(user, confirmId, confirmPid);
		gzipCompress.data(result, response);
	}

	@Override
	protected BaseManager<SysSettings> getManager() {
		return sysSettingsManager;
	}

}