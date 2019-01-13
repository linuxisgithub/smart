package com.system.dao;

import com.alibaba.fastjson.JSONObject;
import com.system.model.SysMenu;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: SysMenuManager
 * @author duwufeng
 * @date 2017-06-27 11:14:50
 *
 */
@Repository
public class SysMenuDao extends BaseMybatis3Dao<SysMenu> {


	/**
	 * 测试三级菜单用
	 * @return
	 */
	public List<Map<String, Object>> getFristMenus_three(Integer userType) {
		return sqlSessionTemplate.selectList(generateStatement("getFristMenus_three"),userType);
	}
	/**
	 * 测试三级菜单用
	 * @return
	 */
	public List<Map<String, Object>> getMenusByPid_three(Long menuPid) {
		return sqlSessionTemplate.selectList(generateStatement("getMenusByPid_three"),menuPid);
	}

	/**
	 * 食阁管理公司系统菜单 食阁管理 查询出登录的食阁管理公司下的食阁当做二级菜单
	 * @param companyId
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getSgMenus(Long companyId,Long pid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("pid", pid);
		return sqlSessionTemplate.selectList(generateStatement("getSgMenus"),param);
	}

	/**
	 * 摊位系统菜单 把自己当做二级菜单
	 * @param companyId
	 * @param eid
	 * @return
	 */
	public List<Map<String, Object>> getCurrentTwMenu(Long companyId, Long eid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("eid", eid);
		return sqlSessionTemplate.selectList(generateStatement("getCurrentTwMenu"),param);
	}
	/**
	 * 食阁拥有者系统菜单 查找绑定的食阁当做菜单
	 * @param companyId
	 * @param eid
	 * @return
	 */
	public List<Map<String, Object>> getSgyyzMenu(Long companyId, Long eid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("eid", eid);
		return sqlSessionTemplate.selectList(generateStatement("getSgyyzMenu"),param);
	}

	public List<Map<String, String>> getFristMenus(Long userId, Long deptId,
			Long companyId, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("deptId", deptId);
		param.put("companyId", companyId);
		param.put("companyLevel", companyLevel);
		return sqlSessionTemplate.selectList(generateStatement("getFristMenus"), param);
	}

	public List<Map<String, String>> getMenusByPid(Long menuPid, Long userId, Long deptId,
			Long companyId, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("menuPid", menuPid);
		param.put("deptId", deptId);
		param.put("companyId", companyId);
		param.put("companyLevel", companyLevel);
		return sqlSessionTemplate.selectList(generateStatement("getMenusByPid"), param);
	}

	public List<Map<String, String>> getAdminFristMenus(int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyLevel", companyLevel);
		return sqlSessionTemplate.selectList(generateStatement("getAdminFristMenus"), param);
	}

	public List<Map<String, String>> getAdminMenusByPid(Long menuPid, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menuPid", menuPid);
		param.put("companyLevel", companyLevel);
		return sqlSessionTemplate.selectList(generateStatement("getAdminMenusByPid"), param);
	}

	public List<Map<String, Object>> getSysSetFirstMenus(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("getSysSetFirstMenus"), param);
	}

	public List<Map<String, Object>> getSysSetMenusByPid(Long menuPid, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menuPid", menuPid);
		param.put("companyLevel", companyLevel);
		return sqlSessionTemplate.selectList(generateStatement("getSysSetMenusByPid"), param);
	}

	public List<Map<String, Object>> getSysSetSecMenusByPid(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("getSysSetSecMenusByPid"), param);
	}

	public List<Map<String, Object>> getSettingsRightList(Long companyId, Long pid, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("companyLevel", companyLevel);
		param.put("pid", pid);
		return sqlSessionTemplate.selectList(generateStatement("getSettingsRightList"), param);
	}

	public List<String> getMenuChildIds(Long pid) {
		return sqlSessionTemplate.selectList(generateStatement("getMenuChildIds"), pid);
	}

	public int getSpecialRight(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(generateStatement("getSpecialRight"), param);
	}

	public List<Map<String, String>> getWorkMenu(Long userId, Long deptId,
			Long companyId, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("deptId", deptId);
		param.put("companyId", companyId);
		param.put("companyLevel", companyLevel);
		return sqlSessionTemplate.selectList(generateStatement("getWorkMenu"), param);
	}

	public List<Map<String, String>> getMainMenuWithCj(int level) {
		return sqlSessionTemplate.selectList(generateStatement("getMainMenuWithCj"), level);
	}

	public List<Map<String, String>> getSubMenu(int level,String superType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyLevel", level);
		param.put("superType", superType);
		return sqlSessionTemplate.selectList(generateStatement("getSubMenu"), param);
	}

	public List<Map<String, Object>> getTwMenus(Long companyId, Long pid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("pid", pid);
		return sqlSessionTemplate.selectList(generateStatement("getTwMenus"),param);
	}
	public List<Map<String,String>> findMenusBySubPid(int userType,String subPid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userType", userType);
		param.put("subPid", subPid);
		return sqlSessionTemplate.selectList(generateStatement("findMenusBySubPid"), param);
	}

}