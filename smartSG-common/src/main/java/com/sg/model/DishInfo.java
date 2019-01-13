package com.sg.model;

import javacommon.base.BaseModel;

import java.math.BigDecimal;

/**
 *
 * @author 超享
 * @date 2019-01-03 14:50:15
 *
 */
public class DishInfo extends BaseModel implements Cloneable{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 摊位ID. */
	private Long deptId;

	/** 编号. */
	private String serNo;

	/** 菜品中文名. */
	private String name;

	/** 菜品英文名. */
	private String enName;

	/** 分组ID. */
	private Long groupId;

	/** 分组名称. */
	private String groupName;

	/** 单位. */
	private String unit;

	/** 销售价. */
	private BigDecimal sellingPrice;

	/** 卡路里. */
	private String calorie;

	/** 准备时间. */
	private String preparationTime;

	/** 成本. */
	private BigDecimal cost;

	/** 剩余数量. */
	private Integer number;

	/** 特别说明. */
	private String description;

	/** 英文特别说明. */
	private String enDescription;

	/** 是否删除：0=否，1=是. */
	private Integer isDel;

	@Override
	public DishInfo clone() {
		DishInfo dishInfo = null;
		try{
			dishInfo = (DishInfo)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return dishInfo;
	}

	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getSerNo() {
		return serNo;
	}
	public void setSerNo(String serNo) {
		this.serNo = serNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public String getCalorie() {
		return calorie;
	}
	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}
	public String getPreparationTime() {
		return preparationTime;
	}
	public void setPreparationTime(String preparationTime) {
		this.preparationTime = preparationTime;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEnDescription() {
		return enDescription;
	}
	public void setEnDescription(String enDescription) {
		this.enDescription = enDescription;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "DishInfo [deptId=" + deptId + ", serNo=" + serNo
				+ ", name=" + name + ", enName=" + enName
				+ ", groupId=" + groupId + ", groupName=" + groupName
				+ ", unit=" + unit + ", sellingPrice=" + sellingPrice
				+ ", calorie=" + calorie + ", preparationTime=" + preparationTime
				+ ", cost=" + cost + ", number=" + number
				+ ", description=" + description + ", enDescription=" + enDescription
				+ ", isDel=" + isDel + ", eid=" + getEid() + "]";
	}
}