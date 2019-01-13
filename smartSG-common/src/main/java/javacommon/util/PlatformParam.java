package javacommon.util;

import com.system.model.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统一些参数
 * @author duwufeng
 * @date 2017年7月17日 上午11:19:04
 */
public class PlatformParam {

	/**--客户端保持登录cookie的key--**/
	public static final String cookie_l_token = "l_token";
	/**--session保存登录用户key--**/
	public static final String USER_IN_SESSION = "USER_IN_SESSION";
	/**--redis保存菜单的key--**/
	public static final String REDIS_MENU_KEY = "SMARTSG_REDIS_MENU_KEY";
	/**--redis保存用户key前缀--**/
	public static final String REDIS_LOGIN_KEY = "smartSG_bs:";

	public static final String COM_NOTICE_APPLE= "您有一条新的公司通知";
	
	public static final String BUSINESS_APPLE= "您有一条新的业务批阅";
	
	public static final String ADMININS_APPLE= "您有一条新的行政批阅";
	
	public static final String SHARE_APPLE= "您有一条新的日志分享";

	public static final String DEPT_NOTICE_APPLE= "您有一条新的部门通知";
	
	public static final String VEDIO_CONTENT_APPLE = "您有一项语音会议需要您参与";
	
	public static final String END_VEDIO_CONTENT_APPLE = "语音会议已经结束";

	public static final String APPROVAL_CONTENT= "呼叫！请点击查看";

	public static final String wake_up_url_oa = "yjsaas.cn/oaCrmWp";
	public static final String wake_up_url_oa_plus = "yjsaas.cn/oaPlusWp";
	public static final String wake_up_url_oa_crm = "ty.yjsaas.cn/wpCrm";

	public static String getWakeUpUrl(int comLevel) {
		String wake_up_url = "";
		if(comLevel == 1) {
			wake_up_url = PlatformParam.wake_up_url_oa;
		} else if(comLevel == 2) {
			wake_up_url = PlatformParam.wake_up_url_oa_plus;
		} 
		return wake_up_url;
	}
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("Proxy-Client-IP");
    	}
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("WL-Proxy-Client-IP");
    	}
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("HTTP_CLIENT_IP");
    	}
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    	}
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    		ip = request.getRemoteAddr();
    	}
    	return ip;
	}

	/**
     * 判断是否为Ajax请求
     * @param request
     * @return 是true, 否false
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
        	return true;
        } else {
        	return false;
        }
    }
	public static String getWakeUpUrl(LoginUser loginUser) {
		Integer version = 1;
		if(loginUser != null && loginUser.getCompany() != null) {
			version = loginUser.getCompany().getVersion();
		}
		return getWakeUpUrl(version);
	}
}
