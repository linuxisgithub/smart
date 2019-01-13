package com.system.dao;

import com.system.model.LoginUser;
import com.system.model.SysUserMenu;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author duwufeng
 * @date 2017-07-05 19:30:33
 *
 */
@Repository
public class SysUserMenuDao extends BaseMybatis3Dao<SysUserMenu> {

	/**
	 * 删除正常授权的权限或者因为发布而得到的权限
	 *
	 * @param companyId
	 * @param menuId
	 * @param grantBy
	 * @param grantType
	 */
	public void deleteAllRight(Long companyId, Long menuId, Long grantBy,
			Integer grantType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("menuId", menuId);
		param.put("grantBy", grantBy);
		param.put("grantType", grantType);
		sqlSessionTemplate.update(generateStatement("deleteAllRight"), param);
	}

	/**
	 * 删除因为批审而得到的权限
	 *
	 * @param companyId
	 * @param menuId
	 * @param approvalId
	 * @param grantBy
	 * @param grantType
	 */
	public void deleteAllApprovalRight(Long companyId, Long menuId,
			Long approvalId, Long grantBy, Integer grantType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("menuId", menuId);
		param.put("approvalId", approvalId);
		param.put("grantBy", grantBy);
		param.put("grantType", grantType);
		sqlSessionTemplate.update(generateStatement("deleteAllApprovalRight"),
				param);

	}

	public List<Map<String, Object>> findMenuByLoginUser(LoginUser login) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", login.getUser().getEid());
		if(login.getDepartment() != null){
			param.put("deptId", login.getDepartment().getEid());
		}
		param.put("companyId", login.getCompany().getEid());

		return sqlSessionTemplate.selectList(
				generateStatement("findMenuByLoginUser"), param);
	}

	public void updateByUserId(SysUserMenu sysUserMenu) {
		sqlSessionTemplate.update(generateStatement("updateByUserId"),sysUserMenu);
	}

	public int existUserTemReportRight(SysUserMenu sysUserMenu) {
		return sqlSessionTemplate.selectOne("existUserTemReportRight", sysUserMenu);
	}

}