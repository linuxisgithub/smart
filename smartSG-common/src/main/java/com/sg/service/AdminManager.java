package com.sg.service;

import com.system.model.LoginUser;
import com.system.model.SysCompany;
import com.system.service.SysMenuManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminManager {

	@Inject
	private SysMenuManager sysMenuManager;
	
	public Map<String, Object> getSysSetFirstMenus(LoginUser loginUser) {
		SysCompany company = loginUser.getCompany();
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> list = sysMenuManager.getSysSetFirstMenus(company.getEid(), company.getLevel());
		result.put("first", list);
		for (Map<String, Object> menu : list) {
			Long menuId = (Long)menu.get("id");
			List<Map<String, Object>> childs = sysMenuManager.getSysSetSecMenusByPid(company.getEid(), menuId, company.getLevel());
			menu.put("childNum", childs.size());
			result.put(menuId.toString(), childs);
		}
		return result;
	}

	public List<Map<String, Object>> getSysSetMenusByPid(Long pid, LoginUser loginUser) {
		SysCompany company = loginUser.getCompany();
		List<Map<String, Object>> result = sysMenuManager.getSysSetMenusByPid(pid, company.getLevel());
		return result;
	}

	
}
