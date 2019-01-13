package com.system.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andy
 * @since 2016/10/18 0018.
 */
public class LoginUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3793716753743467950L;

	private SysUser user;

	private SysDepartment department;

	private SysCompany company;

	private String token;

	private Map<String, Object> userMap = new HashMap<>();

	public Map<String, Object> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, Object> userMap) {
		this.userMap = userMap;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public SysDepartment getDepartment() {
		return department;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public SysCompany getCompany() {
		return company;
	}

	public void setCompany(SysCompany company) {
		this.company = company;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginUser [user=" + user + ", department=" + department
				+ ", company=" + company + ", token=" + token + ", userMap="
				+ userMap + "]";
	}
}
