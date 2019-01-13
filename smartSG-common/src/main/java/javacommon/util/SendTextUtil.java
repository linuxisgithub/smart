package javacommon.util;

import com.bcloud.msg.http.HttpSender;
import javacommon.util.encrypt.AESSecurity;

import java.util.Properties;

/**
 * @author Ether_Leung<br>
 *         短信发送
 */
public class SendTextUtil {
    /**
     * 短信接口地址
     */
    private static String url;
    /**
     * 短信客户账户
     */
    private static String account;
    /**
     * 短信账户密码
     */
    private static String pwd;
    /**
     * 是否需要回报状态
     */
    private static boolean needstatus;
    /**
     * <font color="red">暂未定义</font><br>
     * 可选参数。用户订购的产品id，不填写（针对老用户）系统采用用户的默认产品，用户订购多个产品时必填，否则会发生计费错误。
     */
    private static String product;
    /**
     * <font color="red">暂未定义</font><br>
     * 可选参数，扩展码，用户定义扩展码，3位
     */
    private static String extno;

    private SendTextUtil() {
    }

    private static void init() {
        Properties properties = SpringContextUtil.getBean("propertySetting");
        url = properties.getProperty("text_url");
        account = properties.getProperty("text_account");
        account = AESSecurity.doSecurity(account, AESSecurity.DECRYPT_MODE);
        pwd = properties.getProperty("text_pwd");
        pwd = AESSecurity.doSecurity(pwd, AESSecurity.DECRYPT_MODE);
        // 注意Boolean.parseBoolean(String str)的用法，当且仅当str为"true"时返回boolean值true
        needstatus = Boolean.parseBoolean(properties.getProperty("text_needstatus"));

		/*url = AppConfig.getInstance().get("text_url");
        account = AppConfig.getInstance().get("text_account");
		pwd = AppConfig.getInstance().get("text_pwd");
		// 注意Boolean.parseBoolean(String str)的用法，当且仅当str为"true"时返回boolean值true
		needstatus = Boolean.parseBoolean(AppConfig.getInstance().get("text_needstatus"));*/
    }

    public static String send(String content, String mobiles) {
        init();
        String returnString = null;
        try {
            returnString = HttpSender.batchSend(url, account, pwd, mobiles, content, needstatus, product, extno);
            System.out.println(returnString);
        } catch (Exception e) {

        }
        return returnString;
    }


    public static void main(String[] args) {
        //	String msg = send("测试的小情歌", "15626027733");
        String content = "asdasd"
                + ";asdasdasd";
        String msg = send(content, "13650832491");
        System.out.println(msg);
    }
}
