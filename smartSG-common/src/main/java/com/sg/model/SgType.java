package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-12 14:47:12
 *
 */
public class SgType extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 部门ID. */
	private Long deptId;

	/** 部门名称. */
	private String deptName;

	/** 公司ID. */
	private Long companyId;

	/** 类型名称. */
	private String name;

	/** 类型描述. */
	private String description;

	/** 使用状态：0=停用，1=启用. */
	private Integer status;

	/** 是否删除：0=否，1=是. */
	private Integer isDel;

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "SgType [deptId=" + deptId + ", deptName=" + deptName
				+ ", companyId=" + companyId +  ", name=" + name + ", description=" + description
				+ ", status=" + status + ", isDel=" + isDel
				+ ", eid=" + getEid() + "]";
	}
}