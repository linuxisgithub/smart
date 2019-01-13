package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-28 09:17:53
 *
 */
public class Binding extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 摊位ID. */
	private Long bid;

	/** 被绑定摊位ID. */
	private Long bbid;

	/** tw=摊位，sg=食阁. */
	private String type;

	/** 操作人ID. */
	private Long userId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getBid() {
		return bid;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public Long getBbid() {
		return bbid;
	}
	public void setBbid(Long bbid) {
		this.bbid = bbid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Binding{" +
				"bid=" + bid +
				", bbid=" + bbid +
				", type='" + type + '\'' +
				", userId=" + userId +
				", eis=" + getEid() +
				'}';
	}
}