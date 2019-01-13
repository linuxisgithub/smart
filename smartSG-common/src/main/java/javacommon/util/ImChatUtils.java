package javacommon.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * 聊天工具类
 * @author Administrator
 *
 */
public class ImChatUtils {

	/**
	 * 聊天im不在线时，发送短信
	 * @param telephone
	 * @param imAccount
	 */
	public static void sendChatText(Object telephone, Object imAccount, int level) {
		if(!StringUtils.isEmpty(telephone) && !StringUtils.isEmpty(imAccount)) {
			sendChatText(telephone.toString(), imAccount.toString(), level);
		} else {
			System.out.println("聊天发送短息失败--->telephone：" + telephone + ", imAccount-->" + imAccount);
		}
	}
	/**
	 * 聊天im不在线时，发送短信
	 * @param telephone
	 * @param imAccount
	 */
	public static void sendChatText(String telephone, String imAccount, int level) {
		System.out.println("开始发送短信--->telephone：" + telephone + ", imAccount-->" + imAccount);
		try {
			if(!StringUtils.isEmpty(telephone) && !StringUtils.isEmpty(imAccount)) {
				Properties propertySetting = SpringContextUtil.getBean("propertySetting");
				String im_http_url = propertySetting.getProperty("im_http_url");
				String url = im_http_url + "?sendSms=true&imAccount=" + imAccount;
				String getResult = HttpClientUtil.get(url);
				JSONObject jsonResult = JSONObject.parseObject(getResult);
				System.out.println("GET 请求：" + url);
				System.out.println("请求结果：" + jsonResult);
				if(jsonResult.getInteger("status") != null && jsonResult.getInteger("status") == 400) {
					String wake_up_url = PlatformParam.getWakeUpUrl(level);
					String content = PlatformParam.APPROVAL_CONTENT + wake_up_url;
					PushMessageUtil.msmQueue(telephone.toString(),
							OaBtype.MESSAGE_SER_TYPE_007.getCode(), false, content);
				}
			} else {
				System.out.println("聊天发送短息失败--->telephone：" + telephone + ", imAccount-->" + imAccount);
			}
			
		} catch (Exception e) {
			System.out.println("聊天发送短息失败--->telephone：" + telephone + ", imAccount-->" + imAccount);
		}
	}
	
	/**
	 * 判断im是否在线
	 * @param imAccount
	 * @return
	 */
	public static boolean isImOnline(String imAccount) {
		if(!StringUtils.isEmpty(imAccount)) {
			Properties propertySetting = SpringContextUtil.getBean("propertySetting");
			String im_http_url = propertySetting.getProperty("im_http_url");
			String url = im_http_url + "?sendSms=true&imAccount=" + imAccount;
			String getResult;
			try {
				getResult = HttpClientUtil.get(url);
				JSONObject jsonResult = JSONObject.parseObject(getResult);
				System.out.println("GET 请求：" + url);
				System.out.println("请求结果：" + jsonResult);
				if(jsonResult.getInteger("status") != null && jsonResult.getInteger("status") == 200) {
					return true;
				}
			} catch (Exception e) {
				System.out.println("判断im是否在线异常---> imAccount：" + imAccount);
			}
		}
		return false;
	}
}
