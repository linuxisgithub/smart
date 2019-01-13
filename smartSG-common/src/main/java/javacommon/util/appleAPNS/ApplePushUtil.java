package javacommon.util.appleAPNS;

import com.system.service.SysUserManager;
import javacommon.util.SpringContextUtil;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import java.util.*;

public class ApplePushUtil {

	public static void appleAPNS(List<String> tokens, String message,
			String type, int level) throws Exception {
		System.out.println("ApplePushUtil=====" + type
				+ "=====开始推送消息====tokens-->" + tokens + "(end)");
		if (tokens == null || tokens.isEmpty())
			return;

		boolean sendCount = true;
		// 去除List里面的 null值
		List<String> temp = new LinkedList<String>();
		temp.add(null);
		tokens.removeAll(temp);

		if (tokens.isEmpty()) {
			System.out.println("token为空，apns推送终止...");
			return;
		}

		String sound = "default"; // 铃音
		String msgCertificatePassword = null;// 导出证书时设置的密码

		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();

		String certificatePath = null;

		Properties properties = SpringContextUtil.getBean("propertySetting");
		String production = properties.getProperty("production");
		boolean isproduction = Boolean.parseBoolean(production);

		switch (level) {
		case 1:
			// 办公OA
			if (isproduction) {
				certificatePath = path + "bg_aps_distribution.p12";
				msgCertificatePassword = "12345678";
				System.out.println("path==" + certificatePath);
			} else {
				certificatePath = path + "bg_aps_development.p12";
				msgCertificatePassword = "12345678";
				System.out.println("path==" + certificatePath);
			}
			break;
		case 2:
			// 销售管理
			if (isproduction) {
				certificatePath = path + "xs_aps_distributioin.p12";
				msgCertificatePassword = "12345678";
				System.out.println("path==" + certificatePath);
			} else {
				certificatePath = path + "xs_aps_development.p12";
				msgCertificatePassword = "12345678";
				System.out.println("path==" + certificatePath);
			}
			break;
		case 3:
			// 业绩管理
			if (isproduction) {
				certificatePath = path + "dev.p12";
				msgCertificatePassword = "123456";
				System.out.println("path==" + certificatePath);
			} else {
				certificatePath = path + "dis.p12";
				msgCertificatePassword = "123456";
				System.out.println("path==" + certificatePath);
			}
			break;
		default:
			break;
		}

		// 消息的对象
		PushNotificationPayload payload = new PushNotificationPayload();
		payload.addAlert(message); // 消息内容
		payload.addSound(sound);
		// payload.setContentAvailable(false);

		PushNotificationManager pushManager = new PushNotificationManager();
		// true：正式 false：测试
		pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
				certificatePath, msgCertificatePassword, isproduction));

		List<PushedNotification> notifications = new ArrayList<PushedNotification>();
		// 开始推送消息
		if (sendCount) {
			SysUserManager sysUserManager = SpringContextUtil
					.getBean(SysUserManager.class);
			List<Map<String, Object>> list = sysUserManager
					.findBadgeByDeviceToken(tokens);
			for (Map<String, Object> each : list) {
				String e_diriveToken = (String) each.get("diriveToken");
				int e_badge = 0;
				if (null != each.get("badge")) {
					e_badge = (int) each.get("badge");
				}
				++e_badge;

				each.put("badge", e_badge);
				payload.addBadge(e_badge);// 其值为数字，表示当通知到达设备时，应用的角标变为多少

				Device device = new BasicDevice();
				device.setToken(e_diriveToken);
				PushedNotification notification = pushManager.sendNotification(
						device, payload, true);
				System.out.println("返回状态==" + notification);
				notifications.add(notification);
			}
			// 更新数据库未读数量
			sysUserManager.updateBadgeBatch(list);
		}

		List<PushedNotification> failedNotification = PushedNotification
				.findFailedNotifications(notifications);
		List<PushedNotification> successfulNotification = PushedNotification
				.findSuccessfulNotifications(notifications);
		int failed = failedNotification.size();
		int successful = successfulNotification.size();
		System.out.println("apns==========成功数：" + successful);
		System.out.println("apns==========失败数：" + failed);
		pushManager.stopConnection();
		System.out.println("apns==========消息推送完毕");
	}
}
