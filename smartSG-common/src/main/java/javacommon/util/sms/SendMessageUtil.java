package javacommon.util.sms;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 新梦网短信发送工具类
 * 
 * @author lzj
 * 
 * @2017年11月1日
 */
public class SendMessageUtil {

	
	private static Logger log = LoggerFactory.getLogger(SendMessageUtil.class);

	// 日期格式定义
	private static SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");

	public static void main(String[] args) {
		singleSend("13650832491","收到了吗？","M0003");
	}
	
	/**
	 * 发送单条短信
	 * 
	 * @param mobile手机号
	 * 
	 * @param content短信内容
	 * 
	 * @param svrType
	 *            业务类型，方便EMP后台系统查看（标明该短信主要业务作用）。
	 * @return 状态码，注意：这里的提交成功只是代表请求成功到达运营商，不代表用户是确实收到短信了，短信查收结果需要登录EMP后台查看。
	 */
	public static int singleSend(String mobile, String content, String svrType) {
		if (StringUtils.isBlank(mobile) || mobile.trim().length() != 11) {
			// 手机号为空 ，空字符串不发送短信
			log.info("单发短信号码为空，终止发送...");
			return 1;
		}

		// 清除所有IP (此处为清除IP示例代码，如果需要修改IP，请先清除IP，再设置IP)
		ConfigManager.removeAllIpInfo();

		// 备IP1 选填
		String ipAddress1 = null;
		// 备IP2 选填
		String ipAddress2 = null;
		// 备IP3 选填
		String ipAddress3 = null;
		
		// 用户密码
		 String pwd = "159369";
		// 主IP信息，请前往您的控制台获取请求域名(IP)或联系梦网客服进行获取
		 String masterIpAddress = "TSC3.800CT.COM:8086";

		boolean isEncryptPwd = false;
		
		// 设置IP
		ConfigManager.setIpInfo(masterIpAddress, ipAddress1, ipAddress2,
				ipAddress3);
		// 密码是否加密 true：密码加密;false：密码不加密
		ConfigManager.IS_ENCRYPT_PWD = isEncryptPwd;

		// 返回值
		int result = -310099;

		try {

			 isEncryptPwd = ConfigManager.IS_ENCRYPT_PWD;

			// 参数类
			Message message = new Message();
			// 实例化短信处理对象
			CHttpPost cHttpPost = new CHttpPost();

			//TODO 用户账号
			// 设置账号 将 userid转成大写,以防大小写不一致
			message.setUserid("JS6031".toUpperCase());

			// 判断密码是否加密。
			// 密码加密，则对密码进行加密
			if (isEncryptPwd) {
				// 设置时间戳
				String timestamp = sdf.format(Calendar.getInstance().getTime());
				message.setTimestamp(timestamp);

				// 对密码进行加密
				String encryptPwd = cHttpPost.encryptPwd(message.getUserid(),
						pwd, message.getTimestamp());
				// 设置加密后的密码
				message.setPwd(encryptPwd);

			} else {
				// 设置密码
				message.setPwd(pwd);
			}

			// 设置手机号码 此处只能设置一个手机号码
			message.setMobile(mobile);
			// 设置内容
			message.setContent(content);
			// 设置扩展号
			message.setExno("");
			// 用户自定义流水编号
			message.setCustid("");
			// 自定义扩展数据
			message.setExdata("");
			// 业务类型
			message.setSvrtype(svrType);

			// 返回的平台流水编号等信息
			StringBuffer msgId = new StringBuffer();

			log.info("发送短信message对象-->"+message.toString());
			// 发送短信
			result = cHttpPost.singleSend(message, msgId);
			// result为0:成功;非0:失败
			if (result == 0) {
				log.info("单条发送提交成功！");
				log.info(msgId.toString());
			} else {
				log.warn("单条发送提交失败,错误码：" + result);
			}
		} catch (Exception e) {
			log.error("单条发送提交失败,进入exception catch...");
		}
		return result;
	}

	/**
	 * 相同内容群发
	 * 
	 * @param mobiles
	 *            多个手机号，逗号隔开
	 * @param content
	 * @param svrType
	 * @return
	 */
	public static int batchSend(String mobiles, String content, String svrType) {
		int result = -310099;
		if (StringUtils.isBlank(mobiles)) {
			// 手机号为空 ，空字符串不发送短信
			return 1;
		}
		try {

			// 清除所有IP (此处为清除IP示例代码，如果需要修改IP，请先清除IP，再设置IP)
			ConfigManager.removeAllIpInfo();

			// 备IP1 选填
			String ipAddress1 = null;
			// 备IP2 选填
			String ipAddress2 = null;
			// 备IP3 选填
			String ipAddress3 = null;
			
			// 用户密码
			 String pwd = "159369";
			// 主IP信息，请前往您的控制台获取请求域名(IP)或联系梦网客服进行获取
			 String masterIpAddress = "TSC3.800CT.COM:8086";

			boolean isEncryptPwd = false;
			
			// 设置IP
			ConfigManager.setIpInfo(masterIpAddress, ipAddress1, ipAddress2,
					ipAddress3);
			// 密码是否加密 true：密码加密;false：密码不加密
			ConfigManager.IS_ENCRYPT_PWD = isEncryptPwd;

			// 参数类
			Message message = new Message();

			// 实例化短信处理对象
			CHttpPost cHttpPost = new CHttpPost();

			//TODO 账号
			// 设置账号 将 userid转成大写,以防大小写不一致
			message.setUserid("JS6031".toUpperCase());

			// 判断密码是否加密。
			// 密码加密，则对密码进行加密
			if (isEncryptPwd) {
				// 设置时间戳
				String timestamp = sdf.format(Calendar.getInstance().getTime());
				message.setTimestamp(timestamp);

				// 对密码进行加密
				String encryptPwd = cHttpPost.encryptPwd(message.getUserid(),
						pwd, message.getTimestamp());
				// 设置加密后的密码
				message.setPwd(encryptPwd);

			} else {
				// 设置密码
				message.setPwd(pwd);
			}

			// 设置手机号码
			message.setMobile(mobiles);
			// 设置内容
			message.setContent(content);
			// 设置扩展号
			message.setExno("");
			// 用户自定义流水编号
			message.setCustid("");
			// 自定义扩展数据
			message.setExdata("");
			// 业务类型
			message.setSvrtype(svrType);

			// 返回的平台流水编号等信息
			StringBuffer msgId = new StringBuffer();

			// 发送短信
			result = cHttpPost.batchSend(message, msgId);
			// result为0:成功;非0:失败
			if (result == 0) {
				log.info("群发短信提交成功！");

				log.info(msgId.toString());
			} else {
				log.warn("群发短信提交失败,错误码：" + result);
			}

		} catch (Exception e) {
			log.error("群发送提交失败,进入exception catch...");
		}
		return result;
	}


}
