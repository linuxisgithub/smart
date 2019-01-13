package com.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.dao.SysMenuDao;
import com.system.dao.SysUserMenuDao;
import com.system.model.*;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.PlatformParam;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @ClassName: SysMenuManager
 * @author duwufeng
 * @date 2017-06-27 11:14:50
 *
 */
@Service
public class SysMenuManager extends BaseManager<SysMenu> {

	@Inject
	private SysMenuDao sysMenuDao;
	@Inject
	private SysUserMenuDao sysUserMenuDao;
	@SuppressWarnings("rawtypes")
	@Inject
	protected RedisTemplate redisTemplate;
	
	@Override
	protected BaseMybatis3Dao<SysMenu> getDao() {
		return sysMenuDao;
	}

	/**
	 * 测试三级菜单用
	 * @return
	 */
	public Map<String, Object> getSysSetFirstMenus_three(LoginUser loginUser) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> list = sysMenuDao.getFristMenus_three(loginUser.getUser().getUserType());
		result.put("first", list);
		for (Map<String, Object> menu : list) {
			Long menuId = (Long)menu.get("id");
            List<Map<String, Object>> childs;
			if(menuId == 203){//食阁管理公司系统菜单 食阁管理 查询出登录的食阁管理公司下的食阁当做二级菜单
                childs = sysMenuDao.getSgMenus(loginUser.getCompany().getEid(),loginUser.getDepartment().getEid());
            }else if(menuId == 301){
            	childs = sysMenuDao.getTwMenus(loginUser.getCompany().getEid(),loginUser.getDepartment().getEid());
            }else if(menuId == 401){
				childs = sysMenuDao.getCurrentTwMenu(loginUser.getCompany().getEid(),loginUser.getDepartment().getEid());
			}else if(menuId == 501){
				childs = sysMenuDao.getSgyyzMenu(loginUser.getCompany().getEid(),loginUser.getDepartment().getEid());
			}else{
                childs = sysMenuDao.getMenusByPid_three(menuId);
            }
			menu.put("childNum", childs.size());
			result.put(menuId.toString(), childs);
		}
		return result;
	}

	/**
	 * 获取第一层菜单
	 * @return
	 */
	public List<Map<String, String>> getFristMenus(LoginUser loginUser) {
		SysUser user = loginUser.getUser();
		SysCompany company = loginUser.getCompany();
		int userType = user.getUserType();
		List<Map<String, String>> result = null;
		if(userType == 1) {
			// 普通用户
			result = sysMenuDao.getFristMenus(user.getEid(), user.getDeptId(), user.getCompanyId(), company.getLevel());
		} else {
			// 管理员
			result = sysMenuDao.getFristMenus(user.getEid(), user.getDeptId(), user.getCompanyId(), company.getLevel());
//			result = sysMenuDao.getAdminFristMenus(company.getLevel());
		}
		return result;
	}
	
	/**
	 * 根据父菜单ID获取子菜单
	 * @param menuPid
	 * @param loginUser
	 * @return
	 */
	public List<Map<String, String>> getMenusByPid(Long menuPid, LoginUser loginUser) {
		SysUser user = loginUser.getUser();
		SysCompany company = loginUser.getCompany();
		int userType = user.getUserType();
		List<Map<String, String>> result = new ArrayList<>();
		if (menuPid.toString().substring(0,3).equals("203")){
            Map<String, String> menu = new HashMap<>();
            menu.put("id",menuPid.toString()+"01");
            menu.put("pid",menuPid.toString());
            menu.put("name","食阁装修");
            menu.put("code","");
            menu.put("url","/sg/to/renovation.htm?id="+menuPid.toString().substring(3));
            menu.put("icon","");
            menu.put("level","3");
            menu.put("menuShow","1");
            menu.put("hasRight","1");
            result.add(menu);
            Map<String, String> menu2 = new HashMap<>();
            menu2.put("id",menuPid.toString()+"02");
            menu2.put("pid",menuPid.toString());
            menu2.put("name","食阁报表");
            menu2.put("code","");
            menu2.put("url","/sg/to/report.htm?id="+menuPid.toString().substring(3));
            menu2.put("icon","");
            menu2.put("level","3");
            menu2.put("menuShow","1");
            menu2.put("hasRight","1");
            result.add(menu2);
        } else if (menuPid.toString().substring(0,3).equals("301")){
        	List<Map<String,String>> menus = sysMenuDao.findMenusBySubPid(userType,menuPid.toString().substring(0,3));
        	for (int i = 0; i < menus.size(); i++) {
        		Map<String,String>menu = menus.get(i);
        		menu.put("id", menuPid.toString().concat("0"+(i+1)));
        		menu.put("pid",menuPid.toString());
        		menu.put("url",menu.get("url").concat("?id=").concat(menuPid.toString().substring(3)).concat("&type=sg"));
			}
        	result.addAll(menus);
        } else if (menuPid.toString().substring(0,3).equals("401")){
			List<Map<String,String>> menus = sysMenuDao.findMenusBySubPid(3,"301");
			for (int i = 0; i < menus.size(); i++) {
				Map<String,String>menu = menus.get(i);
				menu.put("id", menuPid.toString().concat("0"+(i+1)));
				menu.put("pid",menuPid.toString());
                menu.put("url",menu.get("url").concat("?id=").concat(menuPid.toString().substring(3)).concat("&type=tw"));
			}
			result.addAll(menus);
		} else if (menuPid.toString().substring(0,3).equals("501")){
			Map<String, String> menu = new HashMap<>();
			menu.put("id",menuPid.toString()+"01");
			menu.put("pid",menuPid.toString());
			menu.put("name","食阁报表");
			menu.put("code","");
			menu.put("url","/sg/to/report.htm?id="+menuPid.toString().substring(3));
			menu.put("icon","");
			menu.put("level","3");
			menu.put("menuShow","1");
			menu.put("hasRight","1");
			result.add(menu);
			Map<String, String> menu2 = new HashMap<>();
			menu2.put("id",menuPid.toString()+"02");
			menu2.put("pid",menuPid.toString());
			menu2.put("name","摊位报表");
			menu2.put("code","");
			menu2.put("url","/tw/to/report.htm?id="+menuPid.toString().substring(3));
			menu2.put("icon","");
			menu2.put("level","3");
			menu2.put("menuShow","1");
			menu2.put("hasRight","1");
			result.add(menu2);
		} else {
            result = sysMenuDao.getMenusByPid(menuPid, user.getEid(), user.getDeptId(), user.getCompanyId(), company.getLevel());
        }
		return result;
	}

	public List<Map<String, Object>> getSysSetFirstMenus(Long conpanyId, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyLevel", companyLevel);
		param.put("conpanyId", conpanyId);
		return sysMenuDao.getSysSetFirstMenus(param);
	}

	public List<Map<String, Object>> getSysSetSecMenusByPid(Long conpanyId, Long menuPid, int companyLevel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menuPid", menuPid);
		param.put("companyLevel", companyLevel);
		param.put("conpanyId", conpanyId);
		return sysMenuDao.getSysSetSecMenusByPid(param);
	}
	
	public List<Map<String, Object>> getSysSetMenusByPid(Long menuPid, int companyLevel) {
		return sysMenuDao.getSysSetMenusByPid(menuPid, companyLevel);
	}

	public List<Map<String, Object>> getSettingsRightList(Long companyId, Long pid, int companyLevel) {
		return sysMenuDao.getSettingsRightList(companyId, pid, companyLevel);
	}

	public List<String> getMenuChildIds(Long pid) {
		return sysMenuDao.getMenuChildIds(pid);
	}

	private SysUserMenu copySysUserMenu(SysUserMenu data) {
		SysUserMenu result = new SysUserMenu();
		result.setMenuId(data.getMenuId());
		result.setMenuName(data.getMenuName());
		result.setCompanyId(data.getCompanyId());
		result.setKind(data.getKind());
		return result;
	}

	@Transactional
	public void saveRight(LoginUser loginUser, SysUserMenu data) {
		Long grantBy = data.getMenuId();
		sysUserMenuDao.deleteAllRight(loginUser.getCompany().getEid(), data.getMenuId(), grantBy, 0);
		data.setCompanyId(loginUser.getCompany().getEid());
		List<SysUserMenu> saveData = new ArrayList<>();
		String useUsers = data.getUseUsers();
		List<JSONObject> jsonObjects = JSONArray.parseArray(useUsers, JSONObject.class);
		for (JSONObject jsonObject : jsonObjects) {
			SysUserMenu copy = copySysUserMenu(data);
			copy.setUseId(jsonObject.getLong("useId"));
			copy.setUseDeptName(jsonObject.getString("useDeptName"));
			copy.setUseName(jsonObject.getString("useName"));
			copy.setGrantBy(grantBy);
			copy.setGrantType(0);
			saveData.add(copy);
			if(saveData.size() >= 100) {
				sysUserMenuDao.saveInitInsert(saveData);
				saveData.clear();
			}
		}
		if(saveData.size() > 0) {
			sysUserMenuDao.saveInitInsert(saveData);
			saveData.clear();
		}
		Long menuId = data.getMenuId();
		String menuName = data.getMenuName();
		
		boolean falg = false;
		if(menuId == 10111) {
			menuId = 10101L;
			menuName = "公司公告";
			falg = true;
		} else if(menuId == 10112) {
			menuId = 10102L;
			menuName = "部门通知";
			falg = true;
		} else if(menuId == 10113) {
			menuId = 10103L;
			menuName = "文件中心";
			falg = true;
		} else if(menuId == 1009 || menuId == 1010 || menuId == 1011 || menuId == 1012 || menuId == 1013) {
			menuId = 10203L;
			menuName = "事务申请";
			falg = true;
		} else if(menuId == 1018 || menuId == 1019 || menuId == 1020 || menuId == 1021) {
			menuId = 10403L;
			menuName = "人事事务";
			falg = true;
		}
		if(falg == true) {
			data.setMenuId(menuId);
			data.setMenuName(menuName);
			sysUserMenuDao.deleteAllRight(loginUser.getCompany().getEid(), data.getMenuId(), grantBy, 1);
			for (JSONObject jsonObject : jsonObjects) {
				SysUserMenu copy = copySysUserMenu(data);
				copy.setUseId(jsonObject.getLong("useId"));
				copy.setUseDeptName(jsonObject.getString("useDeptName"));
				copy.setUseName(jsonObject.getString("useName"));
				copy.setGrantBy(grantBy);
				copy.setGrantType(1);
				saveData.add(copy);
				if(saveData.size() >= 100) {
					sysUserMenuDao.saveInitInsert(saveData);
					saveData.clear();
				}
			}
			if(saveData.size() > 0) {
				sysUserMenuDao.saveInitInsert(saveData);
			}
		}
	}

	/**
	 * 判断用户是否有权限
	 * @param code
	 * @param loginUser
	 * @return 0=无权限，1=有权限
	 */
	public int hasRightByCode(String code, LoginUser loginUser) {
		int result = 0;
		Map<String, SysMenu> menus = this.getAllWidthCodeKey();
		SysMenu menu = menus.get(code);
		if(menu != null) {
			int companyLevel = loginUser.getCompany().getLevel();
			if(companyLevel < menu.getCompanyLevel()) {
				result = 0;
			} else {
				Integer userType = loginUser.getUser().getUserType();
				if(userType > 1) {
					result = 1;
				} else {
					Long rightId = menu.getEid();
					int num = this.getSpecialRight(loginUser, rightId);
					if(num > 0) {
						result = 1;
					} else {
						result = 0;
					}
				}
			}
		}
		return result;
	}
	/**
	 * 获取所有的菜单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, SysMenu> getAllWidthCodeKey() {
		//redisTemplate.delete(PlatformParam.REDIS_MENU_KEY);
		String redisResult = (String) redisTemplate.opsForValue().get(PlatformParam.REDIS_MENU_KEY);
		Map<String, SysMenu> result = new HashMap<>();
		List<SysMenu> sysMenus = null;
		if(redisResult == null) {
			sysMenus = sysMenuDao.findAll();
			redisTemplate.opsForValue().set(PlatformParam.REDIS_MENU_KEY,
					JSON.toJSONString(sysMenus), 7L, TimeUnit.DAYS);
		} else {
			sysMenus = JSONArray.parseArray(redisResult, SysMenu.class);
		}
		for (SysMenu sysMenu : sysMenus) {
			result.put(sysMenu.getMenuCode(), sysMenu);
		}
		return result;
	}
	/**
	 * 获取所有的菜单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Long, SysMenu> getAllWidthEidKey() {
		String redisResult = (String) redisTemplate.opsForValue().get(PlatformParam.REDIS_MENU_KEY);
		Map<Long, SysMenu> result = new HashMap<>();
		List<SysMenu> sysMenus = null;
		if(redisResult == null) {
			sysMenus = sysMenuDao.findAll();
			redisTemplate.opsForValue().set(PlatformParam.REDIS_MENU_KEY,
					JSON.toJSONString(sysMenus), 7L, TimeUnit.DAYS);
		} else {
			sysMenus = JSONArray.parseArray(redisResult, SysMenu.class);
		}
		for (SysMenu sysMenu : sysMenus) {
			result.put(sysMenu.getEid(), sysMenu);
		}
		return result;
	}
	/**
	 * 获取用户是否有menuId的权限
	 * @param loginUser
	 * @param menuId
	 * @return
	 */
	public int getSpecialRight(LoginUser loginUser, Long menuId) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", loginUser.getCompany().getEid());
		param.put("userId", loginUser.getUser().getEid());
		param.put("deptId", loginUser.getDepartment().getEid());
		param.put("menuId", menuId);
		param.put("companyLevel", loginUser.getCompany().getLevel());
		return sysMenuDao.getSpecialRight(param);
	}

	public List<Map<String, String>> getWorkMenu(LoginUser loginUser) {
		SysUser user = loginUser.getUser();
		SysCompany company = loginUser.getCompany();
		int userType = user.getUserType();
		List<Map<String, String>> result = null;
		if(userType == 1) {
			// 普通用户
			result = sysMenuDao.getWorkMenu(user.getEid(), user.getDeptId(), user.getCompanyId(), company.getLevel());
		} else {
			// 管理员
			result = sysMenuDao.getWorkMenu(user.getEid(), user.getDeptId(), user.getCompanyId(), company.getLevel());
		}
		return result;
	}

	public List<Map<String, String>> getMenusByType(String type,
			LoginUser loginUser,String superType) {
		SysCompany company = loginUser.getCompany();
		List<Map<String, String>> list = null;
		if("main".equals(type)){
			list = sysMenuDao.getMainMenuWithCj(company.getLevel());
		}else{
			list = sysMenuDao.getSubMenu(company.getLevel(),superType);
		}
		return list;
	}
	
}