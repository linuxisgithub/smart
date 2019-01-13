package javacommon.util.chuanglan.interDemo;

import javacommon.util.chuanglan.interDemo.util.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BalanceQuery {

	private static final Logger logger = LoggerFactory.getLogger(BalanceQuery.class);
	
	public static void main(String[] args){
		//请求地址
		String url="http://intapi.253.com/balance/json";
		
		//API账号，50位以内。必填
		String account="I2170326";	
		
		//API账号对应密钥，联系客服获取。必填
		String password="******";
		
		
		//组装请求参数
		JSONObject map=new JSONObject();
		map.put("account", account);
		map.put("password", password);

		String params=map.toString();

		logger.info("请求参数为:" + params);
		try {
			String result=HttpUtil.post(url, params);
			
			logger.info("返回参数为:" + result);
			
			JSONObject jsonObject =  JSON.parseObject(result);
			String code = jsonObject.get("code").toString();
			String balance = jsonObject.get("balance").toString();
			String error = jsonObject.get("error").toString();
			
			logger.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + balance );
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("请求异常：" + e);
		}
	}
}
