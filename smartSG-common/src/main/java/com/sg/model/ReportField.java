package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:59:23
 *
 */
public class ReportField extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 报表ID. */
	private Long reportId;

	/** 字段表ID. */
	private Long fieldId;

	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	@Override
	public String toString() {
		return "ReportField [reportId=" + reportId + ", fieldId=" + fieldId
				+ ", eid=" + getEid() + "]";
	}
}