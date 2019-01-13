package javacommon.util.chuanglan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javacommon.util.SpringContextUtil;
import javacommon.util.chuanglan.interDemo.util.HttpUtil;
import javacommon.util.sms.CHttpPost;
import javacommon.util.sms.ConfigManager;
import javacommon.util.sms.Message;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * 创蓝国际短信短信发送工具类
 * 
 * @author jqz
 * 
 * @2018年12月5日
 */
public class SendMessageUtil {

	
	private static Logger log = LoggerFactory.getLogger(SendMessageUtil.class);

	// 日期格式定义
	private static SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");

	public static void main(String[] args) {
		String result = send("{'account':'I0325656','mobile':'8616626726647','msg':'【smartSG】您的验证码是：2530（测试）','password':'jsZRVX4TcSeeeb','senderId':''}");
		System.out.println(result);
	}
	
	/**创蓝国际短信
	 * 发送短信
	 */
	public static String send(String params) {
		/*if (StringUtils.isBlank(mobile)) {
			// 手机号为空 ，空字符串不发送短信
			log.info("短信号码为空，终止发送...");
			return "短信号码为空，终止发送...";
		}*/
		log.info("请求参数为:" + params);
		Properties properties = SpringContextUtil.getBean("propertySetting");
		String url = properties.getProperty("text_url");
		String result = "";
		try {
			result= HttpUtil.post(url, params);

			log.info("返回参数为:" + result);
			JSONObject jsonObject =  JSON.parseObject(result);
			String code = jsonObject.get("code").toString();
			String msgid = jsonObject.get("msgid").toString();
			String error = jsonObject.get("error").toString();
			log.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("请求异常：" + e);
		}
		return result;
	}

}
