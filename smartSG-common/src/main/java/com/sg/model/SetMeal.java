package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2019-01-04 14:30:29
 *
 */
public class SetMeal extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 摊位ID. */
	private Long bid;

	/** 快餐类型：1=快餐类型、2=称重类型. */
	private Integer type;

	/** 单位数量. */
	private Integer unitNumber;

	/** 计价单位. */
	private String unit;

	/** 准备时间. */
	private String preparationTime;

	public Long getBid() {
		return bid;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPreparationTime() {
		return preparationTime;
	}
	public void setPreparationTime(String preparationTime) {
		this.preparationTime = preparationTime;
	}

	@Override
	public String toString() {
		return "SetMeal [bid=" + bid + ", type=" + type
				+ ", unitNumber=" + unitNumber + ", unit=" + unit
				+ ", preparationTime=" + preparationTime + ", eid=" + getEid() + "]";
	}
}