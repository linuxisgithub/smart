package com.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import javacommon.base.BaseModel;
import javacommon.util.DateUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author duwufeng
 * @date 2017-07-05 18:06:02
 * 
 */
public class SysUser extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 员工编号. */
	private String staffNo;

	/** 创建人ID. */
	private Long createUserId;

	/** 公司ID. */
	private Long companyId;

	/** 部门ID. */
	private Long deptId;
	
	/** 部门名称. */
	private String deptName;

	/** 部门代码. */
	private String deptCode;

	/** 上级主管ID. */
	private Long managerId;

	/** 层级归属：1=领导层；2=员工层;3=系统管理员. */
	private Integer level;

	/** 学历，1=初中，2=高中，3=大专，4=本科，5=硕士，6=博士，7=其他. */
	private Integer education;

	/** 职务. */
	private String job;

	/** 任职状态，1=实习，2=试用期，3=在职，4=离职，5=其他. */
	private Integer jobStatus;

	/** 姓名. */
	@NotEmpty(message = "姓名不能为空!")
	private String name;

	/** 籍贯. */
	private String originAddr;

	/** 家庭住址. */
	private String address;

	/** 联系住址. */
	private String linkAddress;

	/** 联系电话. */
	private String telephone;

	/** QQ. */
	private String qq;

	/** 微信. */
	private String wechat;

	/** 婚姻状态,1=未婚未育，2=未婚已育，3=已婚未育，4=已婚已育，5=其他. */
	private Integer marry;

	/** 性别, 1=男, 2=女. */
	private Integer sex;

	/** 邮箱. */
	private String email;

	/** 薪酬. */
	private BigDecimal salary;

	/** 用户账户. */
	@NotBlank(message = "账号不能为空!")
	private String account;

	/** 用户密码. */
	private String password;

	/** 用户密码-显示. */
	private String showPassword;

	/** IM帐号. */
	private String imAccount;

	/** 头像. */
	private String icon;

	/** 用户类型，1=公司内部用户，2=食阁管理公司用户，3=食阁用户，4=档口(摊位)用户，5=食阁拥有者用户，11=客户端用户. */
	private Integer userType;

	/** 入职时间. */
	private String employTime;

	/** 最后登录时间. */
	private String lastLoginTime;

	/** 账号授权：1=启用，2=停用. */
	private Integer status;

	/** 是否黑名单：0=否、1=是. */
	private Integer isBlack;
	/** 0=正常、1=已删除. */
	private Integer isDel;

	/** 出生年月. */
	private String birthDate;

	/** im账号状态:0=停用，1=启用. */
	private Integer imStatus;

	/** 年龄. */
	private Integer age;

	/** 合同形式，1=正式合同，2=实习协议，3=雇佣协议,4=兼职协议,5=试用期协议，5=无固定期限合同，6=其他.' */
	private Integer contract;

	/** 工作圈封面图片地址. */
	private String workImg;
	
	/** 毕业学校. */
	private String school;
	
	/** 身份证号. */
	private String idCard;

	private String appOs;
	
	private Integer busMess;
	
	private Integer chatMess;

	public Integer getBusMess() {
		if(busMess == null) {
			busMess = 1;
		}
		return busMess;
	}

	public void setBusMess(Integer busMess) {
		this.busMess = busMess;
	}

	public Integer getChatMess() {
		if(chatMess == null) {
			chatMess = 1;
		}
		return chatMess;
	}

	public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setChatMess(Integer chatMess) {
		this.chatMess = chatMess;
	}

	public String getAppOs() {
		return appOs;
	}

	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}

	private String diriveToken;

	public String getDiriveToken() {
		return diriveToken;
	}

	public void setDiriveToken(String diriveToken) {
		this.diriveToken = diriveToken;
	}
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getWorkImg() {
		return workImg;
	}

	public void setWorkImg(String workImg) {
		this.workImg = workImg;
	}

	public Integer getContract() {
		return contract;
	}

	public void setContract(Integer contract) {
		this.contract = contract;
	}

	public Integer getAge() {
		if(age == null) {
			if (birthDate != null) {
				try {
					DateFormat df = new SimpleDateFormat("yyyy-MM");
					Date birthTime = df.parse(birthDate);
					Date nowDate = new Date();
					int age = DateUtil.yearsBetween(nowDate, birthTime);
					this.age = age;
				} catch (ParseException e) {
				}
			}
		}
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getImStatus() {
		return imStatus;
	}

	public void setImStatus(Integer imStatus) {
		this.imStatus = imStatus;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}


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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginAddr() {
		return originAddr;
	}

	public void setOriginAddr(String originAddr) {
		this.originAddr = originAddr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public Integer getMarry() {
		return marry;
	}

	public void setMarry(Integer marry) {
		this.marry = marry;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImAccount() {
		return imAccount;
	}

	public void setImAccount(String imAccount) {
		this.imAccount = imAccount;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getEmployTime() {
		return employTime;
	}

	public void setEmployTime(String employTime) {
		this.employTime = employTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return "SysUser [staffNo=" + staffNo + ", createUserId=" + createUserId
				+ ", companyId=" + companyId + ", deptId=" + deptId
				+ ", deptName=" + deptName + ", deptCode=" + deptCode
				+ ", managerId=" + managerId + ", level=" + level
				+ ", education=" + education + ", job=" + job + ", jobStatus="
				+ jobStatus + ", name=" + name + ", originAddr=" + originAddr
				+ ", address=" + address + ", linkAddress=" + linkAddress
				+ ", telephone=" + telephone + ", qq=" + qq + ", wechat="
				+ wechat + ", marry=" + marry + ", sex=" + sex + ", email="
				+ email + ", salary=" + salary + ", account=" + account
				+ ", password=" + password + ", showPassword=" + showPassword
				+ ", imAccount=" + imAccount + ", icon=" + icon + ", userType="
				+ userType + ", employTime=" + employTime + ", lastLoginTime="
				+ lastLoginTime + ", status=" + status + ", birthDate="
				+ birthDate + ", imStatus=" + imStatus + ", age=" + age
				+ ", contract=" + contract + ", workImg=" + workImg
				+ ", school=" + school + ", idCard=" + idCard + ", appOs="
				+ appOs + ", diriveToken=" + diriveToken + "]";
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getShowPassword() {
		return showPassword;
	}

	public void setShowPassword(String showPassword) {
		this.showPassword = showPassword;
	}

}