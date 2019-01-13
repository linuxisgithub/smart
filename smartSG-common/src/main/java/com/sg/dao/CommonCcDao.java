package com.sg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import javacommon.base.BaseMybatis3Dao;
import javacommon.util.OaBtype;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Repository
public class CommonCcDao extends BaseMybatis3Dao<Map<String, Object>> {
	@Inject
	private RabbitTemplate erpTemplate;

	@Inject
	private Properties propertySetting;

	/**
	 * @description 根据业务ID查询抄送人
	 * @author david
	 * @since 2016年4月18日
	 */
	public List<Map<String, Object>> findListByBid(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("findCcList"),
				param);
	}

	/**
	 * @description 根据业务ID查询抄送人
	 * @author david
	 * @since 2016年4月18日
	 */
	public List<Map<String, Object>> findListByBid(Long bid, Integer btype) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("btype", btype);
		param.put("bid", bid);
		return sqlSessionTemplate.selectList(generateStatement("findCcList"),
				param);
	}

	public void saveCC(Map<String, Object> map, JSONArray obj) {
		super.save(map);
		try {
			if (obj != null && obj.size() > 0) {
				Integer btype = (Integer) map.get("type");
				String pushCC = "";
				for (int i = 0; i < obj.size(); i++) {
					Object object = obj.get(i);
					JSONObject jsonObject = (JSONObject) object;
					pushCC += jsonObject.getString("imAccount");
					if (i < obj.size() - 1) {
						pushCC += ",";
					}
				}
				JSONObject result = new JSONObject();
				result.put("btype", btype);
				OaBtype oaBtype = OaBtype.get(btype);
				result.put("msg", "您有[" + oaBtype.getValue() + "]的抄送！");
				result.put("pid", 103);
				result.put("menuId", 10303);
				result.put("menuCode", "gzbg_csgw");

				JSONObject pushMsg = new JSONObject();
				JSONObject data = new JSONObject();
				pushMsg.put("to", pushCC);
//				data.put(OaBtype.PUSH_CC.getCode(), result);
				pushMsg.put("datas", data);
				System.out.println("推出的消息：" + pushMsg);
				String sendorNo = propertySetting.getProperty("sendorNo");
				if (sendorNo.equalsIgnoreCase("yes")) {
					erpTemplate.convertAndSend("erpMessageQueue",
							pushMsg.toJSONString());
				}

			}
		} catch (Exception e) {
			System.out.println("推出的消息异常！");
			e.printStackTrace();
		}
	}

	public void setStatus(Map<String, Object> param) {
		sqlSessionTemplate.update(generateStatement("setStatus"), param);
	}

}
