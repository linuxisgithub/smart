package com.sg.model;

import javacommon.base.BaseModel;

import java.math.BigDecimal;

/**
 *
 * @author 超享
 * @date 2019-01-04 15:59:42
 *
 */
public class DishFeaturesOption extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 菜品特性表EID. */
	private Long bid;

	/** 选项值. */
	private Integer optionValue;

	/** 选项名称. */
	private String name;

	/** 英文选项名称. */
	private String enName;

	/** 对应金额. */
	private BigDecimal money;

	/** 是否默认1=默认，2=不默认. */
	private Integer isDefault;

	/** 0=正常、1=已删除. */
	private Integer isDel;

	public Long getBid() {
		return bid;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public Integer getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(Integer optionValue) {
		this.optionValue = optionValue;
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
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "DishFeaturesOption [bid=" + bid + ", optionValue=" + optionValue
				+ ", name=" + name + ", enName=" + enName
				+ ", money=" + money + ", isDefault=" + isDefault
				+ ", isDel=" + isDel + ", eid=" + getEid() + "]";
	}
}