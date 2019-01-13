package com.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import javacommon.base.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 *
 * @author duwufeng
 * @date 2017-07-05 19:30:34
 *
 */
public class SysUserMenu extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 菜单ID. */
	@NotNull(message = "菜单不能为空！")
	private Long menuId;
	
	private String menuName;

	/** 公司ID. */
	private Long companyId;

	/** 使用者ID，KIND=1时为公司ID，KIND=2时为部门ID，KIND=3时为用户ID. */
	private Long useId;
	private String useName;
	private String useDeptName;

	/** 级别，1=全公司，2=部门级别，3=职员. */
	@NotNull(message = "授权范围不能为空！")
	private Integer kind;

	@JSONField(serialize = false)
	@NotBlank(message = "授权用户不能为空！")
	/** 提交数据时用. */
	private String useUsers;
	
	private Long approvalId;
	
	/** 权限获取方式，0=正常，1=因为发布，2=因为批审，3=临时报告. */
	private Integer grantType;
	/** 菜单ID，权限获取方式，因为此菜单ID而得到权限 */
	private Long grantBy;
	
	public Long getGrantBy() {
		return grantBy;
	}
	public void setGrantBy(Long grantBy) {
		this.grantBy = grantBy;
	}
	public Integer getGrantType() {
		return grantType;
	}
	public void setGrantType(Integer grantType) {
		this.grantType = grantType;
	}
	public Long getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getUseDeptName() {
		return useDeptName;
	}
	public void setUseDeptName(String useDeptName) {
		this.useDeptName = useDeptName;
	}
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getUseUsers() {
		return useUsers;
	}
	public void setUseUsers(String useUsers) {
		this.useUsers = useUsers;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getUseId() {
		return useId;
	}
	public void setUseId(Long useId) {
		this.useId = useId;
	}
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	@Override
	public String toString() {
		return "SysUserMenu [menuId=" + menuId + ", menuName=" + menuName + ", companyId=" + companyId + ", useId="
				+ useId + ", useName=" + useName + ", useDeptName=" + useDeptName + ", kind=" + kind + ", useUsers="
				+ useUsers + "]";
	}
	
}