package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-17 14:03:49
 *
 */
public class Reply extends BaseModel {

	public Reply (){
		super();
	}
	public Reply (Long eid, Integer sendIsDel, Integer receiveIsDel){
		setEid(eid);
		this.sendIsDel = sendIsDel;
		this.receiveIsDel = receiveIsDel;
	}
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 公司ID. */
	private Long companyId;

	/** 部门ID. */
	private Long deptId;

	/** 发送人ID. */
	private Long sendId;

	/** 投诉建议ID. */
	private Long bid;

	/** 回复内容. */
	private String content;

	/** 接收人是否删除：0=否，1=是. */
	private Integer receiveIsDel;
	
	/** 发送人是否删除：0=否，1=是. */
	private Integer sendIsDel;
	
	public Integer getReceiveIsDel() {
		return receiveIsDel;
	}
	public void setReceiveIsDel(Integer receiveIsDel) {
		this.receiveIsDel = receiveIsDel;
	}
	public Integer getSendIsDel() {
		return sendIsDel;
	}
	public void setSendIsDel(Integer sendIsDel) {
		this.sendIsDel = sendIsDel;
	}

	/** 回复日期. */
	private String replyDate;

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
	public Long getBid() {
		return bid;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	
	@Override
	public String toString() {
		return "Reply [companyId=" + companyId + ", deptId=" + deptId + ", sendId=" + sendId + ", bid=" + bid
				+ ", content=" + content + ", receiveIsDel=" + receiveIsDel + ", sendIsDel=" + sendIsDel
				+ ", replyDate=" + replyDate + ", eid=" + getEid() + "]";
	}

	
}