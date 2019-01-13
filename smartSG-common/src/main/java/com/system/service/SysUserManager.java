package com.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sg.service.AnnexManager;
import com.system.dao.SysUserDao;
import com.system.model.LoginUser;
import com.system.model.SysCompany;
import com.system.model.SysDepartment;
import com.system.model.SysUser;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.UUID;
import javacommon.util.*;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author duwufeng
 * @date 2017-06-28 09:36:46
 *
 */
@Service
public class SysUserManager extends BaseManager<SysUser> {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private SysUserDao sysUserDao;

	@Inject
	private SysCompanyManager companyManager;

	@Inject
	private SysDictionaryManager dictionaryManager;

	@Inject
	private SysDepartmentManager departmentManager;

	@Inject
	private Properties propertySetting;

	@Inject
	private SysUserMenuManager sysUserMenuManager;

	@Inject
	private SysDepartmentManager sysDepartmentManager;

	@Inject
	private AnnexManager annexManager;

	@Override
	protected BaseMybatis3Dao<SysUser> getDao() {
		return sysUserDao;
	}

	@SuppressWarnings("rawtypes")
	@Inject
	protected RedisTemplate redisTemplate;

	public void resiter(Long companyId) {
		SysUser sysUser = new SysUser();
		sysUser.setAccount("manager");
	}

	public SysUser findByAccount(String account) {
		return sysUserDao.findByAccount(account);
	}

	public Map<String, Object> findByImAccount(String imAccount, Long companyId) {
		return sysUserDao.findByImAccount(imAccount, companyId);
	}

	public void findByType(Paginator<SysUser> paginator) {
		sysUserDao.findByType(paginator);
	}

	public List<SysUser> findAllByCompanyId(LoginUser loginUser) {
		return sysUserDao
				.findAllByCompanyId(loginUser.getUser().getCompanyId());
	}

	public boolean isExistAccount(Long companyId, String account, Long eid) {
		Map<String, Object> param = new HashMap<>();
		// 不同公司account也不能一样
		// param.put("companyId", companyId);
		param.put("account", account);
		param.put("eid", eid);
		return sysUserDao.isExistAccount(param);
	}

	public List<Map<String, Object>> getSysUserByLevel(Long companyId, int level) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", companyId);
		param.put("level", level);
		return sysUserDao.getSysUserByLevel(param);
	}

	public void findDdxByType(Paginator<SysUser> paginator) {
		sysUserDao.findDdxByType(paginator);
	}

	public List<Map<String, Object>> findUsersByDeptId(LoginUser loginUser,
			Long deptId) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", loginUser.getCompany().getEid());
		param.put("deptId", deptId);
		return sysUserDao.findUsersByDeptId(param);
	}

	public List<Map<String, Object>> findUsersBySubDeptCode(
			LoginUser loginUser, Long deptId) {
		Map<String, Object> param = new HashMap<>();
		String code = loginUser.getDepartment().getCode();
		String subCode = StringUtil.subCode(code);
		param.put("companyId", loginUser.getCompany().getEid());
		param.put("subCode", subCode);
		return sysUserDao.findUsersBySubDeptCode(param);
	}

	public List<Map<String, Object>> findUsersByDeptIdAndManager(
			LoginUser loginUser, Long deptId) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", loginUser.getCompany().getEid());
		param.put("deptId", deptId);
		return sysUserDao.findUsersByDeptIdAndManager(param);
	}

	@Transactional
	public int saveUser(LoginUser loginUser, SysUser user)
			throws RuntimeException {
		int result = 0;
		SysCompany company = companyManager
				.get(loginUser.getCompany().getEid());
		String birthDateString = user.getBirthDate();
		if (birthDateString != null && birthDateString.trim().length() > 0) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Date birthDate = df.parse(birthDateString);
				Date nowDate = new Date();
				int age = DateUtil.yearsBetween(nowDate, birthDate);
				user.setAge(age);
			} catch (Exception e) {
			}
		}
		//String imAccount = "oa_" + MobileCode.generate4NumCode() + user.getAccount();
		String imAccount = "smartSG_" + MobileCode.generate4NumCode() + "_" + user.getAccount();
		/*if (company.getCaseStatus() == 2) {
			// 例子账状态下的IM账号
			imAccount += "_case";
		}*/
		user.setImAccount(imAccount);
		user.setOriginAddr(user.getOriginAddr() == null ? "" : user
				.getOriginAddr());
		user.setEmail(user.getEmail() == null ? "" : user.getEmail());
		user.setQq(user.getQq() == null ? "" : user.getQq());// LinkAddress
		user.setLinkAddress(user.getLinkAddress() == null ? "" : user
				.getLinkAddress());
		user.setWechat(user.getWechat() == null ? "" : user.getWechat());
		user.setStatus(user.getStatus() == null ? 1 : user.getStatus());
		user.setImStatus(user.getImStatus() == null ? 1 : user.getImStatus());
		user.setJobStatus(user.getJobStatus() == null ? 3 : user.getJobStatus());
		user.setContract(user.getContract() == null ? 1 : user.getContract());
		if (user.getDeptCode() == null) {
			SysDepartment dept = sysDepartmentManager.get(user.getDeptId());
			user.setDeptCode(dept.getCode());
			user.setDeptName(dept.getName());
		}

		sysUserDao.save(user);

	/*	String post = null;
		String url = propertySetting.getProperty("im_register_url").toString();
		Map<String, Object> imrestParam = new HashMap<>();
		String appId = propertySetting.getProperty("local_project_appid").toString();
		String appKey = propertySetting.getProperty("local_project_appkey").toString();
		imrestParam.put("appId", appId);
		imrestParam.put("appKey", appKey);
		imrestParam.put("account", imAccount);
		imrestParam.put("password", user.getPassword());
		imrestParam.put("name", user.getName() == null ? "" : user.getName());
		imrestParam.put("icon", user.getIcon() == null ? "" : user.getIcon());
		imrestParam.put("companyId", user.getCompanyId());
		try {
			post = HttpClientUtil.post(url, imrestParam);
			JSONObject response = JSON.parseObject(post);
			String status = response.get("status").toString();
			if(!"200".equals(status)){
				if("501".equals(status)){
					throw new RuntimeException("appId已失效");
				}else{
					throw new RuntimeException("IM注册失败");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("IM注册失败", e);
		}*/
/*		String url = propertySetting.getProperty("imRestful") + "register";
		Map<String, Object> imrestParam = new HashMap<>();
		imrestParam.put("account", imAccount);
		imrestParam.put("password", user.getPassword());
		imrestParam.put("name", user.getName() == null ? "" : user.getName());
		imrestParam.put("icon", user.getIcon() == null ? "" : user.getIcon());
		imrestParam.put("companyId", user.getCompanyId());
		String post = null;
		JSONObject response = null;
		try {
			post = HttpClientUtil.post(url, imrestParam);
			logger.warn("注册到IM----IM返回：" + post);
			response = (JSONObject) JSON.parse(post);
		} catch (Exception e) {
			logger.error("HttpClientUtil.post:" + url, e);
		}
		logger.warn("注册到IM服务:地址:" + url + "\n参数：" + imrestParam + "\n返回:"
				+ response);
		if (response == null) {
			logger.warn("注册到IM服务请求失败。。。");
			throw new RuntimeException("IM注册失败");
		} else {
			logger.warn("注册到IM----IM返回：" + response);
			if (response.getInteger("status") != HttpStatus.SC_OK
					&& response.getInteger("status") != HttpStatus.SC_GONE) {
				throw new RuntimeException("IM注册失败");
			}
		}*/

		return result;
	}

	@Transactional
	public int updateUser(LoginUser loginUser, SysUser user) {
		int result = 0;
		if (user.getLevel() != null && user.getStatus() != null) {
			if (user.getLevel() == 3 && user.getStatus() != 1) {
				// 不能停用管理员账号
				return 3;
			}
		}
		// SysCompany company =
		// companyManager.get(loginUser.getCompany().getEid());
		SysCompany company = loginUser.getCompany();
		String birthDateString = user.getBirthDate();
		if (birthDateString != null && birthDateString.trim().length() > 0) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Date birthDate = df.parse(birthDateString);
				Date nowDate = new Date();
				int age = DateUtil.yearsBetween(nowDate, birthDate);
				user.setAge(age);
			} catch (Exception e) {
			}
		}
		SysUser old = sysUserDao.get(user.getEid());
		if ("".equals(user.getPassword())
				|| "******".equals(user.getPassword())
				|| user.getPassword() == null) {
			user.setPassword(old.getPassword());
		}
		if (user.getDeptId() != null && user.getDeptId().longValue() == 0) {
			user.setDeptId(old.getDeptId());
			user.setDeptName(old.getDeptName());
			user.setDeptCode(old.getDeptCode());
		}
		user.setImAccount(old.getImAccount());

		/*if (user.getStatus() != null && user.getStatus() == 1
				&& old.getStatus() != 1) {
			int useNum = sysUserDao
					.countUseNum(loginUser.getCompany().getEid()); // 基本用户数
			int extraNum = extraUserNumManager.findExtraUserNum(company
					.getEid()); // 额外购买的用户数
			if (useNum >= company.maxUserNum() + extraNum) {
				// 启用的用户超过限额
				return 1;
			}
		}
		if (user.getImStatus() != null && user.getImStatus() == 1
				&& old.getImStatus() != 1) {
			int imUseNum = sysUserDao.countImUseNum(loginUser.getCompany()
					.getEid());
			if (imUseNum >= company.maxImUserNum()) {
				// 启用的IM用户超过限额
				return 2;
			}
		}*/

		sysUserDao.update(user);

		/*if (!old.getPassword().equals(user.getPassword())) {
			// 更新IM密码
			String ImAccount = old.getImAccount();
			String url = propertySetting.getProperty("imRestful") + "register/"
					+ ImAccount + "/" + user.getPassword();
			logger.warn("修改IM密码：" + ImAccount);
			String put = null;
			JSONObject response = null;
			try {
				put = HttpClientUtil.put(url, null);
				logger.warn("修改IM密码----IM返回：" + put);
				response = JSONObject.parseObject(put);
			} catch (Exception e) {
				logger.error("IM请求失败：" + put, e);
			}
			if (response == null) {
				logger.warn("修改IM密码请求失败。。。");
				throw new RuntimeException("修改IM密码失败");
			} else {
				logger.warn("修改IM密码----IM返回：" + response);
				if (response.getInteger("status") != HttpStatus.SC_OK
						&& response.getInteger("status") != HttpStatus.SC_GONE) {
					throw new RuntimeException("修改IM密码失败");
				}
			}
		}*/
		updateImInfo(user, old);

		return result;
	}

	@Transactional
	public void updateImInfo(SysUser user, SysUser old) {
		// 修改IM
		String updateUrl = propertySetting.getProperty("imRestful") + "user";
		Map<String, Object> param = new HashMap<>();
		param.put("account", old.getImAccount());
		if (StringUtils.isNotBlank(user.getTelephone())
				&& !user.getTelephone().equals(old.getTelephone())) {
			param.put("phone", user.getTelephone());
		} else {
			param.put("phone", old.getTelephone());
		}
		if (StringUtils.isNotBlank(user.getName())
				&& !user.getName().equals(old.getName())) {
			param.put("name", user.getName());
		} else {
			param.put("name", old.getName());
		}
		if (StringUtils.isNotBlank(user.getIcon())
				&& !user.getIcon().equals(old.getIcon())) {
			param.put("icon", user.getIcon());
		}
		if (param.size() > 0) {
			String post = null;
			try {
				logger.warn("修改IM信息：" + param);
				post = HttpClientUtil.post(updateUrl, param);
				JSONObject response = (JSONObject) JSON.parse(post);
				logger.warn("修改IM信息----IM返回：" + response);
				if (response.getInteger("status") != HttpStatus.SC_OK) {
					throw new RuntimeException("修改IM失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public LoginUser findLogin(SysUser user) throws Exception {

		LoginUser login = null;

		SysUser sysUser = sysUserDao.findLogin(user);
		if (sysUser == null|| sysUser.getUserType() != 11) {
			return login;
		}

		// 安卓登陆清空苹果设备号
		if (!"ios".equals(user.getAppOs())) {
			sysUserDao.updateDriveTokenById(null, sysUser.getEid());
		}

		// 公司和部门
//		SysCompany company = companyManager.get(sysUser.getCompanyId());
//		SysDepartment department = departmentManager.get(sysUser.getDeptId());
//
		login = new LoginUser();
		login.setUser(sysUser);
//		if(department != null){
//			login.setDepartment(department);
//		}
//		login.setCompany(company);

		String token = MD5Util.base64Encode(UUID.getUUID());
		int hashToken = MD5Util.FNVHash1(token);

		login.setToken(token);

		// 静态数据
		redisTemplate.opsForValue().set(String.valueOf(hashToken),
				JSON.toJSONString(login), 5L, TimeUnit.DAYS);
		logger.warn(redisTemplate.opsForValue().get(String.valueOf(hashToken))
				.toString());

		//List staticList = null;

		/*
		 * String redisStr = (String) redisTemplate.opsForValue().get(
		 * "sysDictionary"); staticList = JSON.parseArray(redisStr, Map.class);
		 */

//		if (staticList == null || staticList.isEmpty()) {
//			staticList = dictionaryManager.findAllForMap();
//			/*
//			 * redisTemplate.opsForValue().set("sysDictionary",
//			 * JSON.toJSONString(staticList), 5L, TimeUnit.DAYS);
//			 */
//		}

		// 用户可操作的菜单
//		List<Map<String, Object>> menuList = sysUserMenuManager
//				.getMenuByLoginUser(login);
//
//		login.getUserMap().put("menuList", menuList);
//		login.getUserMap().put("staticList", staticList);
//		login.getUserMap().put("token", token);
//		login.getUserMap().put("companyId", company.getEid());
//		login.getUserMap().put("eid", sysUser.getEid());
//		if(department != null){
//			login.getUserMap().put("dpName", department.getName());
//		}
//		login.getUserMap().put("companyName", company.getName());
//		login.getUserMap().put("account", sysUser.getAccount());
//		login.getUserMap().put("userName", sysUser.getName());
//		login.getUserMap().put("level", sysUser.getLevel());
//		login.getUserMap().put("job", sysUser.getJob());
//		login.getUserMap().put("icon",
//				sysUser.getIcon() == null ? "" : sysUser.getIcon());
//		if(company.getLevel() == 1){
//			login.getUserMap().put("ios_download","https://itunes.apple.com/cn/app/%E4%BA%91%E5%A2%83crm-%E4%B8%AD%E5%B0%8F%E4%BC%81%E4%B8%9A%E9%94%80%E5%94%AEcrm%E7%AE%A1%E7%90%86%E8%BD%AF%E4%BB%B6/id1277425300?mt=8");
//		}else if(company.getLevel() == 2){
//			login.getUserMap().put("ios_download","https://itunes.apple.com/cn/app/%E4%BA%91%E5%A2%83%E9%94%80%E5%94%AE%E7%AE%A1%E7%90%86-%E9%94%80%E5%94%AE%E7%AE%A1%E7%90%86%E4%BA%91%E5%B9%B3%E5%8F%B0/id1280836796?mt=8");
//		}else if(company.getLevel() == 3){
//			login.getUserMap().put("ios_download","");
//		}
//		login.getUserMap().put("imAccount", sysUser.getImAccount());
//
//		login.getUserMap().put("companyLevel", company.getLevel()); // 公司级别
//		login.getUserMap().put("imStatus", sysUser.getImStatus()); // IM状态:0=停用，1=启用
//		login.getUserMap().put("caseStatus",
//				company.getCaseStatus() == null ? 3 : company.getCaseStatus());
//		login.getUserMap().put("isVip", company.getIsVip());
//		login.getUserMap().put("userType", sysUser.getUserType());
//		login.getUserMap().put("isSpec",
//				company.getIsSpec() == null ? 0 : company.getIsSpec());
//
//		login.getUserMap().put("isLocation",
//				company.getIsLocation() == null ? 0 : company.getIsLocation());
//		login.getUserMap().put("isOutWork",
//				company.getIsOutWork() == null ? 0 : company.getIsOutWork());
//		login.getUserMap().put("isShowWaiQin", "0");
//		// 邀请注册链接
//		login.getUserMap().put("yaoUrl", propertySetting.getProperty("yaoUrl"));
		return login;
	}

	public List<Map<String, Object>> findDetailUsers(LoginUser loginUser,
			Long deptId) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", loginUser.getCompany().getEid());
		param.put("deptId", deptId);
		return sysUserDao.findDetailUsers(param);
	}

	public void perFileByDeptId(Paginator<SysUser> paginator) {

		sysUserDao.perFileByDeptId(paginator);
	}

	public void findPersosalFile(Paginator<SysUser> paginator) {

		sysUserDao.findPersosalFile(paginator);
	}

	public Map<String, Object> getFileById(Long eid) {

		return sysUserDao.getFileById(eid);
	}

	public List<Map<String, Object>> findAllByCompanyIdMap(
			Map<String, Object> map) {
		return sysUserDao.findAllByCompanyIdMap(map);
	}

	public Map<String, Object> findSexNum(Map<String, Object> param) {
		return sysUserDao.findSexNum(param);
	}

	public Map<String, Object> findEdicationNum(Map<String, Object> param) {
		return sysUserDao.findEdicationNum(param);
	}

	public Map<String, Object> findContractNum(Map<String, Object> param) {
		return sysUserDao.findContractNum(param);
	}

	public void findPageAll(Paginator<Map<String, Object>> paginator) {
		sysUserDao.findPageAll(paginator);
	}

	public long findAllCount() {
		return sysUserDao.findAllCount();
	}

	@Transactional
	public void updatePwdByEid(SysUser user) throws Exception {
		sysUserDao.updatePwdByEid(user);

		// 更新IM密码
		String ImAccount = user.getImAccount();
		String url = propertySetting.getProperty("imRestful") + "register/"
				+ ImAccount + "/" + user.getPassword();
		logger.warn("修改IM密码：" + ImAccount);
		String put = null;
		JSONObject response = null;
		try {
			put = HttpClientUtil.put(url, null);
			logger.warn("IM返回：" + put);
			response = JSONObject.parseObject(put);
		} catch (Exception e) {
			logger.error("IM请求失败：" + put, e);
		}
		if (response == null) {
			logger.warn("修改IM密码请求失败。。。");
			throw new RuntimeException("修改IM密码失败");
		} else {
			logger.warn("IM返回：" + response);
			if (response.getInteger("status") != HttpStatus.SC_OK
					&& response.getInteger("status") != HttpStatus.SC_GONE) {
				throw new RuntimeException("修改IM密码失败");
			}
		}
	}

	/**
	 * 获取客服
	 *
	 * @return
	 */
	public List<Map<String, Object>> findKefuList() {
		return sysUserDao.findKefuList();
	}

	/**
	 * 获取部门员工数
	 *
	 * @param conpanyId
	 * @return
	 */
	public List<Map<String, Object>> deptContactsCount(Long conpanyId) {

		return sysUserDao.deptContactsCount(conpanyId);
	}

	public List<Map<String, Object>> findAllUser() {
		return sysUserDao.findAllUser();
	}

	@Transactional
	public void updatePwdByPhone(SysUser t) throws Exception {
		sysUserDao.updatePwdByPhone(t);

		SysUser user = sysUserDao.findByAccount(t.getAccount());

		// 修改IM密码
		String ImAccount = user.getImAccount();
		String url = propertySetting.getProperty("imRestful") + "register/"
				+ ImAccount + "/" + t.getPassword();
		String put = HttpClientUtil.put(url, null);
		JSONObject response = (JSONObject) JSON.parse(put);
		if (response.getInteger("status") != HttpStatus.SC_OK) {
			throw new RuntimeException("修改IM失败");
		}
	}

	public long findCountByLevel(int i) {
		return sysUserDao.findCountByLevel(i);
	}

	public void findPageByLevel(Paginator<SysUser> paginator) {
		sysUserDao.findPageByLevel(paginator);
	}

	public SysUser findSuperUserByCompanyId(Long companyId) {
		return sysUserDao.findSuperUserByCompanyId(companyId);
	}

	public long findCount(SysUser user) {
		return sysUserDao.findCount(user);
	}

	public long findCountByPhone(String phone,Integer userType) {
		return sysUserDao.findCountByPhone(phone,userType);
	}

	public String findAllUserImAccountStr(long companyId, String imAccount) {
		List<SysUser> sysUsers = sysUserDao.findAllByCompanyId(companyId);
		String allIm = "";
		String finalStr = null;
		if (StringUtils.isBlank(imAccount)) {
			imAccount = "";
		}
		for (SysUser sysUser : sysUsers) {
			if (sysUsers != null) {
				if (imAccount.equals(sysUser.getImAccount()))
					continue;
				allIm += "," + sysUser.getImAccount();
			}
		}
		if (allIm != null) {
			finalStr = allIm.substring(1, allIm.length());
		}
		return finalStr;
	}

	public String findAllUserImAccountStrIncludeSelf(long companyId, String imAccount) {
		List<SysUser> sysUsers = sysUserDao.findAllByCompanyId(companyId);
		String allIm = "";
		String finalStr = null;
		if (StringUtils.isBlank(imAccount)) {
			imAccount = "";
		}
		for (SysUser sysUser : sysUsers) {
			if (sysUsers != null) {
				allIm += "," + sysUser.getImAccount();
			}
		}
		if (allIm != null) {
			finalStr = allIm.substring(1, allIm.length());
		}
		return finalStr;
	}

	@Transactional
	public void updateCodeByDeptId(Long deptId, String code) {
		Map<String, Object> param = new HashMap<>();
		param.put("deptId", deptId);
		param.put("code", code);
		sysUserDao.updateCodeByDeptId(param);
	}

	public List<Map<String, Object>> getUserByLevel(Map<String, Object> criteria) {
		return sysUserDao.findUserByLevel(criteria);
	}

	public Map<String, Object> birthAndHtCount(LoginUser loginUser) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", loginUser.getCompany().getEid());
		int birthCount = sysUserDao.findLatelyBirthCount(param);
		param.put("birthCount", birthCount);
		param.put("birthUrl", "/sysUser/birth/list.htm");
		int contractCount = sysUserDao.findLatelyContractCount(param);
		param.put("contractCount", contractCount);
		param.put("contractUrl", "/sysUser/contract/expire/list.htm");
		return param;
	}

	public List<Map<String, Object>> latelyBirthList(LoginUser loginUser) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", loginUser.getCompany().getEid());
		return sysUserDao.findLatelyBirthList(param);
	}

	public List<Map<String, Object>> latelyContractList(LoginUser loginUser) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", loginUser.getCompany().getEid());
		return sysUserDao.findLatelyContractList(param);
	}

	public int countImUseNum(Long cid) {
		return sysUserDao.countImUseNum(cid);
	}

	public void closeUser(Long ygId) {
		sysUserDao.closeUser(ygId);
	}

	public List<String> findImAccountsByIds(List<Long> userIds) {
		return sysUserDao.findImAccountsByIds(userIds);
	}

	public List<Map<String, Object>> findBadgeByDeviceToken(List<String> tokens) {
		return sysUserDao.findBadgeByDeviceToken(tokens);
	}

	public void updateBadgeBatch(List<Map<String, Object>> list) {
		sysUserDao.updateBadgeBatch(list);
	}

	@Transactional
	public void updateAppleApnsBadge(Long userId, int badge) {
		sysUserDao.updateAppleApnsBadge(userId, badge);
	}

	@Transactional
	public void setTokenNullByDeviceToken(String deviceToken) {
		sysUserDao.setTokenNullByDeviceToken(deviceToken);
	}

	@Transactional
	public void updateDriveTokenById(String driveToken, Long userId) {
		sysUserDao.updateDriveTokenById(driveToken, userId);
	}

	public List<String> finddeviceTokenAll(Long companyId, Long eid) {
		return sysUserDao.finddeviceTokenAll(companyId, eid);
	}

	public List<String> finddeviceTokenForDept(String deptId, Long eid) {
		return sysUserDao.finddeviceTokenForDept(deptId, eid);
	}

	public List<Map<String, Object>> findDriveTokenByImAccounts(List<String> list) {
		return sysUserDao.findDriveTokenByImAccounts(list);
	}

	public void updateMess(SysUser user) {
		sysUserDao.updateMess(user);
	}

	public List<Map<String, Object>> findUsers(Long eid) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("companyId", eid);
		return sysUserDao.findUsers(param);
	}

	public List<Map<String, Object>> findUsersByEids(List<Long> eidList) {
		return sysUserDao.findUsersByEids(eidList);
	}

	public List<Map<String, Object>> findUserByDeptId(Long deptId) {
		return sysUserDao.findUserByDeptId(deptId);
	}

	public List<Map<String, Object>> countDeptNum(Long companyId) {
		return sysUserDao.countDeptNum(companyId);
	}
	
	public Map<String, Object> findAgeData(Map<String, Object> param) {
		return sysUserDao.findAgeData(param);
	}
	public Map<String, Object> findIncomeData(Map<String, Object> param) {
		return sysUserDao.findIncomeData(param);
	}
	public Map<String, Object> findEducationData(Map<String, Object> param) {
		return sysUserDao.findEducationData(param);
	}
	public Map<String, Object> findContractData(Map<String, Object> param) {
		return sysUserDao.findContractData(param);
	}
}