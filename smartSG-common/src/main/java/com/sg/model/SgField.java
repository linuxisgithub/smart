package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-22 11:32:31
 *
 */
public class SgField extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 食阁ID. */
	private Long bid;

	/** 字段名称. */
	private String name;

	/** 默认值. */
	private String defaultValue;

	/** 值. */
	private String myValue;

	/** 字段类型：1=文本，2=数字，3=货币，4=日期. */
	private Integer fieldType;

	/** 1=显示，2=不显示. */
	private Integer isShow;

	/** 0=正常、1=已删除. */
	private Integer isDel;

	public Long getBid() {
		return bid;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getMyValue() {
		return myValue;
	}

	public void setMyValue(String myValue) {
		this.myValue = myValue;
	}

	public Integer getFieldType() {
		return fieldType;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "SgField [bid=" + bid + ", name=" + name
				+ ", defaultValue=" + defaultValue + ", fieldType=" + fieldType
				+ ", isShow=" + isShow + ", isDel=" + isDel
				+ ", eid=" + getEid() + "]";
	}
}