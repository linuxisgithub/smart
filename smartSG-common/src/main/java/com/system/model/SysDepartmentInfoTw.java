package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:23:23
 *
 */
public class SysDepartmentInfoTw extends BaseModel {

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

	/** 门牌号. */
	private String houseNumber;

	/** 支付宝用户名称. */
	private String alipayName;

	/** 支付宝账户. */
	private String alipayAccount;

	/** 银行账户. */
	private String bankCardAccount;

	/** 开户行. */
	private String bank;

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
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getAlipayName() {
		return alipayName;
	}
	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
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

	@Override
	public String toString() {
		return "SysDepartmentInfoTw [deptId=" + deptId + ", deptName=" + deptName
				+ ", companyId=" + companyId + ", myId=" + myId
				+ ", houseNumber=" + houseNumber + ", alipayName=" + alipayName
				+ ", alipayAccount=" + alipayAccount + ", bankCardAccount=" + bankCardAccount
				+ ", bank=" + bank + ", eid=" + getEid() + "]";
	}
}