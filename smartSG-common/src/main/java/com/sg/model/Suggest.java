package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-17 13:57:01
 *
 */
public class Suggest extends BaseModel {

	
	public Suggest() {
		super();
	}
	public Suggest(Long eid, Integer isReply, Integer isDel){
		setEid(eid);
		this.isReply = isReply;
		this.isDel = isDel;
	}
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 公司ID. */
	private Long companyId;

	/** 部门ID. */
	private Long deptId;

	/** 发送人ID. */
	private Long sendId;

	/** 收件人ID. */
	private Long receiveId;

	/** 发送日期. */
	private String sendDate;

	/** 消息类型：1=商业合作，2=投诉结果，3=意见反馈，4=其他反馈，5=系统消息. */
	private Integer kind;

	/** 消息类型名称. */
	private String kindName;
	
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/** 意见内容. */
	private String content;

	/** 是否回复：0=否，1=是. */
	private Integer isReply;

	/** 是否删除：0=否，1=是. */
	private Integer isDel;

	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getSendId() {
		return sendId;
	}
	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}
	public Long getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public Integer getKind() {
		return kind;
	}
	public void setKind(Integer kind) {
		this.kind = kind;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsReply() {
		return isReply;
	}
	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "Suggest [companyId=" + companyId + ", deptId=" + deptId
				+ ", sendId=" + sendId + ", receiveId=" + receiveId
				+ ", sendDate=" + sendDate + ", kind=" + kind
				+ ", content=" + content + ", isReply=" + isReply
				+ ", isDel=" + isDel + ", eid=" + getEid() + "]";
	}
}