package com.system.service;

import com.system.dao.SysUserMenuDao;
import com.system.model.LoginUser;
import com.system.model.SysUserMenu;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author duwufeng
 * @date 2017-07-05 19:30:33
 *
 */
@Service
public class SysUserMenuManager extends BaseManager<SysUserMenu> {

	@Inject
	private SysUserMenuDao sysUserMenuDao;
	@Override
	protected BaseMybatis3Dao<SysUserMenu> getDao() {
		return sysUserMenuDao;
	}

	public void deleteAllRight(Long companyId, Long menuId, Long grantBy, Integer grantType) {
		sysUserMenuDao.deleteAllRight(companyId, menuId, grantBy, grantType);
	}

	public void deleteAllApprovalRight(Long companyId, Long menuId, Long approvalId, Long grantBy, Integer grantType) {
		sysUserMenuDao.deleteAllApprovalRight(companyId, menuId, approvalId, grantBy, grantType);
		
	}

	public List<Map<String, Object>> getMenuByLoginUser(LoginUser login) {
		List<Map<String, Object>> maps = sysUserMenuDao.findMenuByLoginUser(login);
		if(login.getUser().getUserType() != null && login.getUser().getUserType().intValue() == 2) {
			if(maps != null && maps.size() > 0) {
				for (Map<String, Object> map : maps) {
					map.put("isIn", "yes");
				}
			}
		}
		return maps;
	}

	public void updateByUserId(SysUserMenu sysUserMenu) {
		sysUserMenuDao.updateByUserId(sysUserMenu);
	}

	public boolean existUserTemReportRight(SysUserMenu sysUserMenu) {
		int num = sysUserMenuDao.existUserTemReportRight(sysUserMenu);
		if(num > 0) {
			return true;
		}
		return false;
	}
}