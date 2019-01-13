package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author chaoxiang
 * @date 2017-07-03 10:39:01
 *
 */
public class SysDictionary extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 公司ID. */
	private long companyId;

	/** 字典名称. */
	private String name;

	/** 字典代码. */
	private String code;

	/** 字典值. */
	private String value;

	/** 字典描述. */
	private String remark;

	/** 是否启用 1=启用 2=停用. */
	private short isEnable;

	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public short getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(short isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "Dictionary [companyId=" + companyId + ", name=" + name
				+ ", code=" + code + ", value=" + value
				+ ", remark=" + remark + ", isEnable=" + isEnable
				+ ", eid=" + getEid() + "]";
	}
}