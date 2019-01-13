package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-14 14:27:12
 *
 */
public class TwVarieties extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 公司ID. */
	private Long companyId;

	/** 主营品种名称. */
	private String name;

	/** 菜单样式：1=称重点餐专用菜单样式，2=快餐点餐专用样式，3=无分组菜单样式，4=分组菜单样式. */
	private Integer style;

	/** 使用状态：0=停用，1=启用. */
	private Integer status;

	/** 是否删除：0=否，1=是. */
	private Integer isDel;

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
	public Integer getStyle() {
		return style;
	}
	public void setStyle(Integer style) {
		this.style = style;
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
		return "TwVarieties [companyId=" + companyId + ", name=" + name
				+ ", style=" + style + ", status=" + status
				+ ", isDel=" + isDel + ", eid=" + getEid() + "]";
	}
}