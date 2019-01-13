package com.system.dao;

import com.alibaba.fastjson.JSONObject;
import com.system.model.SysDepartment;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.Paginator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author duwufeng
 * @date 2017-06-28 10:23:28
 *
 */
@Repository
public class SysDepartmentDao extends BaseMybatis3Dao<SysDepartment> {

	public void findPageRlzy(Paginator<SysDepartment> paginator) {
		super.findPage(paginator, "findPageRlzy");
	}

	public Map<String, Object> findPageRlzyTotal(Map<String, Object> criteria) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findPageRlzyTotal"), criteria);
	}

	public void findByType(Paginator<SysDepartment> paginator) {
		super.findPage(paginator, "findByType");
	}

	public List<Map<String, Object>> findDeptsByType(Long conpanyId, int type, int dtype) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", conpanyId);
		param.put("type", type);
		param.put("dtype", dtype);
		return sqlSessionTemplate.selectList(generateStatement("findDeptsByType"), param);
	}

	public List<Map<String, Object>> findDeptsByType(Long conpanyId) {
		 List<Map<String, Object>> Depts = sqlSessionTemplate.selectList(generateStatement("findDeptsByConpanyId"), conpanyId);
		 for (Map<String, Object> map : Depts) {
			map.put("users",sqlSessionTemplate.selectList(generateStatement("findUserByDeptEid"),map.get("eid")));
		 }
		 return Depts;
	}

	public List<Map<String, Object>> findTreeDeptsByPidAndType(Long conpanyId, Long pid, int type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("conpanyId", conpanyId);
		param.put("pid", pid);
		param.put("type", type);
		return sqlSessionTemplate.selectList(generateStatement("findTreeDeptsByPidAndType"), param);
	}

	public void updatePname(Long conpanyId, Long pid, String newName) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("newName", newName);
		param.put("pid", pid);
		param.put("conpanyId", conpanyId);
		sqlSessionTemplate.update(generateStatement("updatePname"), param);
	}

	public boolean isExistDeptName(Map<String, Object> param) {
		long count = sqlSessionTemplate.selectOne(generateStatement("isExistDeptName"), param);
		if(count > 0) {
			return true;
		}
		return false;
	}

	public Map<String, Object> getDeptLevelNum(Long conpanyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("conpanyId", conpanyId);
		return sqlSessionTemplate.selectOne(generateStatement("getDeptLevelNum"), param);
	}

	public List<Map<String, Object>> getTreeLevelNum(Long conpanyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("conpanyId", conpanyId);
		return sqlSessionTemplate.selectList(generateStatement("getTreeLevelNum"), param);
	}

	public List<Map<String, Object>> findDepts(Long companyId, Long pid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("l_pid", pid);
		return sqlSessionTemplate.selectList(generateStatement("findDepts"), param);
	}

	public Map<String, Object> findRlzyTotal(Long companyId, Long pid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("l_companyId", companyId);
		param.put("l_pid", pid);
		return sqlSessionTemplate.selectOne(
				generateStatement("findPageRlzyTotal"), param);
	}

	public void updateStaffNum(Map<String, Object> deptStaffNum) {
		sqlSessionTemplate.insert(generateStatement("updateStaffNum"), deptStaffNum);
	}

	public int findCountByPid(Long pid) {
		return sqlSessionTemplate.selectOne(generateStatement("findCountByPid"), pid);
	}

	public SysDepartment findLastChildByPid(Long pid) {
		return sqlSessionTemplate.selectOne(generateStatement("findLastChildByPid"), pid);
	}

	public List<SysDepartment> findChildsBySubCode(Long companyId, String code, String subCode) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", companyId);
		param.put("code", code);
		param.put("subCode", subCode);
		return sqlSessionTemplate.selectList(generateStatement("findChildsBySubCode"), param);
	}

	public Integer sumTeamStaff(Map<String, Object> criteria) {
		return sqlSessionTemplate.selectOne(generateStatement("sumTeamStaff"),criteria);
	}

	public Map<String, Object> findTeamSummaryTotal(Map<String, Object> criteria) {
		return sqlSessionTemplate.selectOne(
				generateStatement("findTeamSummaryTotal"), criteria);
	}

	public JSONObject findTeamStaffSizeData(Map<String, Object> criteria) {
		return sqlSessionTemplate.selectOne(generateStatement("findTeamStaffSizeData"), criteria);
	}

	public JSONObject findTeamIncomeData(Map<String, Object> criteria) {
		return sqlSessionTemplate.selectOne(generateStatement("findTeamIncomeData"), criteria);
	}

	public long existDeptNameNumber(Map<String, Object> result) {
		return sqlSessionTemplate.selectOne(generateStatement("existDeptNameNumber"), result);
	}

	public List<Map<String, Object>> findDeptList(Map<String, Object> result) {
		return sqlSessionTemplate.selectList(generateStatement("findDeptList"), result);
	}
	
	public List<Map<String, Object>> findDeptDate(Map<String, Object> criteria) {
		return sqlSessionTemplate.selectList(generateStatement("findDeptDate"), criteria);
	}

	public List<Map<String, Object>> findByPid(Long pid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pid", pid);
		return sqlSessionTemplate.selectList(generateStatement("findByPid"), param);
	}


}