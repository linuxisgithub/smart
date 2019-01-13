package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:35
 *
 */
public class SysDepartmentInfoSggl extends BaseModel {

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

	/** 邮编. */
	private String zipCode;

	/** 银行卡持有人. */
	private String bankCardHavePeople;

	/** 银行账户. */
	private String bankCardAccount;

	/** 开户行. */
	private String bank;

	/** 最大食阁用户数. */
	private Integer maxNum;

	/** 已激活食阁用户数. */
	private Integer activationNum;

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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getBankCardHavePeople() {
		return bankCardHavePeople;
	}
	public void setBankCardHavePeople(String bankCardHavePeople) {
		this.bankCardHavePeople = bankCardHavePeople;
	}
	public String getBankCardAccount() {
		return bankCardAccount;
	}
	public void setBankCardAccount(String bankCardAccount) {
		this.bankCardAccount = bankCardAccount;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
		return "SysDepartmentInfoSggl [deptId=" + deptId + ", deptName=" + deptName
				+ ", companyId=" + companyId + ", myId=" + myId
				+ ", address=" + address + ", zipCode=" + zipCode
				+ ", bankCardHavePeople=" + bankCardHavePeople + ", bankCardAccount=" + bankCardAccount
				+ ", bank=" + bank + ", maxNum=" + maxNum
				+ ", activationNum=" + activationNum + ", eid=" + getEid() + "]";
	}
}