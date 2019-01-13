package javacommon.util;

import java.util.regex.Pattern;

public class ValidateUtil {

	/** 正则表达式：验证手机号. **/
	public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	
	/** 正则表达式：验证区号+座机号码+分机号码. **/
	public static final String REGEX_FIXED_PHONE = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +  
            "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";

	/** 正则表达式：验证邮箱. **/
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/** 正则表达式：验证汉字. **/
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/** 正则表达式：验证身份证. **/
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
	
	/** 正则表达式：验证正整数. **/
	public static final String REGEX_INTEGER = "^[0-9]*[1-9][0-9]*$";

	/**
	 * 小数验证，返回：true=验证通过，false=验证不通过
	 * 
	 * @param value 要验证的数字
	 * @param intNum  多少位整数
	 * @param decimalNum 多少位小数
	 * @return 返回：true=验证通过，false=验证不通过
	 */
	public static boolean validateDecimal(String value, int intNum, int decimalNum) {
		if (value == null) {
			return false;
		}
		String reguStr = "^0{1}([.]\\d{1," + decimalNum + "})?$|^[1-9]\\d{0," + (intNum - 1) + "}([.]{1}[0-9]{1," + decimalNum + "})?$";
		return Pattern.matches(reguStr, value);
	}
	
	/**
	 * 校验正整数
	 * 
	 * @param value
	 * @return 返回：true=验证通过，false=验证不通过
	 */
	public static boolean validateInteger(String value) {
		if (value == null) {
			return false;
		}
		return Pattern.matches(REGEX_INTEGER, value);
	}

	/**
	 * 校验座机号
	 * 
	 * @param value
	 * @return 返回：true=验证通过，false=验证不通过
	 */
	public static boolean validateFixedPhone(String value) {
		if (value == null) {
			return false;
		}
		return Pattern.matches(REGEX_FIXED_PHONE, value);
	}
	
	/**
	 * 校验手机号
	 * 
	 * @param value
	 * @return 返回：true=验证通过，false=验证不通过
	 */
	public static boolean validateMobile(String value) {
		if (value == null) {
			return false;
		}
		return Pattern.matches(REGEX_MOBILE, value);
	}

	/**
	 * 校验邮箱
	 * 
	 * @param value
	 * @return 返回：true=验证通过，false=验证不通过
	 */
	public static boolean validateEmail(String value) {
		if (value == null) {
			return false;
		}
		return Pattern.matches(REGEX_EMAIL, value);
	}

	/**
	 * 校验汉字
	 * 
	 * @param value
	 * @return 返回：true=验证通过，false=验证不通过
	 */
	public static boolean validateChinese(String value) {
		if (value == null) {
			return false;
		}
		return Pattern.matches(REGEX_CHINESE, value);
	}

	/**
	 * 校验身份证
	 * 
	 * @param value
	 * @return 返回：true=验证通过，false=验证不通过
	 */
	public static boolean validateIDCard(String value) {
		if (value == null) {
			return false;
		}
		return Pattern.matches(REGEX_ID_CARD, value);
	}

	public static void main(String[] args) {
		System.out.println(validateDecimal("",4,2));
	}
}
