package com.system.service;

import com.system.dao.SysCompanyDao;
import com.system.model.SysCompany;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author duwufeng
 * @date 2017-06-28 10:22:36
 *
 */
@Service
public class SysCompanyManager extends BaseManager<SysCompany> {

	@Inject
	private SysCompanyDao sysCompanyDao;
	@Override
	protected BaseMybatis3Dao<SysCompany> getDao() {
		return sysCompanyDao;
	}
	public SysCompany findByCode(String companyCode) {
		return sysCompanyDao.findByCode(companyCode);
	}
	public long findCount(String phone) {
		return sysCompanyDao.findCount(phone);
	}
	public SysCompany findByDndAddress(String dnsAddress) {

		return sysCompanyDao.findByDndAddress(dnsAddress);
	}
	@Transactional
	public void updateCaseStatus(Long companyId, Integer caseStatus) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", companyId);
		param.put("caseStatus", caseStatus);
		sysCompanyDao.updateCaseStatus(param);
	}
	@Transactional
	public void setUnLead(Long eid) {
		sysCompanyDao.setUnLead(eid);
	}
	@Transactional
	public void clearCase(Long eid) {
		sysCompanyDao.clearCase(eid);
	}
	public int findIsSpec(Long eid) {
		return sysCompanyDao.findIsSpec(eid);
	}
}