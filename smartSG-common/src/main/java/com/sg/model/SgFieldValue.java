package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-24 08:46:23
 *
 */
public class SgFieldValue extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 食阁字段表EID. */
	private String bid;

	/** 值. */
	private String value;

	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SgFieldValue [bid=" + bid + ", value=" + value
				+ ", eid=" + getEid() + "]";
	}
}