package com.system.dao;

import com.system.model.SysUser;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.Paginator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author duwufeng
 * @date 2017-06-28 09:36:46
 * 
 */
@Repository
public class SysUserDao extends BaseMybatis3Dao<SysUser> {

	public SysUser findByAccount(String account) {
		Map<String, Object> param = new HashMap<>();
		param.put("account", account);
		return sqlSessionTemplate.selectOne(generateStatement("findByAccount"),
				param);
	}
	
	public Map<String,Object> findByImAccount(String imAccount,Long companyId){
		Map<String,Object>map = new HashMap<>();
		map.put("imAccount", imAccount);
		map.put("companyId", companyId);
		return sqlSessionTemplate.selectOne(generateStatement("findByImAccount"),map);
	}

	public void findByType(Paginator<SysUser> paginator) {
		super.findPage(paginator, "findByType");
	}

	public List<SysUser> findAllByCompanyId(long companyId) {
		return sqlSessionTemplate.selectList(
				generateStatement("findAllByCompanyId"), companyId);
	}

	public boolean isExistAccount(Map<String, Object> param) {
		long count = sqlSessionTemplate.selectOne(
				generateStatement("isExistAccount"), param);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public List<Map<String, Object>> getSysUserByLevel(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(
				generateStatement("getSysUserByLevel"), param);
	}

	public void findDdxByType(Paginator<SysUser> paginator) {
		super.findPage(paginator, "findDdxByType");
	}

	public List<Map<String, Object>> findUsersByDeptId(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(
				generateStatement("findUsersByDeptId"), param);
	}

	public List<Map<String, Object>> findDetailUsers(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(
				generateStatement("findDetailUsers"), param);
	}

	public SysUser findLogin(SysUser user) {
		List<SysUser> list = this.sqlSessionTemplate.selectList(
				generateStatement("findLogin"), user);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void perFileByDeptId(Paginator<SysUser> paginator) {
		super.findPage(paginator, "perFileByDeptId");
	}

	public void findPersosalFile(Paginator<SysUser> paginator) {

		super.findPage(paginator, "findPersosalFile");
	}

	public Map<String, Object> getFileById(Long eid) {

		return sqlSessionTemplate.selectOne(generateStatement("getFileById"),
				eid);
	}

	public List<Map<String, Object>> findAllByCompanyIdMap(
			Map<String, Object> map) {
		return sqlSessionTemplate.selectList(
				generateStatement("findAllByCompanyIdMap"), map);
	}

	public Map<String, Object> findSexNum(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(generateStatement("findSexNum"),
				param);
	}

	public Map<String, Object> findEdicationNum(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findEdicationNum"), param);
	}

	public Map<String, Object> findContractNum(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findContractNum"), param);
	}

	public void findPageAll(Paginator<Map<String, Object>> paginator) {
		super.findPageMap(paginator, "findPageAll");
	}

	public long findAllCount() {
		return sqlSessionTemplate.selectOne(generateStatement("findAllCount"));
	}

	public void updatePwdByEid(SysUser user) {
		sqlSessionTemplate.update(generateStatement("updatePwdByEid"), user);
	}

	public List<Map<String, Object>> findKefuList() {
		return sqlSessionTemplate.selectList(generateStatement("findKefuList"));
	}

	public List<Map<String, Object>> deptContactsCount(Long conpanyId) {
		return sqlSessionTemplate.selectList(
				generateStatement("deptContactsCount"), conpanyId);
	}

	public List<Map<String, Object>> findAllUser() {
		return sqlSessionTemplate.selectList(generateStatement("findAllUser"));
	}

	public List<Map<String, Object>> findUsersByDeptIdAndManager(
			Map<String, Object> param) {
		return sqlSessionTemplate.selectList(
				generateStatement("findUsersByDeptIdAndManager"), param);
	}

	public long findCountByLevel(int i) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findCountByLevel"), i);
	}

	public void findPageByLevel(Paginator<SysUser> paginator) {
		super.findPage(paginator, "findPageByLevel");
	}

	public void updatePwdByPhone(SysUser t) {
		sqlSessionTemplate.update(generateStatement("updatePwdByPhone"), t);
	}

	public SysUser findSuperUserByCompanyId(Long companyId) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findSuperUserByCompanyId"), companyId);
	}

	public long findCount(SysUser user) {
		return sqlSessionTemplate.selectOne(generateStatement("isExistAccount"), user);
	}

	public long findCountByPhone(String phone,Integer userType) {
		Map<String, Object> map = new HashMap<>();
		map.put("phone", phone);
		map.put("userType", userType);
		return sqlSessionTemplate.selectOne(generateStatement("findCountByPhone"), map);
	}

	public List<Map<String, Object>> findUsersBySubDeptCode(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(
				generateStatement("findUsersBySubDeptCode"), param);
	}

	public void updateCodeByDeptId(Map<String, Object> param) {
		sqlSessionTemplate.update(generateStatement("updateCodeByDeptId"), param);
	}

	public List<Map<String, Object>> findUserByLevel(Map<String, Object> criteria) {
		return sqlSessionTemplate.selectList(generateStatement("findUserByLevel"), criteria);
	}

	public int findLatelyBirthCount(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(generateStatement("findLatelyBirthCount"), param);
	}
	public List<Map<String, Object>> findLatelyBirthList(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("findLatelyBirthList"), param);
	}

	public int findLatelyContractCount(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(generateStatement("findLatelyContractCount"), param);
	}
	
	public List<Map<String, Object>> findLatelyContractList(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("findLatelyContractList"), param);
	}

	public int countUseNum(Long cid) {
		return sqlSessionTemplate.selectOne(generateStatement("countUseNum"), cid);
	}
	public int countImUseNum(Long cid) {
		return sqlSessionTemplate.selectOne(generateStatement("countImUseNum"), cid);
	}

	public void closeUser(Long ygId) {
		sqlSessionTemplate.update(generateStatement("closeUser"), ygId);
	}

	public List<String> findImAccountsByIds(List<Long> userIds) {
		return sqlSessionTemplate.selectList("findImAccountsByIds", userIds);
	}

	public List<Map<String, Object>> findBadgeByDeviceToken(List<String> tokens) {
		return sqlSessionTemplate.selectList(
				generateStatement("findBadgeByDeviceToken"), tokens);
	}

	public void updateBadgeBatch(List<Map<String, Object>> list) {
		sqlSessionTemplate.update(
				generateStatement("updateBadgeBatch"), list);
	}

	public void updateAppleApnsBadge(Long userId, int badge) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("badge", badge);
		sqlSessionTemplate.update(generateStatement("updateAppleApnsBadge"),
				map);
	}

	public void setTokenNullByDeviceToken(String deviceToken) {
		sqlSessionTemplate.update(
				generateStatement("setTokenNullByDeviceToken"), deviceToken);
	}

	public void updateDriveTokenById(String driveToken, Long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("driveToken", driveToken);
		map.put("userId", userId);
		sqlSessionTemplate.update(generateStatement("updateDriveTokenById"),
				map);
	}

	public List<String> finddeviceTokenAll(Long companyId, Long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", companyId);
		map.put("userId", userId);
		return sqlSessionTemplate.selectList(
				generateStatement("finddeviceTokenAll"), map);
	}

	public List<String> finddeviceTokenForDept(String deptId, Long eid) {
		Map<String, Object> map = new HashMap<>();
		map.put("deptId", deptId);
		map.put("userId", eid);
		return sqlSessionTemplate.selectList(
				generateStatement("finddeviceTokenForDept"), map);
	}

	public List<Map<String, Object>> findDriveTokenByImAccounts(List<String> list) {
		return sqlSessionTemplate.selectList(generateStatement("findDriveTokenByImAccounts"), list);
	}

	public void updateMess(SysUser user) {
		sqlSessionTemplate.update(
				generateStatement("updateMess"), user);
	}

	public List<Map<String, Object>> findUsers(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("findUsers"),param);
	}

	public List<Map<String, Object>> findUsersByEids(List<Long> eidList) {
		return sqlSessionTemplate.selectList(generateStatement("findUsersByEids"), eidList);
	}

	public List<Map<String, Object>> findUserByDeptId(Long deptId) {
		return sqlSessionTemplate.selectList(generateStatement("findUserByDeptId"), deptId);
	}

	public List<Map<String, Object>> countDeptNum(Long companyId) {
		return sqlSessionTemplate.selectList(generateStatement("countDeptNum"),
				companyId);
	}

	
	public Map<String, Object> findAgeData(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findAgeData"), param);
	}
	public Map<String, Object> findIncomeData(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findIncomeData"), param);
	}
	public Map<String, Object> findEducationData(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findEducationData"), param);
	}
	public Map<String, Object> findContractData(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findContractData"), param);
	}
	
}