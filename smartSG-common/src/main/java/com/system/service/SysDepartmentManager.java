package com.system.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.dao.SysDepartmentDao;
import com.system.model.*;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.FirstLetterUtil;
import javacommon.util.Paginator;
import javacommon.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author duwufeng
 * @date 2017-06-28 10:23:28
 *
 */
@Service
public class SysDepartmentManager extends BaseManager<SysDepartment> {

	@Inject
	private SysDepartmentDao sysDepartmentDao;
	@Inject
	private SysUserManager sysUserManager;
	@Inject
	private SysDepartmentInfoSgglManager sgglManager;
	@Inject
	private SysDepartmentInfoSgManager sgManager;
	@Inject
	private SysDepartmentInfoTwManager twManager;
	@Inject
	private SysDepartmentInfoSgyyzManager sgyyzManager;
	@Inject
	private SysDepartmentInfoContactsManager contactsManager;

	@Override
	protected BaseMybatis3Dao<SysDepartment> getDao() {
		return sysDepartmentDao;
	}

	public void findByType(Paginator<SysDepartment> paginator) {
		sysDepartmentDao.findByType(paginator);
	}

	public List<Map<String, Object>> findDeptsByType(Long conpanyId, int type,int dtype) {
		return sysDepartmentDao.findDeptsByType(conpanyId, type, dtype);
	}
	public List<Map<String, Object>> findDeptsByType(Long conpanyId) {
		return sysDepartmentDao.findDeptsByType(conpanyId);
	}
	public List<Map<String, Object>> findTreeDeptsByPidAndType(Long conpanyId, Long pid, int type) {
		return sysDepartmentDao.findTreeDeptsByPidAndType(conpanyId, pid, type);
	}



	public Integer sumTeamStaff(Map<String, Object> criteria) {
		return sysDepartmentDao.sumTeamStaff(criteria);
	}

	public Map<String, Object> findTeamSummaryTotal(Map<String, Object> criteria) {
		return sysDepartmentDao.findTeamSummaryTotal(criteria);
	}

	@Override
	@Transactional
	public void save(SysDepartment dept) {
		//String code = FirstLetterUtil.getFullFirstSpell(dept.getName());
		Long pid = dept.getPid();
		SysDepartment parentDept = sysDepartmentDao.get(pid);
		String parentCode = parentDept.getCode();
		String subCode = StringUtil.subCode(parentCode);

		String code = findLastCodeByPid(subCode, pid);

		/*StringBuffer sb = new StringBuffer(subCode);
		int count = sysDepartmentDao.findCountByPid(pid);
		if(count < 9) {
			sb.append("0" + (count + 1));
		} else {
			sb.append(count + 1);
		}
		String tem = sb.toString();
		for (int i = 0; i < 10 - tem.length(); i++) {
			sb.append("0");
		}*/
		dept.setCode(code);
		super.save(dept);
		if (dept.getDtype() == 2){
			if (dept.getType() == 2){
				SysDepartmentInfoSggl sggl = dept.getSggl();
				sggl.setDeptId(dept.getEid());
				sggl.setDeptName(dept.getName());
				sggl.setCompanyId(dept.getCompanyId());
				sgglManager.save(sggl);
			}else if(dept.getType() == 3){
				SysDepartmentInfoSg sg = dept.getSg();
				sg.setDeptId(dept.getEid());
				sg.setDeptName(dept.getName());
				sg.setCompanyId(dept.getCompanyId());
				sgManager.save(sg);
			}else if(dept.getType() == 4){
				SysDepartmentInfoTw tw = dept.getTw();
				tw.setDeptId(dept.getEid());
				tw.setDeptName(dept.getName());
				tw.setCompanyId(dept.getCompanyId());
				twManager.save(tw);
			}else if(dept.getType() == 5){
				SysDepartmentInfoSgyyz sgyyz = dept.getSgyyz();
				sgyyz.setDeptId(dept.getEid());
				sgyyz.setDeptName(dept.getName());
				sgyyz.setCompanyId(dept.getCompanyId());
				sgyyzManager.save(sgyyz);
			}
			List<SysDepartmentInfoContacts> contactsList = dept.getContactsList();
			for (SysDepartmentInfoContacts contacts : contactsList) {
				contacts.setDeptId(dept.getEid());
				contacts.setDeptName(dept.getName());
				contacts.setCompanyId(dept.getCompanyId());
				contactsManager.save(contacts);
			}
		}
	}

	public String findLastCodeByPid(String subCode, Long pid) {
		StringBuffer sb = new StringBuffer(subCode);
		SysDepartment lastChild = sysDepartmentDao.findLastChildByPid(pid);
		if(lastChild != null) {
			String code = lastChild.getCode();
			String lastSubCode = StringUtil.subCode(code);
			String lastTwoCode = lastSubCode.substring(lastSubCode.length() - 2, lastSubCode.length());
			if(lastTwoCode.startsWith("0")) {
				lastTwoCode = lastTwoCode.substring(1, 2);
			}
			int count = Integer.parseInt(lastTwoCode);
			if(count < 9) {
				sb.append("0" + (count + 1));
			} else {
				sb.append(count + 1);
			}
		} else {
			sb.append("01");
		}
		String tem = sb.toString();
		for (int i = 0; i < 10 - tem.length(); i++) {
			sb.append("0");
		}
		return sb.toString();
	}
	@Transactional
	public void saveForRegister(SysDepartment dept) {
		super.save(dept);
	}

	@Transactional
	@Override
	public void update(SysDepartment t) {
		SysDepartment oldDept = sysDepartmentDao.get(t.getEid());
		if(t.getPid() != null && oldDept.getPid().longValue() != t.getPid().longValue()) {
			// 归属部门改变了
			SysDepartment parentDept = sysDepartmentDao.get(t.getPid());
			String parentSubCode = StringUtil.subCode(parentDept.getCode());
			String newCode = findLastCodeByPid(parentSubCode, t.getPid());
			String newSubCode = StringUtil.subCode(newCode);
			t.setCode(newCode); // 更新部门代码
			sysUserManager.updateCodeByDeptId(t.getEid(), newCode); // 更新员工部门代码

			String oldSubCode = StringUtil.subCode(oldDept.getCode());
			List<SysDepartment> childs = sysDepartmentDao.findChildsBySubCode(oldDept.getCompanyId(), oldDept.getCode(), oldSubCode);
			if(childs != null && !childs.isEmpty()) {
				for (SysDepartment dept : childs) {
					String childNewCode = newSubCode + dept.getCode().substring(newSubCode.length(), dept.getCode().length());
					dept.setCode(childNewCode);  // 更新下属部门的部门代码
					sysDepartmentDao.update(dept);
					sysUserManager.updateCodeByDeptId(dept.getEid(), dept.getCode());
				}
			}
		}
		super.update(t);
		if(!oldDept.getName().equals(t.getName())) {
			sysDepartmentDao.updatePname(t.getCompanyId(), t.getEid(), t.getName());
		}
		if (t.getDtype() == 2) {
			if (t.getType() == 2) {
				SysDepartmentInfoSggl sggl = t.getSggl();
				sgglManager.update(sggl);
			} else if (t.getType() == 3) {
				SysDepartmentInfoSg sg = t.getSg();
				sgManager.update(sg);
			} else if (t.getType() == 4) {
				SysDepartmentInfoTw tw = t.getTw();
				twManager.update(tw);
			} else if (t.getType() == 5) {
				SysDepartmentInfoSgyyz sgyyz = t.getSgyyz();
				sgyyzManager.update(sgyyz);
			}
			List<SysDepartmentInfoContacts> contactsList = t.getContactsList();
			for (SysDepartmentInfoContacts contacts : contactsList) {
				contactsManager.update(contacts);
			}
		}
	}

	public boolean isExistDeptName(Long conpanyId, String deptName, Long eid) {
		Map<String, Object> param = new HashMap<>();
		param.put("deptName", deptName);
		param.put("eid", eid);
		param.put("conpanyId", conpanyId);
		return sysDepartmentDao.isExistDeptName(param);
	}

	public Map<String, Object> getDeptLevelNum(Long conpanyId) {
		return sysDepartmentDao.getDeptLevelNum(conpanyId);
	}
	public List<Map<String, Object>> getTreeLevelNum(Long conpanyId) {
		return sysDepartmentDao.getTreeLevelNum(conpanyId);
	}
	public List<Map<String, Object>> findDepts(Long companyId, Long pid) {
		return sysDepartmentDao.findDepts(companyId, pid);
	}
	public Map<String, Object> findRlzyTotal(Long companyId, Long pid) {
		return sysDepartmentDao.findRlzyTotal(companyId, pid);
	}
	@Transactional
	public void updateStaffNum(SysDepartment department) {
		String eidStaffNum = department.getEid_staffNum();
		if (StringUtils.isNotBlank(eidStaffNum)) {
			if (eidStaffNum != null && !"[]".equals(eidStaffNum) && eidStaffNum.length() > 0) {
				List<Map> staffNum = JSONArray.parseArray(eidStaffNum,
						Map.class);
				Map<String, Object> deptStaffNum = new HashMap<>();
				if (staffNum != null && staffNum.size() > 0) {
					deptStaffNum.put("staffNumList", staffNum.toArray());
					sysDepartmentDao.updateStaffNum(deptStaffNum);
				}
			}
		}
	}
	public int findCountByPid(Long pid) {
		return sysDepartmentDao.findCountByPid(pid);
	}

	public JSONObject findTeamData(Map<String, Object> criteria) {
		if("count".equalsIgnoreCase(criteria.get("s_type").toString())){
			return sysDepartmentDao.findTeamStaffSizeData(criteria);
		}else{
			return sysDepartmentDao.findTeamIncomeData(criteria);
		}
	}
	public boolean existDeptName(Long companyId, String name) {
		Map<String, Object> result = new HashMap<>();
		result.put("companyId", companyId);
		result.put("name", name);
		long count = sysDepartmentDao.existDeptNameNumber(result);
		if(count == 0){
			return true;
		}
		return false;
	}
	public List<Map<String, Object>> findDeptList(Long companyId) {
		Map<String, Object> result = new HashMap<>();
		result.put("companyId", companyId);
		return sysDepartmentDao.findDeptList(result);
	}
	
	public List<Map<String, Object>> findDeptDate(Map<String, Object> criteria) {
		return sysDepartmentDao.findDeptDate(criteria);
	}

	public List<Map<String, Object>> findByPid(Long pid) {
		return  sysDepartmentDao.findByPid(pid);
	}

}