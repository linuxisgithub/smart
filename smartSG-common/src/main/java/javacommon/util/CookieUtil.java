package javacommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {
	private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	private static String chartset = "UTF-8";
	private static String defalut_path = "/";
	private static int defalut_time = 3600 * 24;
	
	public static void setCookie(String name, String value, HttpServletResponse response) {
		try {
			name = URLEncoder.encode(name, chartset);
			value = URLEncoder.encode(value, chartset);
			Cookie cookie = new Cookie(name, value);
			cookie.setPath(defalut_path);
			cookie.setMaxAge(defalut_time);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("设置cookie失败:" + e.getMessage());
		}
	}
	
	public static void setCookie(String name, String value,
			String path, int maxAge, HttpServletResponse response) {
		try {
			name = URLEncoder.encode(name, chartset);
			value = URLEncoder.encode(value, chartset);
			Cookie cookie = new Cookie(name, value);
			cookie.setPath(path);
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("设置cookie失败:" + e.getMessage());
		}
	}
	
	public static Cookie getCookie(String name, HttpServletRequest request) {
		try {
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				name = URLEncoder.encode(name, chartset);
				for(Cookie cookie : cookies){
					if(cookie.getName().equals(name)) {
						return cookie;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取cookie失败:" + e.getMessage());
		}
		return null;
	}
	
	public static void removeCookie(String name, 
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie cookie = getCookie(name, request);
			if(cookie != null) {
				cookie.setPath(defalut_path);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		} catch (Exception e) {
			logger.error("删除cookie失败:" + e.getMessage());
		}
	}
	
	public static String getValue(String name, HttpServletRequest request) {
		
		try {
			Cookie cookie = getCookie(name, request);
			if(cookie != null) {
				String value = cookie.getValue();
				value = URLDecoder.decode(value, chartset);
				return value;
			}
		} catch (Exception e) {
			logger.error("获取cookie失败:" + e.getMessage());
		}
		return null;
	}
}
