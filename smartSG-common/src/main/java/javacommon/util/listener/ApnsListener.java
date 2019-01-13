package javacommon.util.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javacommon.util.appleAPNS.ApplePushUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * 异步苹果推送队列监听处理
 * 
 * @author Administrator
 * 
 * @2017年12月7日
 */
public class ApnsListener implements
		org.springframework.amqp.core.MessageListener {

	private static Logger log = LoggerFactory.getLogger(ApnsListener.class);
	
	@Override
	public void onMessage(Message msg) {
		try {
			String str = new String(msg.getBody(), "UTF-8");
			JSONObject json = JSONObject.parseObject(str);
			
			JSONArray jsonArray = json.getJSONArray("tokens");
			if(null != jsonArray){
				List<String> tokens = new LinkedList<>();
				for(int i = 0; i < jsonArray.size(); i++){
					tokens.add(jsonArray.getString(i));
				}
				String pushType = json.getString("pushType");
				int level = json.getIntValue("level");
				String content = json.getString("content");
				log.info("apns接收到的队列--->" + json);
				ApplePushUtil.appleAPNS(tokens,
						content, pushType,level);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
