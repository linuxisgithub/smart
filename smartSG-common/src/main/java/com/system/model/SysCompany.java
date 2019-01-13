package com.system.model;

import javacommon.base.BaseModel;

/**
 *
 * @author chaoxiang
 * @date 2017-08-31 11:15:51
 *
 */
public class SysCompany extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 公司名称. */
	private String name;

	/** 注册时使用的手机号码. */
	private String telephone;

	/** 公司级别，1=现在办公，2=商务办公，3=企业运营. */
	private Integer level;

	/** 是否付费. */
	private Integer isVip;

	/** 进入超级授权的密码. */
	private String adminPassword;

	/** 付费过期时间. */
	private String expireTime;

	/** 所属地区. */
	private String region;

	/** 固定电话. */
	private String fixedTelephone;

	/** 用户私有域名地址. */
	private String dnsAddress;

	/** 公司简称. */
	private String shortName;

	/** 公司编号. */
	private String code;

	/** 公司人数. */
	private Integer staffNum;

	/** 公司地址. */
	private String address;

	/** 法定注册人. */
	private String legalPerson;

	/** 官网网址. */
	private String webAddress;
	
	/** 例子账状态：1=需要提示下载例子账，2=下载过例子账，3=清空过下载例子账或者跳过下载例子账. */
	private Integer caseStatus;
	
	/** 例子账状态：是否需要引导. */
	private Integer needLead;
	
	/** 付费级别，1=10个用户，2=20个用户... */
	private Integer vipLevel;
	
	/** 是否是城南园区那个用户，1=是，0=否 */
	private Integer isSpec;
	/** 是否有定位功能，1=是，2=否 */
	private Integer isLocation;
	/** 是否有外勤功能，1=是，2=否 */
	private Integer isOutWork;
	
	/** 是否需要加密锁，1=是，0=否 */
	private Integer needLock;
	
	/** 1=高级版，2=专业版，3=标准版. */
	private Integer version;
	
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getNeedLock() {
		return needLock;
	}
	public void setNeedLock(Integer needLock) {
		this.needLock = needLock;
	}
	public Integer getIsLocation() {
		return isLocation;
	}
	public void setIsLocation(Integer isLocation) {
		this.isLocation = isLocation;
	}
	public Integer getIsOutWork() {
		return isOutWork;
	}
	public void setIsOutWork(Integer isOutWork) {
		this.isOutWork = isOutWork;
	}
	public Integer getIsSpec() {
		return isSpec;
	}
	public void setIsSpec(Integer isSpec) {
		this.isSpec = isSpec;
	}
	/**
	 * 获取用户的存储空间大小
	 * @return
	 */
	public double maxFileSizeNum() {
		if(getEid() == 640){
			return 50D;
		}
		if(getEid() == 813){
			return 100D;
		}
		if(getVipLevel() == 8){
			return 200D;
		}
		return getVipLevel() * 20.0;
	}
	/**
	 * 获取用户可启用的最大系统用户数
	 * @return
	 */
	public int maxUserNum() {
		if(getEid() == 640){
			return 55;
		}
		if(getEid() == 813){
			return 5;
		}
		if(getVipLevel() == 8){
			return 100;
		}
		return getVipLevel() * 10;
	}
	/**
	 * 获取用户可启用的最大IM用户数
	 * @return
	 */
	public int maxImUserNum() {
		if(getEid() == 640){
			return 55;
		}
		if(getEid() == 813){
			return 15;
		}
		if(getVipLevel() == 8){
			return 300;
		}
		return getVipLevel() * 30;
	}
	public Integer getVipLevel() {
		if(vipLevel == null) {
			vipLevel = 1;
		}
		return vipLevel;
	}
	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}
	public Integer getNeedLead() {
		if(needLead == null) {
			needLead = 1;
		}
		return needLead;
	}
	public void setNeedLead(Integer needLead) {
		this.needLead = needLead;
	}
	public Integer getCaseStatus() {
		if(caseStatus == null) {
			caseStatus = 3;
		}
		return caseStatus;
	}
	public void setCaseStatus(Integer caseStatus) {
		this.caseStatus = caseStatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getFixedTelephone() {
		return fixedTelephone;
	}
	public void setFixedTelephone(String fixedTelephone) {
		this.fixedTelephone = fixedTelephone;
	}
	public String getDnsAddress() {
		return dnsAddress;
	}
	public void setDnsAddress(String dnsAddress) {
		this.dnsAddress = dnsAddress;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getStaffNum() {
		return staffNum;
	}
	public void setStaffNum(Integer staffNum) {
		this.staffNum = staffNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getWebAddress() {
		return webAddress;
	}
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public SysCompany() {
		super();
	}
	public SysCompany(String name, String address, String webAddress, String legalPerson, String telephone,
			String fixedTelephone, Integer level, Integer staffNum) {
		super();
		this.name = name;
		this.address = address;
		this.webAddress = webAddress;
		this.legalPerson = legalPerson;
		this.telephone = telephone;
		this.fixedTelephone = fixedTelephone;
		this.level = level;
		this.staffNum = staffNum;
	}
	@Override
	public String toString() {
		return "SysCompany [name=" + name + ", telephone=" + telephone
				+ ", level=" + level + ", isVip=" + isVip
				+ ", adminPassword=" + adminPassword + ", expireTime=" + expireTime
				+ ", region=" + region + ", fixedTelephone=" + fixedTelephone
				+ ", dnsAddress=" + dnsAddress + ", shortName=" + shortName
				+ ", code=" + code + ", staffNum=" + staffNum
				+ ", address=" + address + ", legalPerson=" + legalPerson
				+ ", webAddress=" + webAddress + ", eid=" + getEid() + "]";
	}
	public Integer getVersion() {
		if(version == null) {
			version = 1;
		}
		return version;
	}
}