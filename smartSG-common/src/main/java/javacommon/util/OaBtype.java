package javacommon.util;

public enum OaBtype {

	TSJY_CODE(1,"TSJY","投诉建议"),
	
	PUSH_APPROVE(118, "118", "批审推送"), 
	PUSH_FINISH(121, "121", "批审完成推送"), 

	MESSAGE_SER_TYPE_001(00001, "M00001", "手动审批"),
	MESSAGE_SER_TYPE_002(00002, "M00002", "自动审批"), 
	MESSAGE_SER_TYPE_003(00003, "M00003", "普通验证"), 
	MESSAGE_SER_TYPE_004(00004, "M00004", "注册成功"), 
	MESSAGE_SER_TYPE_005(00005,"M00005", "自动提醒"), 
	MESSAGE_SER_TYPE_006(00006, "M00006","手动提醒"), 
	MESSAGE_SER_TYPE_007(00007, "M00007", "聊天发送");

	private int btype;
	private String code;
	private String value;

	private OaBtype(int btype, String code, String value) {
		this.btype = btype;
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getBtype() {
		return btype;
	}

	public void setBtype(int btype) {
		this.btype = btype;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static OaBtype get(int btype) {
		OaBtype[] values = OaBtype.values();
		for (OaBtype value : values) {
			if (btype == value.getBtype()) {
				return value;
			}
		}
		return null;
	}

	public static String getValueByType(int btype) {
		OaBtype[] values = OaBtype.values();
		for (OaBtype value : values) {
			if (btype == value.getBtype()) {
				return value.getValue();
			}
		}
		return null;
	}
}
