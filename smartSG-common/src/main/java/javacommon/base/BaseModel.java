package javacommon.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.sg.model.Annex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共对象 所有model都要继承此类
 *
 * @author Andy
 */
public class BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 8484697384425520946L;

	private Long eid;

	private String createTime;

	private String updateTime;

	@JSONField(serialize = false)
	private String cc;

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	@JSONField(serialize = false)
	private Integer oaBtype;
	@JSONField(serialize = false)
	private String annex;
	@JSONField(serialize = false)
	private String romoveAnnexId;

	// 是否含有审批 0 有 1 没有
	@JSONField(serialize = false)
	private int isApproval;

	public int getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(int isApproval) {
		this.isApproval = isApproval;
	}
	@JSONField(serialize = false)
	private List<Annex> annexList;

	public List<Annex> getAnnexList() {
		return annexList;
	}

	public void setAnnexList(List<Annex> annexList) {
		this.annexList = annexList;
	}

	public String getRomoveAnnexId() {
		return romoveAnnexId;
	}

	public void setRomoveAnnexId(String romoveAnnexId) {
		this.romoveAnnexId = romoveAnnexId;
	}

	@Override
	public String toString() {
		return "BaseModel [eid=" + eid + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", oaBtype=" + oaBtype
				+ ", annex=" + annex + ", romoveAnnexId=" + romoveAnnexId
				+ ", isApproval=" + isApproval + ", annexList=" + annexList
				+ ", returnParam=" + returnParam + ", tableIndex=" + tableIndex
				+ "]";
	}

	public String getAnnex() {
		return annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}

	/**
	 * 返回值
	 */
	@JSONField(serialize = false)
	private Map<String, Object> returnParam;

	@JSONField(serialize = false)
	private String tableIndex;

	public String getTableIndex() {
		return tableIndex;
	}

	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}

	public Long getEid() {
		return eid;
	}

	public void setEid(Long eid) {
		this.eid = eid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getOaBtype() {
		return oaBtype;
	}

	public void setOaBtype(Integer oaBtype) {
		this.oaBtype = oaBtype;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Map<String, Object> getReturnParam() {
		return returnParam;
	}

	public Object getReturnParam(String key) {
		if (returnParam == null) {
			returnParam = new HashMap<>();
			returnParam.put("status", "200");
		}
		return returnParam.get(key);
	}

	public void setReturnParam(Map<String, Object> returnParam) {
		this.returnParam = returnParam;
	}

	public void setParam(String key, Object value) {
		if (returnParam == null) {
			returnParam = new HashMap<>();
		}
		returnParam.put(key, value);
	}
}
