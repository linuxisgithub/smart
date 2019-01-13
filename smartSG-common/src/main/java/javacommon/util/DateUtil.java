package javacommon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 类描述:
 *
 * @author Andy
 * @since 2016/6/2
 */
public class DateUtil {

	/**
	 * format: HH:mm:ss
	 *
	 * @return
	 */
	public static String getTimeStr() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * format: YYYY-MM-dd
	 *
	 * @return
	 */
	public static String getDateStr() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		return format.format(new Date());
	}
	/**
	 *
	 * format: YYYY-MM-dd
	 *
	 * @return
	 */
	public static String getDateDayStr() {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		return format.format(new Date());
	}

	/**
	 * format: YYYY-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static String getDate_TImeStr() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * format: MM-dd HH:mm
	 *
	 * @return
	 */
	public static String getDate_TImeStr1() {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		return format.format(new Date());
	}

	/**
	 * format: mm:ss
	 *
	 * @return
	 */
	public static String getMinStr() {
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		return format.format(new Date());
	}

	/**
	 * format: YYYY
	 *
	 * @return
	 */
	public static String getYearStr() {
		SimpleDateFormat format = new SimpleDateFormat("YYYY");
		return format.format(new Date());
	}

	/**
	 * 返回形如: 1602 字串
	 *
	 * @return
	 */
	public static String getDataYearMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyMM");
		return format.format(new Date());
	}

	/**
	 * 返回形如: 02 字串
	 *
	 * @return
	 */
	public static String getDataMonth() {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		return format.format(new Date());
	}

	/**
	 * 返回形如: 160201 字串
	 *
	 * @return
	 */
	public static String getYearMonthDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		return format.format(new Date());
	}

	/**
	 * 返回形如: 2016-02 字串
	 *
	 * @return
	 */
	public static String getYear_Month() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(new Date());
	}

	/**
	 * 返回形如: 201602 字串
	 *
	 * @return
	 */
	public static String getYearMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(new Date());
	}

	/**
	 * 返回当月的上一个月形如: 201601 字串
	 *
	 * @return
	 */
	public static String getPreYearMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			return String.valueOf(year) + "0" + month;
		} else {
			return String.valueOf(year) + month;
		}
	}

	/**
	 * 获取本周第一天：周日
	 *
	 * @return
	 */
	public static String getFirstDayOfWeek() {
		return getDay(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	}

	/**
	 * 获取本周最后一天：周六
	 *
	 * @return
	 */
	public static String getLastDayOfWeek() {
		return getDay(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	}

	/**
	 * 获取本月第一天
	 *
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		return getDay(Calendar.DAY_OF_MONTH, 1);
	}

	/**
	 * 获取本月最后一天
	 *
	 * @return
	 */
	public static String getLastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return df.format(calendar.getTime());
	}

	/**
	 * 获取当前年第一天
	 *
	 * @return
	 */
	public static String getFirstDayOfYear() {
		return getDay(Calendar.DAY_OF_YEAR, 1);
	}

	/**
	 * 获取当前年最后一天
	 *
	 * @return
	 */
	public static String getLastDayOfYear() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.DAY_OF_YEAR,
				calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		return df.format(calendar.getTime());
	}

	private static String getDay(int field, int value) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(field, value);
		return df.format(calendar.getTime());
	}

	/**
	 * 获取两个日期相差的年数，不满一年算一年
	 *
	 * @param big
	 * @param small
	 * @return
	 */
	public static int yearsBetween(Date big, Date small) {
		Calendar bigCalendar = Calendar.getInstance();
		bigCalendar.setTime(big);
		Calendar smallCalendar = Calendar.getInstance();
		smallCalendar.setTime(small);
		int month = (bigCalendar.get(Calendar.YEAR) - smallCalendar
				.get(Calendar.YEAR))
				* 12
				+ bigCalendar.get(Calendar.MONTH)
				- smallCalendar.get(Calendar.MONTH);
		if (month == 0) {
			return 1;
		} else if (month % 12 == 0) {
			return month / 12;
		} else {
			return month / 12 + 1;
		}
	}

	/**
	 * 获取两个日期相差的天数
	 *
	 * @param big
	 * @param small
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String createTime) throws ParseException {

		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleFormat.parse(createTime);

		long od = date.getTime();
		long now = System.currentTimeMillis();

		// +1 算上当天
		return (int) ((od - now) / (1000 * 3600 * 24)) + 1;
	}

	/**
	 * 计算当前时间和用户注册时间是否在60天内
	 *
	 * @param createTime
	 * @return
	 * @throws ParseException
	 */
	public static int getSixDay(String createTime) throws ParseException {

		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date userCreateTime = simpleFormat.parse(createTime);

		String toDate = simpleFormat.format(new Date());
		long from = userCreateTime.getTime();// 用户注册时间 2016-09-09
		long to = simpleFormat.parse(toDate).getTime(); // 当前系统时间2016-10-09
		int days = (int) ((to - from) / (1000 * 60 * 60 * 24));// 当前年月日 减
																// 注册时间年月日 得出天数

		return days;
	}

	/**
	 * 创建日期
	 *
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date createDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		month--;
		calendar.set(year, month, date);
		return calendar.getTime();
	}

	/**
	 * 获取当天周日期，1=周一，7=周日
	 *
	 * @return
	 */
	public static int getTodayWeekNum() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekNumber == 0) {
			weekNumber = 7;
		}
		return weekNumber;
	}

	/**
	 * 获取昨天周日期，1=周一，7=周日
	 *
	 * @return
	 */
	public static int getYesterdayWeekNum() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekNumber == 0) {
			weekNumber = 7;
		}
		return weekNumber;
	}

	/**
	 * 获取昨天日期
	 *
	 * @return
	 */
	public static String getYesterday() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	public static void main(String[] args) {
	}

	/**
	 * 获取时间+N分钟后的时间
	 *
	 * @return
	 */
	public static Date getDateByAddMinute(Date date, int minute) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);// 设置参数时间
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	/**
	 * 获取时间-N分钟后的时间
	 *
	 * @return
	 */
	public static Date getDateByReduceMinute(Date date, int minute) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);// 设置参数时间
		c.add(Calendar.MINUTE, -minute);
		return c.getTime();
	}

	/**
	 * 获取当前月份
	 *
	 * @return
	 */
	public static int getMonthNum() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int monthNumber = calendar.get(Calendar.MONTH) + 1;
		return monthNumber;
	}

	/**
	 * 判断时间是否在时间段内
	 *
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean belongCalendar(Date nowTime, Date _beginTime,
			Date _endTime) throws ParseException {

		Date beginTime = null;
		Date endTime = null;

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		beginTime = df.parse(df.format(_beginTime));
		endTime = df.parse(df.format(_endTime));

		nowTime = df.parse(df.format(nowTime));

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
}
