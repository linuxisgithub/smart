package com.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.sg.model.SgField;
import javacommon.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:06
 *
 */
public class SysDepartmentInfoSg extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 部门ID. */
	private Long deptId;

	/** 部门名称. */
	private String deptName;

	/** 公司ID. */
	private Long companyId;

	/** 自己ID. */
	private Long myId;

	/** 地址. */
	private String address;

	/** 食阁类型:. */
	private Integer type;

	/** 1=HALAL、2=NON、3=MIX . */
	private Integer halal;

	/** 邮编. */
	private String zipCode;

	/** 食阁所有者. */
	private String have;

	/** 最大档口数. */
	private Integer maxNum;

	/** 已激活档口数. */
	private Integer activationNum;

	/** 营业开始时间. */
	private String startTime;
	/** 营业结束时间. */
	private String endTime;

	/** 字段 */
	@JSONField(serialize = false)
	private List<SgField> sgFieldList = new ArrayList<>();

	public List<SgField> getSgFieldList() {
		return sgFieldList;
	}

	public void setSgFieldList(List<SgField> sgFieldList) {
		this.sgFieldList = sgFieldList;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getMyId() {
		return myId;
	}
	public void setMyId(Long myId) {
		this.myId = myId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getHalal() {
		return halal;
	}
	public void setHalal(Integer halal) {
		this.halal = halal;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getHave() {
		return have;
	}
	public void setHave(String have) {
		this.have = have;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	public Integer getActivationNum() {
		return activationNum;
	}
	public void setActivationNum(Integer activationNum) {
		this.activationNum = activationNum;
	}

	@Override
	public String toString() {
		return "SysDepartmentInfoSg [deptId=" + deptId + ", deptName=" + deptName
				+ ", companyId=" + companyId + ", myId=" + myId
				+ ", address=" + address + ", type=" + type
				+ ", halal=" + halal + ", zipCode=" + zipCode
				+ ", have=" + have + ", maxNum=" + maxNum
				+ ", activationNum=" + activationNum
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", eid=" + getEid() + "]";
	}
}