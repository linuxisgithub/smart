package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:57:34
 *
 */
public class Field extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 字段名称. */
	private String name;

	/** 字段类型. */
	private String type;

	/** 关联对象：1=APP顾客，2=摊位，3=食阁，4=菜品. */
	private Integer kind;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", type=" + type
				+ ", kind=" + kind + ", eid=" + getEid() + "]";
	}
}