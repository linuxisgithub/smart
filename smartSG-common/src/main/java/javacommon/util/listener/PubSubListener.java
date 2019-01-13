package javacommon.util.listener;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import javax.inject.Inject;
import java.io.Serializable;

public class PubSubListener implements MessageListener {

	@Inject
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
/*	@Inject
	private SysSensitiveManager sysSensitiveManager;*/

	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] body = message.getBody();// 请使用valueSerializer
		byte[] channel = message.getChannel();
		String msg = (String) redisTemplate.getValueSerializer().deserialize(body);  
        String topic = (String) redisTemplate.getStringSerializer().deserialize(channel);  
		System.out.println("监听" + topic + ",我收到消息：" + msg);
		JSONObject data = JSONObject.parseObject(msg);
		String type = data.getString("type");
		if("update_sensitive".equals(type)) {
			try {
				/*sysSensitiveManager.downSensitiveToFile();*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
