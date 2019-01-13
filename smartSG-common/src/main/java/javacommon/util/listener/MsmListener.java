package javacommon.util.listener;

import com.alibaba.fastjson.JSONObject;
import javacommon.util.chuanglan.SendMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

/**
 * 异步短信队列监听处理
 * 
 * @author Administrator
 * 
 * @2017年12月7日
 */
public class MsmListener implements
		org.springframework.amqp.core.MessageListener {
	
	private static Logger log = LoggerFactory.getLogger(MsmListener.class);

	@Override
	public void onMessage(Message msg) {
		try {
			String str = new String(msg.getBody(), "UTF-8");
			JSONObject json = JSONObject.parseObject(str);
			log.warn("短信接收到的队列--->" + json.toString());
			SendMessageUtil.send(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
