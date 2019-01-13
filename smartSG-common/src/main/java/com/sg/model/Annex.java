package com.sg.model;

import javacommon.base.BaseModel;
import org.hibernate.validator.constraints.Length;


public class Annex extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3650065075451358132L;

	/**
     * 公司ID       db_column: COMPANY_ID
     */
    private Long companyId;

    /**
     * 用户ID       db_column: USER_ID
     */
    private Long userId;

    /**
     * 文件路径       db_column: PATH
     */
    @Length(max=255)
    private String path;

    /**
     * 文件原名       db_column: SRC_NAME
     */
    @Length(max=100)
    private String srcName;

    /**
     * 文件类型       db_column: TYPE
     */
    @Length(max=10)
    private String type;

    /**
     * 业务ID       db_column: BID
     */
    private Long bid;

    /**
     * 主业务ID     db_column: PID
     */
    private Long pid;

    /**
     * 业务类型,1=采购订单,2=采购开单,3=采购退货,4=采购快照,5=销售开单,6=销售退货,7=销售快照,8=仓库其它入库,
     * 9=仓库其它出库,10=仓库快照,11=其它资金收入,12=其它资金支出,13=资金快照,14=客户关怀,15=客户拜访,
     * 16=客户通知,17=往来单位/客户资料,18=客户快照,19=货品资料,20=资金帐户
     *
     * db_column: BTYPE
     */
    private Integer btype;

    /**
     * 文件名       db_column: NAME
     */
    @Length(max=100)
    private String name;

    /**
     * 文件大小, 单位:字节数       db_column: FILE_SIZE
     */
    private Long fileSize;

    /**
     * 显示方式，1=附件方式，2=直接显示       db_column: SHOW_TYPE
     */
    private Integer showType;

    /**
     * 缩略图路径       db_column: PATH_SMALL
     */
    @Length(max=255)
    private String pathSmall;


    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

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
        this.path = path == null ? null : path.trim();
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName == null ? null : srcName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Integer getBtype() {
        return btype;
    }

    public void setBtype(Integer btype) {
        this.btype = btype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public String getPathSmall() {
        return pathSmall;
    }

    public void setPathSmall(String pathSmall) {
        this.pathSmall = pathSmall == null ? null : pathSmall.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Annex{" +
                "eid=" + getEid() +
                ", createTime='" + getCreateTime() + '\'' +
                ", companyId=" + companyId +
                ", userId=" + userId +
                ", path='" + path + '\'' +
                ", srcName='" + srcName + '\'' +
                ", type='" + type + '\'' +
                ", bid=" + bid +
                ", pid=" + pid +
                ", btype=" + btype +
                ", name='" + name + '\'' +
                ", fileSize=" + fileSize +
                ", showType=" + showType +
                ", pathSmall='" + pathSmall + '\'' +
                '}';
    }
}