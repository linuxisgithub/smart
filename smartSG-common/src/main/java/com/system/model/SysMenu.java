package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author duwufeng
 * @date 2017-07-20 15:50:18
 *
 */
public class SysMenu extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 父菜单ID. */
	private Long pid;

	/** 菜单名称. */
	private String menuName;

	/** 菜单英文名称. */
	private String menuCode;

	/** 菜单url. */
	private String menuUrl;

	/** 菜单级别(1：一级菜单；2：二级菜单). */
	private String menuLevel;

	/** 功能分类，menu=菜单，function=功能. */
	private String menuType;

	/** 无权限时是否显示菜单，0=不显示，1=显示. */
	private Integer menuShow;

	/** 流程设置btype. */
	private Integer btype;

	/** 说明. */
	private String remark;

	/** 图标icon. */
	private String icon;

	/** 是否有批审流程. */
	private Integer hasApproval;

	/** 公司使用最低级别. */
	private Integer companyLevel;

	/** 排序. */
	private Integer sortIndex;

	/** 是否启用，1=启用，0=停用. */
	private Integer isUse;

	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public Integer getMenuShow() {
		return menuShow;
	}
	public void setMenuShow(Integer menuShow) {
		this.menuShow = menuShow;
	}
	public Integer getBtype() {
		return btype;
	}
	public void setBtype(Integer btype) {
		this.btype = btype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getHasApproval() {
		return hasApproval;
	}
	public void setHasApproval(Integer hasApproval) {
		this.hasApproval = hasApproval;
	}
	public Integer getCompanyLevel() {
		return companyLevel;
	}
	public void setCompanyLevel(Integer companyLevel) {
		this.companyLevel = companyLevel;
	}
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}

	@Override
	public String toString() {
		return "SysMenu [pid=" + pid + ", menuName=" + menuName
				+ ", menuCode=" + menuCode + ", menuUrl=" + menuUrl
				+ ", menuLevel=" + menuLevel + ", menuType=" + menuType
				+ ", menuShow=" + menuShow + ", btype=" + btype
				+ ", remark=" + remark + ", icon=" + icon
				+ ", hasApproval=" + hasApproval + ", companyLevel=" + companyLevel
				+ ", sortIndex=" + sortIndex + ", isUse=" + isUse
				+ ", eid=" + getEid() + "]";
	}
}