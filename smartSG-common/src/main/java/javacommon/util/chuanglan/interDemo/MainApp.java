package javacommon.util.chuanglan.interDemo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javacommon.util.DateUtil;
import javacommon.util.chuanglan.interDemo.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tianyh 
 * @Description:国际短信发送接口demo
 */
public class MainApp {
	
	private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
	
	public static void main(String[] args){

			send("8618927408729");
			send("8615220159150");
			send("8615673159908");
			send("8613974949107");
			send("8613026600906");
			send("8613924162908");
			send("8613929113912");
			send("8615507598192");
			send("6587686956");
			send("6597280526");
			send("6581638217");
			send("6596745035");
			send("14163886795");
			send("393440462771");


		/*//请求地址
		String url="http://intapi.253.com/send/json";
		
		//API账号，50位以内。必填
		String account="I0325656";
		
		//API账号对应密钥，联系客服获取。必填
		String password="jsZRVX4TcSeeeb";
		
		//短信内容。长度不能超过536个字符。必填
		String msg="smartSG国内短信测试";
		
		//手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填
		String mobile="8616626726647";
		
		//用户收到短信之后显示的发件人，国内不支持自定义，国外支持，但是需要提前和运营商沟通注册，具体请与TIG对接人员确定。选填
		String senderId = "";
		
		//组装请求参数
		JSONObject map=new JSONObject();
		map.put("account", account);
		map.put("password", password);
		map.put("msg", msg);
		map.put("mobile", mobile);
		map.put("senderId", senderId);

		String params=map.toString();
		
		logger.info("请求参数为:" + params);
		try {
			String result= HttpUtil.post(url, params);
			
			logger.info("返回参数为:" + result);
			JSONObject jsonObject =  JSON.parseObject(result);
			String code = jsonObject.get("code").toString();
			String msgid = jsonObject.get("msgid").toString();
			String error = jsonObject.get("error").toString();
			logger.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("请求异常：" + e);
		}*/
	}

	public static void send(String mobile){
		//请求地址
		String url="http://intapi.253.com/send/json";
		//API账号，50位以内。必填
		String account="I0325656";
		//API账号对应密钥，联系客服获取。必填
		String password="jsZRVX4TcSeeeb";
		//短信内容。长度不能超过536个字符。必填
		String msg="SmartSG系统测试：蒋维伟向您问好，请将收到该短信的时间微信回复给蒋维伟，具体到秒（若可）";
		//手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填
		/*String mobile="8616626726647";*/
		//用户收到短信之后显示的发件人，国内不支持自定义，国外支持，但是需要提前和运营商沟通注册，具体请与TIG对接人员确定。选填
		String senderId = "";
		//组装请求参数
		JSONObject map=new JSONObject();
		map.put("account", account);
		map.put("password", password);
		map.put("msg", msg);
		map.put("mobile", mobile);
		map.put("senderId", senderId);
		String params=map.toString();
		System.out.println("\r\n手机号码:" + mobile);
		System.out.println("请求参数为:" + params);
		try {
			/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间*/
			//System.out.println("*系统发送时间:" + DateUtil.getDate_TImeStr());
			System.out.println("*系统发送时间:" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
			String result= HttpUtil.post(url, params);
			System.out.println("返回参数为:" + result);
			JSONObject jsonObject =  JSON.parseObject(result);
			String code = jsonObject.get("code").toString();
			String msgid = jsonObject.get("msgid").toString();
			String error = jsonObject.get("error").toString();
			System.out.println("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("请求异常：" + e);
			logger.error("请求异常：" + e);
		}
	}
	
}
