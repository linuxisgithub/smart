package javacommon.util;

public class MobileCode {
	private static final String str_num = "0123456789";

	private static String generateBaseCode(int k) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < k; i++) {
			int rand = (int) (Math.random() * 10);
			sb.append(str_num.charAt(rand));
		}
		return sb.toString();
	}
	/**
	 * 生产2位数字的验证码
	 */
	public static String generate2NumCode() {
		return generateBaseCode(2);
	}
	/**
	 * 生产4位数字的验证码
	 */
	public static String generate4NumCode() {
		return generateBaseCode(4);
	}

	/**
	 * 生产6位数字验证码
	 */
	public static String generate6NumCode() {
		return generateBaseCode(6);
	}

	/**
	 * 缺省方式(生成4位数验证码)
	 */
	public static String generateTextCode() {
		return generate4NumCode();
	}

}
