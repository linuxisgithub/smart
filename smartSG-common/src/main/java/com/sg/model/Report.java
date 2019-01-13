package com.sg.model;

import javacommon.base.BaseModel;

import java.util.List;

/**
 *
 * @author 超享
 * @date 2018-12-17 09:40:51
 *
 */
public class Report extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 部门ID. */
	private Long deptId;

	/** 创建人ID. */
	private Long createId;

	/** 公司ID. */
	private Long companyId;

	/** 报表名称. */
	private String name;

	/** 描述. */
	private String description;

	/** 使用状态：0=停用，1=启用. */
	private Integer status;

	/** 是否删除：0=否，1=是. */
	private Integer isDel;

	/** 关联对象：1=APP顾客，2=摊位，3=食阁，4=菜品. */
	private Integer kind;

	private List<Long> liOption;

	public List<Long> getLiOption() {
		return liOption;
	}

	public void setLiOption(List<Long> liOption) {
		this.liOption = liOption;
	}

	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
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
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "Report [deptId=" + deptId + ", createId=" + createId
				+ ", companyId=" + companyId + ", name=" + name
				+ ", description=" + description + ", status=" + status
				+ ", isDel=" + isDel + ", kind=" + kind
				+ ", eid=" + getEid() + "]";
	}
}