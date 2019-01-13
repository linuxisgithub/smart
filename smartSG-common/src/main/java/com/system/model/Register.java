package com.system.model;

import javacommon.base.BaseModel;

/**
 * 注册
 * 
 * @author 卢子敬
 * 
 */
public class Register extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2817315115469068318L;

	/**
	 * 手机号码
	 */
	private String phone;

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Register [phone=" + phone + ", password=" + password + "]";
	}

}