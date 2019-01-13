package com.system.service;

import com.system.dao.SysSettingsDao;
import com.system.model.LoginUser;
import com.system.model.SysSettings;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author duwufeng
 * @date 2017-07-14 15:29:37
 *
 */
@Service
public class SysSettingsManager extends BaseManager<SysSettings> {

	@Inject
	private SysSettingsDao sysSettingsDao;
	
	@Inject
	private SysMenuManager sysMenuManager;
	
	@Override
	protected BaseMybatis3Dao<SysSettings> getDao() {
		return sysSettingsDao;
	}
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public int menuConfirm(LoginUser user, Long confirmId, Long confirmPid) {
		int result = 0;
		boolean hasConfirm = sysSettingsDao.hasMenuConfirm(user.getCompany().getEid(), confirmId, "s_menu");
		if(hasConfirm == false) {
			if(confirmPid != null && confirmPid.longValue() != 1) {
				List<String> childIds = sysMenuManager.getMenuChildIds(confirmPid);
				int length = childIds.size();
				long confirmNum = sysSettingsDao.getConfirmNum(user.getCompany().getEid(), childIds);
				if(confirmNum >= length - 1) {
					SysSettings settings2 = new SysSettings();
					settings2.setBid(confirmPid);
					settings2.setValue("1");
					settings2.setCode("s_menu");
					settings2.setCompanyId(user.getCompany().getEid());
					hasConfirm = sysSettingsDao.hasMenuConfirm(user.getCompany().getEid(), confirmPid, "s_menu");
					if(hasConfirm == false) {
						sysSettingsDao.save(settings2);
					}
					result = 1;
				}
			}
			SysSettings settings = new SysSettings();
			settings.setBid(confirmId);
			settings.setValue("1");
			settings.setCode("s_menu");
			settings.setCompanyId(user.getCompany().getEid());
			sysSettingsDao.save(settings);
		}
		return result;
	}

}