package javacommon.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 打印工具类
 * @author duwufeng
 * @date 2017年2月24日 下午3:44:30
 */
public class PrintUils {

	public static <T> void print(HttpServletResponse response, List<Map<String, Object>> rowsData, 
			Map<String,Object> params) throws IOException {
		try {
			String xmlContent = resultSetToXml(rowsData);
            String xmlParams = paramsXml(params);
	            
            StringBuffer XmlText = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
            XmlText.append("<report>\r\n");
            XmlText.append(xmlContent);
            XmlText.append(xmlParams);
            XmlText.append("\r\n</report>");
            
            PrintWriter pw = response.getWriter();			
            pw.print(XmlText.toString());
            pw.close();
		} catch (Exception e) {
			e.printStackTrace();
            PrintWriter pw = response.getWriter();
            pw.print(e.toString());
            pw.close();
		}
	}

	private static String paramsXml(Map<String, Object> params) {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n<_grparam>\r\n");
		if(params != null && !params.isEmpty()) {
			Set<String> keys = params.keySet();
			for (String key : keys) {
				sb.append("\t<"+key+">"+params.get(key)+"</"+key+">\r\n");
			}
		}
		sb.append("\r\n</_grparam>");
		return sb.toString();
	}

	private static String resultSetToXml(List<Map<String, Object>> rowsData) {
		StringBuffer sb = new StringBuffer ();
		sb.append("<xml>\r\n");
		if(rowsData != null && !rowsData.isEmpty()) {
			for (Map<String, Object> rows : rowsData) {
				if(rows != null && rows.size() > 0) {
					sb.append("\t<row>\r\n");
					Set<String> keys = rows.keySet();
					for (String key : keys) {
						sb.append("\t\t<"+key+">"+rows.get(key)+"</"+key+">\r\n");
					}
					sb.append("\t</row>\r\n");
				}
				
			}
		}
		sb.append("\r\n</xml>");
		return sb.toString();
	}
	
	
	/**
	 * yyyy-MM-dd ---> yyyy 年 MM 月 dd 日
	 * @param date
	 * @return
	 */
	public static String changeDate(String date) {
		String result = date;
		SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
		if(date != null && date != "") {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateTime = dateFormat.parse(date);
				result = format.format(dateTime);
			} catch (Exception e) {
				e.printStackTrace();
				result = format.format(new Date());
			}
		} else {
			result = format.format(new Date());
		}
		return result;
	}
	public static String changeDate2(String date) {
		String result = date;
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		if(date != null && date != "") {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateTime = dateFormat.parse(date);
				result = format.format(dateTime);
			} catch (Exception e) {
				e.printStackTrace();
				result = format.format(new Date());
			}
		} else {
			result = format.format(new Date());
		}
		return result;
	}
	
	public static String getCcName(List<Map<String, Object>> ccList) {
		return getCcName(ccList, "userName");
	}
	public static String getCcName(List<Map<String, Object>> ccList, String nameKey) {
		String ccName = "无";
		if(ccList != null && ccList.size() != 0){
			StringBuilder sb = new StringBuilder();
			int size = ccList.size();
			for (int i = 0; i < size; i++) {
				Map<String, Object> item = ccList.get(i);
				String name = (String) item.get(nameKey);
				sb.append(name);
				if(i < size - 1) {
					sb.append("、");
				}
			}
			ccName = sb.toString();
		}
		return ccName;
	}
	/**
	 * 去掉小数后面的0
	 * @param object
	 * @return
	 */
	public static String trimZero(Object object) {
		if(object == null || object.toString().equals("null")) {
			return "";
		}
		String value = object.toString();
		if(value.indexOf(".") > 0) {
			while(value.endsWith("0")) {
				value = value.substring(0, value.length() - 1);
			}
			if(value.endsWith(".")) {
				value = value.substring(0, value.length() - 1);
			}
		}
		return value;
	}
	
	/**
	 * 加空格
	 * @param object
	 * @param flag
	 * @return
	 */
	public static String handlerSpace(Object object) {
		if(object == null || object.toString().equals("null")) {
			return "";
		}
		String value = object.toString();
		return " " + value;
	}
	
	/**
	 * 首行缩进
	 * @param object
	 * @return
	 */
	public static String handlerLineSpace(Object object) {
		if(object == null || object.toString().equals("null")) {
			return "";
		}
		StringBuffer sb = new StringBuffer("");
		String value = object.toString();
		String[] strings = value.split("\r\n");
		boolean first = true;
		for (String content : strings) {
			if(first == true) {
				first = false;
				sb.append("    ");
			} else {
				sb.append("\r\n    ");
			}
			content = content.trim();
			int length = content.length();
			for (int i = 0; i < length; i++) {
				char c = content.charAt(i);
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 首行缩进，段落之间空一行
	 * @param object
	 * @return
	 */
	public static String handlerLineSpace2(Object object) {
		if(object == null || object.toString().equals("null")) {
			return "";
		}
		StringBuffer sb = new StringBuffer("");
		String value = object.toString();
		String[] strings = value.split("\r\n");
		boolean first = true;
		for (String content : strings) {
			if(first == true) {
				first = false;
				sb.append("    ");
			} else {
				sb.append("\r\n\r\n    ");
			}
			content = content.trim();
			int length = content.length();
			for (int i = 0; i < length; i++) {
				char c = content.charAt(i);
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
