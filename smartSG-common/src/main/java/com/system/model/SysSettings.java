package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author duwufeng
 * @date 2017-07-14 15:29:38
 *
 */
public class SysSettings extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 公司ID. */
	private Long companyId;

	/** 外键ID. */
	private Long bid;

	/** 代码. */
	private String code;

	/** 值. */
	private String value;

	/** 是否启用，1=启用，0=停用. */
	private Integer isUse = 1;

	/** 说明. */
	private String remark = "无";

	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getBid() {
		return bid;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SysSettings [companyId=" + companyId + ", bid=" + bid
				+ ", code=" + code + ", value=" + value
				+ ", isUse=" + isUse + ", remark=" + remark
				+ ", eid=" + getEid() + "]";
	}
}