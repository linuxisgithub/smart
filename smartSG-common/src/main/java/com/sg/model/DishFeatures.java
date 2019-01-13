package com.sg.model;

import com.alibaba.fastjson.annotation.JSONField;
import javacommon.base.BaseModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2019-01-04 15:58:39
 *
 */
public class DishFeatures extends BaseModel implements Cloneable{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** TYPE=1时摊位ID、TYPE=2时菜品ID. */
	private Long bid;

	/** 1=单品点餐，2=一餐多菜. */
	private Integer type;

	/** 特性名称. */
	private String name;

	/** 英文特性名称. */
	private String enName;

	/** 1=单选，2=多选. */
	private Integer optionType;

	/** 对应金额. */
	private BigDecimal money;

	/** 1=选用，2=不选用. */
	private Integer isSelected;

	/** 0=正常、1=已删除. */
	private Integer isDel;

	/** 选项个数 */
	@JSONField(serialize = false)
	private Integer optionNumber;

	/** 默认选项 */
	@JSONField(serialize = false)
	private String optionStr;

	public String getOptionStr() {
		return optionStr;
	}

	public void setOptionStr(String optionStr) {
		this.optionStr = optionStr;
	}

	public Integer getOptionNumber() {
		return optionNumber;
	}

	public void setOptionNumber(Integer optionNumber) {
		this.optionNumber = optionNumber;
	}

	/** 特性选项 */
	@JSONField(serialize = false)
	private List<DishFeaturesOption> options_update = new ArrayList<>();

	/** 特性选项 */
	@JSONField(serialize = false)
	private List<DishFeaturesOption> options_insert = new ArrayList<>();

	public List<DishFeaturesOption> getOptions_update() {
		return options_update;
	}

	public void setOptions_update(List<DishFeaturesOption> options_update) {
		this.options_update = options_update;
	}

	public List<DishFeaturesOption> getOptions_insert() {
		return options_insert;
	}

	public void setOptions_insert(List<DishFeaturesOption> options_insert) {
		this.options_insert = options_insert;
	}


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
	public Integer getOptionType() {
		return optionType;
	}
	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Integer getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public DishFeatures clone() {
		DishFeatures dishFeatures = null;
		try{
			dishFeatures = (DishFeatures)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return dishFeatures;
	}

	@Override
	public String toString() {
		return "DishFeatures [bid=" + bid + ", type=" + type
				+ ", name=" + name + ", enName=" + enName
				+ ", optionType=" + optionType + ", money=" + money
				+ ", isSelected=" + isSelected + ", isDel=" + isDel
				+ ", eid=" + getEid() + "]";
	}
}