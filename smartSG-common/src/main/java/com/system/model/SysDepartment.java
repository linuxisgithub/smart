package com.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import javacommon.base.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duwufeng
 * @date 2017-07-05 09:28:02
 *
 */
public class SysDepartment extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 上级部门ID. */
	private Long pid;
	
	/** 上级部门名称. */
	private String pname;

	/** 公司ID. */
	private Long companyId;

	/** 部门名称. */
	@NotBlank(message = "机构名称不能为空!")
	private String name;

	/** 部门代码. */
	private String code;

	/** 编制人数. */
	private Integer staffNum;

	/** 部门层级，1=公司层面，2=一级部门，3=二级部门，4=驻外机构. */
	private Integer type;

	/** 部门类型，1=本公司部门、2=食阁管理... . */
	private Integer dtype;
	
	/** 摊位中文名称. */
	private String nameCh;

	/** 正职称谓，0=董事长、1=总裁、2=总经理、3=总监、4=老板、5=部长、6=主任、7=管理员、8=组长. */
	private Integer mainJob;

	/** 副职称谓，0=副董事长、1=副总裁、2=副总经理、3=副总监、4=副经理、5=副部长、6=副主任、7=副管理员、8=副组长. */
	private Integer lessJob;

	/** 助理称谓，1=秘书，2=助理. */
	private Integer assistJob;
	
	private String eid_staffNum;

	private String mechanismTwo;

	/** 联系人信息 公用 */
	@JSONField(serialize = false)
	private List<SysDepartmentInfoContacts> contactsList = new ArrayList<>();

	/** 食阁信息 */
	@JSONField(serialize=false)
	private SysDepartmentInfoSg sg;

	/** 食阁管理公司信息 */
	@JSONField(serialize=false)
	private SysDepartmentInfoSggl sggl;

	/** 食阁拥有者信息 */
	@JSONField(serialize=false)
	private SysDepartmentInfoSgyyz sgyyz;

	/** 摊位信息 */
	@JSONField(serialize=false)
	private SysDepartmentInfoTw tw;

	public List<SysDepartmentInfoContacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<SysDepartmentInfoContacts> contactsList) {
		this.contactsList = contactsList;
	}

	public SysDepartmentInfoSg getSg() {
		return sg;
	}

	public void setSg(SysDepartmentInfoSg sg) {
		this.sg = sg;
	}

	public SysDepartmentInfoSggl getSggl() {
		return sggl;
	}

	public void setSggl(SysDepartmentInfoSggl sggl) {
		this.sggl = sggl;
	}

	public SysDepartmentInfoSgyyz getSgyyz() {
		return sgyyz;
	}

	public void setSgyyz(SysDepartmentInfoSgyyz sgyyz) {
		this.sgyyz = sgyyz;
	}

	public SysDepartmentInfoTw getTw() {
		return tw;
	}

	public void setTw(SysDepartmentInfoTw tw) {
		this.tw = tw;
	}

	public String getMechanismTwo() {
		return mechanismTwo;
	}

	public void setMechanismTwo(String mechanismTwo) {
		this.mechanismTwo = mechanismTwo;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}


	public void setPname(String pname) {
		this.pname = pname;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Integer getDtype() {
		return dtype;
	}


	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}


	public String getNameCh() {
		return nameCh;
	}


	public void setNameCh(String nameCh) {
		this.nameCh = nameCh;
	}


	public Integer getMainJob() {
		return mainJob;
	}


	public void setMainJob(Integer mainJob) {
		this.mainJob = mainJob;
	}


	public Integer getLessJob() {
		return lessJob;
	}


	public void setLessJob(Integer lessJob) {
		this.lessJob = lessJob;
	}


	public Integer getAssistJob() {
		return assistJob;
	}


	public void setAssistJob(Integer assistJob) {
		this.assistJob = assistJob;
	}


	public String getEid_staffNum() {
		return eid_staffNum;
	}


	public void setEid_staffNum(String eid_staffNum) {
		this.eid_staffNum = eid_staffNum;
	}


	@Override
	public String toString() {
		return "SysDepartment [pid=" + pid + ", companyId=" + companyId
				+ ", name=" + name + ", code=" + code
				+ ", staffNum=" + staffNum + ", type=" + type
				+ ", dtype=" + dtype + ", mainJob=" + mainJob
				+ ", lessJob=" + lessJob + ", assistJob=" + assistJob
				+ ", eid=" + getEid() + "]";
	}
}