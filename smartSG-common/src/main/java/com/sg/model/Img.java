package com.sg.model;

import javacommon.base.BaseModel;

/**
 *
 * @author 超享
 * @date 2019-01-04 09:14:47
 *
 */
public class Img extends BaseModel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 用户ID. */
	private Long userId;

	/** 文件路径. */
	private String path;

	/** 文件原名. */
	private String srcName;

	/** 文件类型. */
	private String type;

	/** 食阁ID. */
	private Long bid;

	/** 加密后文件名. */
	private String name;

	/** 文件大小, 单位:字节数. */
	private Integer fileSize;

	/** 1=食阁装修图片，2=摊位装修图片，3=菜品图片. */
	private Integer dtype;

	/** isDel. */
	private Integer isDel;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public Integer getDtype() {
		return dtype;
	}
	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "Img [userId=" + userId + ", path=" + path
				+ ", srcName=" + srcName + ", type=" + type
				+ ", bid=" + bid + ", name=" + name
				+ ", fileSize=" + fileSize + ", dtype=" + dtype
				+ ", isDel=" + isDel + ", eid=" + getEid() + "]";
	}
}