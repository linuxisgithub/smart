package com.sg.model;

import com.alibaba.fastjson.annotation.JSONField;
import javacommon.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2018-12-29 11:09:21
 *
 */
public class TwRenovation extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 摊位ID. */
	private Long bid;

	/** 卫生：1=A，2=B, 3=C. */
	private Integer health;

	/** 类别：1=HALAL，2=NON-HALAL. */
	private Integer type;

	/** 主营品种表ID. */
	private Long varieties;

	/** 其它经营品种. */
	private String otherVarieties;

	/** 本店最低消费. */
	private String minimum;

	/** 排队规则：1=到店排队，2=下单排队. */
	private Integer lineup;

	/** 顾客端点餐流程：1=“一餐多菜”点餐，2=单品”点餐,3=系统默认点餐流程. */
	private Integer procedure;

	/** 顾客端菜单样式：1=有分组样式，2=无分组样式. */
	private Integer style;

	/** 字段 */
	@JSONField(serialize = false)
	private List<SgField> FieldList = new ArrayList<>();

	public List<SgField> getFieldList() {
		return FieldList;
	}

	public void setFieldList(List<SgField> fieldList) {
		FieldList = fieldList;
	}

	public Long getBid() {
		return bid;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public Integer getHealth() {
		return health;
	}
	public void setHealth(Integer health) {
		this.health = health;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getVarieties() {
		return varieties;
	}
	public void setVarieties(Long varieties) {
		this.varieties = varieties;
	}
	public String getOtherVarieties() {
		return otherVarieties;
	}
	public void setOtherVarieties(String otherVarieties) {
		this.otherVarieties = otherVarieties;
	}
	public String getMinimum() {
		return minimum;
	}
	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}
	public Integer getLineup() {
		return lineup;
	}
	public void setLineup(Integer lineup) {
		this.lineup = lineup;
	}
	public Integer getProcedure() {
		return procedure;
	}
	public void setProcedure(Integer procedure) {
		this.procedure = procedure;
	}
	public Integer getStyle() {
		return style;
	}
	public void setStyle(Integer style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return "TwRenovation [bid=" + bid + ", health=" + health
				+ ", type=" + type + ", varieties=" + varieties
				+ ", otherVarieties=" + otherVarieties + ", minimum=" + minimum
				+ ", lineup=" + lineup + ", procedure=" + procedure
				+ ", style=" + style + ", eid=" + getEid() + "]";
	}
}