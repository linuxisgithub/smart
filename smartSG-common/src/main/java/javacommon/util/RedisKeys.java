package javacommon.util;

public class RedisKeys {

	// 币种
	public static String getCurrencyKey(Long userId) {
		return "currency:" + userId;
	}

	// 资金账户
	public static String getFundsAccountKey(Long userId) {
		return "fundsAccount:" + userId;
	}

	// 资金账户
	public static String getContactsKey(Long userId) {
		return "contacts:" + userId;
	}

	public static String getTextKey(String account) {
		return "erp:" + account + ":text";
	}
	
	public static String getOaTextKey(String account) {
		return "oa:" + account + ":text";
	}

}
