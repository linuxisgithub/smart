package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:18:14
 *
 */
public class SysDepartmentInfoContacts extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 部门ID. */
	private Long deptId;

	/** 部门名称. */
	private String deptName;

	/** 公司ID. */
	private Long companyId;

	/** 联系人. */
	private String name;

	/** 职位. */
	private String job;

	/** 手机. */
	private String phone;

	/** 座机/办公电话. */
	private String telephone;

	/** 电子邮件. */
	private String email;

	/** 身份证. */
	private String idCard;

	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() {
		return "SysDepartmentInfoContacts [deptId=" + deptId + ", deptName=" + deptName
				+ ", companyId=" + companyId + ", name=" + name
				+ ", job=" + job + ", phone=" + phone
				+ ", telephone=" + telephone + ", email=" + email
				+ ", idCard=" + idCard + ", eid=" + getEid() + "]";
	}
}