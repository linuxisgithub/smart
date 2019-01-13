package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author chaoxiang
 * @date 2017-06-30 15:16:35
 *
 */
public class SysParam extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 参数名称. */
	private String name;

	/** 代码. */
	private String code;

	/** 值. */
	private String value;

	/** 是否启用，1=启用，0=停用. */
	private short isUse;

	/** 描述. */
	private String remark;

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
	public short getIsUse() {
		return isUse;
	}
	public void setIsUse(short isUse) {
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
		return "SysParam [name=" + name + ", code=" + code
				+ ", value=" + value + ", isUse=" + isUse
				+ ", remark=" + remark + ", eid=" + getEid() + "]";
	}
}