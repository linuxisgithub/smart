package javacommon.util;

import com.alibaba.fastjson.JSONObject;
import com.system.model.SysCompany;
import com.system.model.SysUser;
import com.system.service.SysCompanyManager;
import com.system.service.SysUserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PushMessageUtil {

	private static Logger log = LoggerFactory.getLogger(PushMessageUtil.class);


	/**
	 * 发短信队列 创蓝国际短信
	 *
	 * @param mobile
	 *            //手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填
	 * @param msg
	 *            //短信内容。长度不能超过536个字符。必填
	 */
	public static final void msmQueue(String mobile, String msg) {
		try {
			Properties properties = SpringContextUtil.getBean("propertySetting");
			// 是否开启推送
			String sendorNo = properties.getProperty("sendorNo");
			System.out.println("-------->" + sendorNo);
			if (sendorNo.equals("yes")) {
				RabbitTemplate rabbitTemplate = SpringContextUtil.getBean("smartSGTemplate");
				String account = properties.getProperty("text_account");
				String password = properties.getProperty("text_pwd");
				JSONObject pushMsg = new JSONObject();
				pushMsg.put("account", account);
				pushMsg.put("password", password);
				pushMsg.put("msg", msg);
				pushMsg.put("mobile", mobile);
				pushMsg.put("senderId", "");//用户收到短信之后显示的发件人，国内不支持自定义，国外支持，但是需要提前和运营商沟通注册，具体请与TIG对接人员确定。选填
				log.info("短信发出队列消息：" + pushMsg);
				rabbitTemplate.convertAndSend("smartSG_msmQueue", pushMsg.toJSONString());
			}
		} catch (Exception e) {
			log.error("短信推出的消息：" + e.getMessage(), e);
		}

	}


	/**
	 * 苹果推送队列
	 *
	 * @param tokens
	 * @param content
	 * @param pushType
	 */
	public static final void apnsQueue(List<String> tokens, String content, String pushType, Integer level) {
		try {
			RabbitTemplate rabbitTemplate = SpringContextUtil.getBean("erpTemplate");
			JSONObject pushMsg = new JSONObject();
			pushMsg.put("tokens", tokens);
			pushMsg.put("content", content);
			pushMsg.put("level", level);
			pushMsg.put("pushType", pushType);
			log.info("apns发出队列消息：" + pushMsg);
			rabbitTemplate.convertAndSend("oa_apnsQueue", pushMsg.toJSONString());
		} catch (Exception e) {
			log.error("apns推出的消息：" + e.getMessage(), e);
		}

	}

	/**
	 * 发短信队列
	 *
	 * @param mobiles
	 *            手机号，多个,号隔开
	 * @param svrType
	 *            OaBtype.MESSAGE_SER_TYPE_002 .getCode()
	 * @param sendType
	 *            true群发，false单发
	 * @param content
	 *            内容
	 */
	public static final void msmQueue(String mobiles, String svrType,
									  Boolean sendType, String content) {
		try {
			Properties properties = SpringContextUtil
					.getBean("propertySetting");
			RabbitTemplate rabbitTemplate = SpringContextUtil
					.getBean("smartSGTemplate");

			// 是否开启推送
			String sendorNo = properties.getProperty("sendorNo");
			if (sendorNo.equals("yes")) {
				JSONObject pushMsg = new JSONObject();
				pushMsg.put("mobiles", mobiles);
				pushMsg.put("svrType", svrType);
				pushMsg.put("sendType", sendType);
				pushMsg.put("content", content);
				log.info("短信发出队列消息：" + pushMsg);
				rabbitTemplate.convertAndSend("smartSG_msmQueue",
						pushMsg.toJSONString());
			}
		} catch (Exception e) {
			log.error("短信推出的消息：" + e.getMessage(), e);
		}

	}

	/**
	 * 推送服务
	 *
	 * @param to
	 * @param type
	 * @param msg
	 */
	public static final void pushMsg(String to, String type, Object msg) {
		try {
			Properties properties = SpringContextUtil.getBean("propertySetting");
			RabbitTemplate rabbitTemplate = SpringContextUtil.getBean("smartSGTemplate");

			// 是否开启推送
			String sendorNo = properties.getProperty("sendorNo");
			System.out.println("-------->" + sendorNo);
			if (sendorNo.equals("yes")) {
				JSONObject pushMsg = new JSONObject();
				JSONObject data = new JSONObject();
				pushMsg.put("to", to);
				data.put(type, msg);
				pushMsg.put("datas", data);
				log.info("推出的消息：" + pushMsg);
				rabbitTemplate.convertAndSend("erpMessageQueue", pushMsg.toJSONString());
			}
		} catch (Exception e) {
			log.error("推送服务：" + e.getMessage(), e);
		}

	}

	/**
	 * 推送给下一个批审人
	 *
	 * @param btype
	 * @param nextUserId
	 */
	public static final void pushApproveMessage(Integer btype, Long nextUserId) {
		try {
			SysUserManager sysUserManager = SpringContextUtil.getBean(SysUserManager.class);
			SysCompanyManager companyManager = SpringContextUtil.getBean(SysCompanyManager.class);
			SysUser user = sysUserManager.get(nextUserId);
			if (null == user) {
				return;
			}
			SysCompany company = companyManager.get(user.getCompanyId());

			JSONObject result = PushMessageUtil.getApproveMessageByBtype(btype);

			// 苹果推送队列
			OaBtype oaBtype = OaBtype.get(btype);
			List<String> tokens = new LinkedList<>();
			tokens.add(user.getDiriveToken());
			PushMessageUtil.apnsQueue(tokens, result.getString("apple"), "推送给下一个批审人" + oaBtype.getValue(),
					company.getLevel());

			// IM内部推送
			result.remove("apple");
			PushMessageUtil.pushMsg(user.getImAccount(), OaBtype.PUSH_APPROVE.getCode(), result);

			Integer busMess = user.getBusMess();
			if (1 == busMess) {
				// 发送短信
				
				  /*String mobiles = user.getTelephone(); String wake_up_url =
				  PlatformParam.getWakeUpUrl(company.getLevel()); String
				  content = PlatformParam.APPROVAL_CONTENT + wake_up_url;
				  PushMessageUtil.msmQueue(mobiles,
				  OaBtype.MESSAGE_SER_TYPE_002.getCode(), false, content );*/
				 
			}

		} catch (Exception e) {
			log.error("推送下一个批审人消息异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 批审完成发出推送
	 *
	 * @param btype
	 * @param createUserId
	 */
	public static final void pushFinishApproveMessage(Integer btype, Long createUserId) {
		try {
			SysUserManager sysUserManager = SpringContextUtil.getBean(SysUserManager.class);
			SysUser user = sysUserManager.get(createUserId);
			if (null == user) {
				return;
			}

			SysCompanyManager companyManager = SpringContextUtil.getBean(SysCompanyManager.class);

			SysCompany company = companyManager.get(user.getCompanyId());

			JSONObject result = PushMessageUtil.getFinishApproveMessageByBtype(btype);
			log.info("推送信息222------》" + result);

			// 苹果推送队列
			OaBtype oaBtype = OaBtype.get(btype);
			List<String> tokens = new LinkedList<>();
			tokens.add(user.getDiriveToken());
			PushMessageUtil.apnsQueue(tokens, result.getString("msg"), "批审完成推送" + oaBtype.getValue(),
					company.getLevel());

			// IM内部推送
			PushMessageUtil.pushMsg(user.getImAccount(), OaBtype.PUSH_FINISH.getCode(), result);

			Integer busMess = user.getBusMess();
			if (1 == busMess) {
				// 发送短信
				/*
				 * String mobiles = user.getTelephone(); String wake_up_url =
				 * PlatformParam.getWakeUpUrl(company.getLevel()); String
				 * content = PlatformParam.APPROVAL_CONTENT + wake_up_url;
				 * PushMessageUtil.msmQueue(mobiles,
				 * OaBtype.MESSAGE_SER_TYPE_002.getCode(), false, content);
				 */
			}
		} catch (Exception e) {
			log.error("推送批审完成消息异常：" + e.getMessage(), e);
		}
	}

	/*
	 * public static JSONObject getFinishApproveMessageByBtype(Integer btype) {
	 * JSONObject result = new JSONObject();
	 * 
	 * result.put("btype", btype); OaBtype oaBtype = OaBtype.get(btype); if
	 * (oaBtype != null) { result.put("msg", "您的【" + oaBtype.getValue() +
	 * "】已经批审完成"); } result.put("isDone", 1); return result; }
	 */

	private static JSONObject getFinishApproveMessageByBtype(Integer btype) {
		log.info("1111推送消息------》：审批完成");
		log.info("推送消息------》btype:" + btype);
		JSONObject result = new JSONObject();
		result.put("btype", btype);
		OaBtype oaBtype = OaBtype.get(btype);
		if (oaBtype != null) {
			result.put("msg", "您的【" + oaBtype.getValue() + "】已经批审完成！");
		}
		result.put("isDone", 1);
		
		return result;
	}

	/*
	 * public static JSONObject getApproveMessageByBtype(int btype) { JSONObject
	 * result = new JSONObject(); result.put("btype", btype); OaBtype oaBtype =
	 * OaBtype.get(btype); if (oaBtype != null) { result.put("msg", "您有【" +
	 * oaBtype.getValue() + "】待您批审"); result.put("apple", "有新的【" +
	 * oaBtype.getValue() + "】待您批审"); } result.put("isDone", 0); return result;
	 * }
	 */

	private static JSONObject getApproveMessageByBtype(int btype) {
		JSONObject result = new JSONObject();
		result.put("btype", btype);
		OaBtype oaBtype = OaBtype.get(btype);
		if (oaBtype != null) {
			result.put("msg", "您有【" + oaBtype.getValue() + "】待批审！");
		}
		result.put("isDone", 0);
		
		return result;
	}

	public static void apnsQueue(List<String> tokens, String content,String pushType) {
		try {
			RabbitTemplate rabbitTemplate = SpringContextUtil
					.getBean("erpTemplate");
			JSONObject pushMsg = new JSONObject();
			pushMsg.put("tokens", tokens);
			pushMsg.put("content", content);
			pushMsg.put("pushType", pushType);
			log.info("apns发出队列消息：" + pushMsg);
			rabbitTemplate.convertAndSend("sale_apnsQueue", pushMsg.toJSONString());
		} catch (Exception e) {
			log.error("apns推出的消息：" + e.getMessage(), e);
		}
	}


	public static JSONObject getSendMessageByBtype(int btype) {
		JSONObject result = new JSONObject();
		result.put("btype", btype);
		OaBtype oaBtype = OaBtype.get(btype);
		if (oaBtype != null) {
			result.put("msg", "您有【" + oaBtype.getValue() + "】待查看！");
		}
		result.put("isDone", 0);
		if(btype == OaBtype.TSJY_CODE.getBtype()){//收件箱
			result.put("ppid", 105);
			result.put("pid", 10501);
			result.put("menuId", 1050101);
			result.put("menuCode", "tsjy_sjx_sjx");
		}
		return result;
	}
}
