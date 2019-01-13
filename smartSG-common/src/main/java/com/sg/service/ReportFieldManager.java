package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.ReportFieldDao;
import com.sg.model.ReportField;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:59:22
 *
 */
@Service
public class ReportFieldManager extends BaseManager<ReportField> {

	@Inject
	private ReportFieldDao reportFieldDao;
	@Override
	protected BaseMybatis3Dao<ReportField> getDao() {
		return reportFieldDao;
	}

	public List<Long> findByReportId(Long reportId) {
			return reportFieldDao.findByReportId(reportId);
	}

	public List<Map<String,Object>> findFieldNameByReportId(Long reportId) {
		return reportFieldDao.findFieldNameByReportId(reportId);
	}

	public void delByReportId(Long reportId) {
		reportFieldDao.delByReportId(reportId);
	}

}