package com.sg.service;

import javax.inject.Inject;

import com.sg.model.ReportField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sg.dao.ReportDao;
import com.sg.model.Report;
import com.system.model.LoginUser;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:58:03
 *
 */
@Service
public class ReportManager extends BaseManager<Report> {

	@Inject
	private ReportDao reportDao;
	@Inject
	private ReportFieldManager reportFieldManager;
	@Override
	protected BaseMybatis3Dao<Report> getDao() {
		return reportDao;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void doSave(Report report,LoginUser user) {
		if(report.getEid() == null){
			report.setCompanyId(user.getCompany().getEid());
			report.setIsDel(0);
			report.setStatus(1);
			report.setCreateId(user.getUser().getEid());
			if (user.getUser().getLevel()!=3)
				report.setDeptId(user.getUser().getDeptId());
			reportDao.save(report);
			List<Long> liOptions = report.getLiOption();
 			for (Long option:liOptions) {
				ReportField reportField = new ReportField();
				reportField.setReportId(report.getEid());
				reportField.setFieldId(option);
				reportFieldManager.save(reportField);
			}
		}else{
			reportDao.update(report);
			reportFieldManager.delByReportId(report.getEid());//删除原有的
			List<Long> liOptions = report.getLiOption();
			for (Long option:liOptions) {//添加跟改后的
				ReportField reportField = new ReportField();
				reportField.setReportId(report.getEid());
				reportField.setFieldId(option);
				reportFieldManager.save(reportField);
			}
		}
	}

	public List<Map<String, Object>> findKingData(Long conpanyId, Integer kind) {
		return reportDao.findKindData(conpanyId,kind);
	}

}