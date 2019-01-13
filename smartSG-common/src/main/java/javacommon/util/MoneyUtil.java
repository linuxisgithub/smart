package javacommon.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 金额转换位数
 * 
 * @author 卢子敬
 * 
 */
public class MoneyUtil {

	/**
	 * 汉语中数字大写
	 */
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
			"伍", "陆", "柒", "捌", "玖" };
	/**
	 * 汉语中货币单位大写，这样的设计类似于占位符
	 */
	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
			"拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
			"佰", "仟" };
	/**
	 * 特殊字符：整
	 */
	private static final String CN_FULL = "整";
	/**
	 * 特殊字符：负
	 */
	private static final String CN_NEGATIVE = "负";
	/**
	 * 金额的精度，默认值为2
	 */
	private static final int MONEY_PRECISION = 2;
	/**
	 * 特殊字符：零元整
	 */
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	public static Map<String, Object> forMat(Double money) throws Exception {

		BigDecimal totalMoney = new BigDecimal(money.toString());

		Map<String, Object> resultMap = new HashMap<>();

		String moneyStr1 = totalMoney.toString();

		if (moneyStr1.indexOf(".") != -1) {
			// 拆分小数
			String moneyNum = moneyStr1.substring(moneyStr1.indexOf(".") + 1,
					moneyStr1.length() - 1);
			String moneyNum2 = moneyStr1.substring(moneyStr1.length() - 1);
			resultMap.put("xiaoshu1", moneyNum.length() == 0 ? 0 : moneyNum);
			resultMap.put("xiaoshu2", moneyNum2.length() == 0 ? 0 : moneyNum2);
		}

		// 拆分去除小数保留个位以上
		String moneyStr = moneyStr1.substring(0, moneyStr1.indexOf("."));

		switch (moneyStr.length()) {
		// 个位数
		case 1:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			break;
		case 2:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			break;
		case 3:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			resultMap.put("bai", moneyStr.substring(moneyStr.length() - 3,
					moneyStr.length() - 2));
			break;
		case 4:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			resultMap.put("bai", moneyStr.substring(moneyStr.length() - 3,
					moneyStr.length() - 2));
			resultMap.put("qian", moneyStr.substring(moneyStr.length() - 4,
					moneyStr.length() - 3));
			break;
		// 万位数
		case 5:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			resultMap.put("bai", moneyStr.substring(moneyStr.length() - 3,
					moneyStr.length() - 2));
			resultMap.put("qian", moneyStr.substring(moneyStr.length() - 4,
					moneyStr.length() - 3));
			resultMap.put("wang", moneyStr.substring(moneyStr.length() - 5,
					moneyStr.length() - 4));
			break;
		case 6:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			resultMap.put("bai", moneyStr.substring(moneyStr.length() - 3,
					moneyStr.length() - 2));
			resultMap.put("qian", moneyStr.substring(moneyStr.length() - 4,
					moneyStr.length() - 3));
			resultMap.put("wang", moneyStr.substring(moneyStr.length() - 5,
					moneyStr.length() - 4));
			resultMap.put("shiwang", moneyStr.substring(moneyStr.length() - 6,
					moneyStr.length() - 5));
			break;
		case 7:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			resultMap.put("bai", moneyStr.substring(moneyStr.length() - 3,
					moneyStr.length() - 2));
			resultMap.put("qian", moneyStr.substring(moneyStr.length() - 4,
					moneyStr.length() - 3));
			resultMap.put("wang", moneyStr.substring(moneyStr.length() - 5,
					moneyStr.length() - 4));
			resultMap.put("shiwang", moneyStr.substring(moneyStr.length() - 6,
					moneyStr.length() - 5));
			resultMap.put("baiwang", moneyStr.substring(moneyStr.length() - 7,
					moneyStr.length() - 6));
			break;
		// 千万位数
		case 8:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			resultMap.put("bai", moneyStr.substring(moneyStr.length() - 3,
					moneyStr.length() - 2));
			resultMap.put("qian", moneyStr.substring(moneyStr.length() - 4,
					moneyStr.length() - 3));
			resultMap.put("wang", moneyStr.substring(moneyStr.length() - 5,
					moneyStr.length() - 4));
			resultMap.put("shiwang", moneyStr.substring(moneyStr.length() - 6,
					moneyStr.length() - 5));
			resultMap.put("baiwang", moneyStr.substring(moneyStr.length() - 7,
					moneyStr.length() - 6));
			resultMap.put("qianwang", moneyStr.substring(moneyStr.length() - 8,
					moneyStr.length() - 7));
			break;
		// 亿位数
		case 9:
			resultMap.put("gew", moneyStr.substring(moneyStr.length() - 1,
					moneyStr.length()));
			resultMap.put("shi", moneyStr.substring(moneyStr.length() - 2,
					moneyStr.length() - 1));
			resultMap.put("bai", moneyStr.substring(moneyStr.length() - 3,
					moneyStr.length() - 2));
			resultMap.put("qian", moneyStr.substring(moneyStr.length() - 4,
					moneyStr.length() - 3));
			resultMap.put("wang", moneyStr.substring(moneyStr.length() - 5,
					moneyStr.length() - 4));
			resultMap.put("shiwang", moneyStr.substring(moneyStr.length() - 6,
					moneyStr.length() - 5));
			resultMap.put("baiwang", moneyStr.substring(moneyStr.length() - 7,
					moneyStr.length() - 6));
			resultMap.put("qianwang", moneyStr.substring(moneyStr.length() - 8,
					moneyStr.length() - 7));
			resultMap.put("yi", moneyStr.substring(moneyStr.length() - 9,
					moneyStr.length() - 8));
			break;
		default:
			break;
		}

		return resultMap;
	}

	public static boolean isDouble(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 把输入的金额转换为汉语中人民币的大写
	 * 
	 * @param numberOfMoney
	 *            输入的金额
	 * @return 对应的汉语大写
	 */
	public static String moneyBigFormat(BigDecimal numberOfMoney) {
		StringBuffer sb = new StringBuffer();
		// -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
		// positive.
		int signum = numberOfMoney.signum();
		// 零元整的情况
		if (signum == 0) {
			return CN_ZEOR_FULL;
		}
		// 这里会进行金额的四舍五入
		long number = numberOfMoney.movePointRight(MONEY_PRECISION)
				.setScale(0, 4).abs().longValue();
		// 得到小数点后两位值
		long scale = number % 100;
		int numUnit = 0;
		int numIndex = 0;
		boolean getZero = false;
		// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
		if (!(scale > 0)) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}
		if ((scale > 0) && (!(scale % 10 > 0))) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}
		int zeroSize = 0;
		while (true) {
			if (number <= 0) {
				break;
			}
			// 每次获取到最后一个数
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero)) {
					sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}

}
