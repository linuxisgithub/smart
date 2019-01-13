package com.system.dao;

import com.system.model.SysCompany;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author duwufeng
 * @date 2017-06-28 10:22:36
 *
 */
@Repository
public class SysCompanyDao extends BaseMybatis3Dao<SysCompany> {

	public SysCompany findByCode(String companyCode) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("companyCode", companyCode);
		return sqlSessionTemplate.selectOne(generateStatement("findByCode"), param);
	}

	public long findCount(String phone) {
		return sqlSessionTemplate.selectOne(generateStatement("findCount"),  phone);
	}

	public SysCompany findByDndAddress(String dnsAddress) {
		Map<String, Object> param = new HashMap<>();
		param.put("dnsAddress", dnsAddress);
		return sqlSessionTemplate.selectOne(generateStatement("findByDndAddress"), param);
	}

	public void updateCaseStatus(Map<String, Object> param) {
		sqlSessionTemplate.update(generateStatement("updateCaseStatus"), param);
	}

	public void setUnLead(Long eid) {
		sqlSessionTemplate.update(generateStatement("setUnLead"), eid);
		
	}

	public void clearCase(Long eid) {
		sqlSessionTemplate.update(generateStatement("clearCase"), eid);
	}

	public int findIsSpec(Long eid) {
		return sqlSessionTemplate.selectOne(generateStatement("findIsSpec"), eid);
	}

}