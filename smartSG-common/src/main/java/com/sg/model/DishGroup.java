package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2019-01-03 11:02:38
 *
 */
public class DishGroup extends BaseModel implements Cloneable{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 摊位ID. */
	private Long deptId;

	/** 分组名称. */
	private String name;

	/** 是否删除：0=否，1=是. */
	private Integer isDel;

	@Override
	public DishGroup clone() {
		DishGroup dishGroup = null;
		try{
			dishGroup = (DishGroup)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return dishGroup;
	}

	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "DishGroup [deptId=" + deptId + ", name=" + name
				+ ", isDel=" + isDel + ", eid=" + getEid() + "]";
	}
}