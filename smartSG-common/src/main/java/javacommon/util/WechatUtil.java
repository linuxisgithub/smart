package javacommon.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.UUID;
import java.util.Map.Entry;

/**
 * 微信工具类
 * @author duwufeng
 * @date 2017年3月18日 上午9:46:51
 */
public class WechatUtil {
	
	//app支付-个体富ERP
	public final static String ERP_API_KEY = "TEqytfS7GJGDkB8GE578htzxdDoVd9EO";
    public final static String ERP_APPID = "wxb78c2aa3ec2f4648";
    public final static String ERP_MCH_ID = "1452294702";
    
    //app支付-个体富CRM
    public final static String CRM_API_KEY = "9eRqT8T1vqIdEj09o4jVRUPtO8DiXBCj";
    public final static String CRM_APPID = "wx76dcb585ec1e93ba";
    public final static String CRM_MCH_ID = "1452308002";
    
    //app支付-个体富LSZ
    public final static String LSZ_API_KEY = "hoHf6Ww9wdbv3qzp5rrOTszggr90LHcz";
    public final static String LSZ_APPID = "wxd25b1e4b04d02cd8";
    public final static String LSZ_MCH_ID = "1452316702";
    
    //web扫码支付
    public final static String WEB_API_KEY = "15folzgtczx83duvrwfwj8vqnd51g8cf";
    public final static String WEB_APPID = "wx3f8cae7da95de37c";
    public final static String WEB_MCH_ID = "1319217401";
    
    public final static String REQUEST_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//    public final static String CALLBACK_URL = "https://ts0erp.chinacloudapp.cn/erp/pay/wechat/app/notify.json";
    public final static String CALLBACK_URL = "https://erp.injoy360.cn/erp/pay/wechat/app/notify.json";
    
    public final static String POST = "POST";
    public final static String GET = "GET";
    public final static String CHARSET = "UTF-8";
    
    /**
     * 微信统一下单入口
     * @return
     * @throws IOException 
     * @throws DocumentException 
     */
	public static Map<String, Object> weixinPrePay(Map<String, Object> param, HttpServletRequest request) throws IOException, DocumentException {
		String type = param.get("key_type").toString();
		Map<String, String> keys = getKeys(type);
		String appid = keys.get("appid");
		String mch_id = keys.get("mch_id");
		String apiKey = keys.get("apiKey");
		
		System.out.println("----------------------------微信统一下单-开始----------------------------");
		SortedMap<String, Object> data = getRequestData(param, request, appid, mch_id);//获取除了签名之外的请求数据
		String sign = signParam(data, apiKey);//对请求数据进行签名
		data.put("sign", sign);
		String requestXml = getRequestXml(data);//组装请求xml数据
		String responseXml = httpsRequest(REQUEST_URL, POST, requestXml);//https请求
		Map<String, Object> responseMap = parseXml(responseXml);//解析https请求返回的xml数据
		System.out.println("----------------------------微信统一下单-结束----------------------------");
		return responseMap;
	}
	
	/**
	 * 更具类型获取不同的key
	 * @param type
	 * @return
	 */
	public static Map<String, String> getKeys(Object type) {
		Map<String, String> result = new HashMap<>();
		if("1".equals(type)) {
			result.put("appid", ERP_APPID);
			result.put("mch_id", ERP_MCH_ID);
			result.put("apiKey", ERP_API_KEY);
		} else if("2".equals(type)) {
			result.put("appid", CRM_APPID);
			result.put("mch_id", CRM_MCH_ID);
			result.put("apiKey", CRM_API_KEY);
		} else if("3".equals(type)) {
			result.put("appid", LSZ_APPID);
			result.put("mch_id", LSZ_MCH_ID);
			result.put("apiKey", LSZ_API_KEY);
		} else if("4".equals(type)) {
			result.put("appid", WEB_APPID);
			result.put("mch_id", WEB_MCH_ID);
			result.put("apiKey", WEB_API_KEY);
		}
		return result;
	}
	/**
	 * 获取除了签名之外的请求数据
	 * @return
	 */
	public static SortedMap<String, Object> getRequestData(Map<String, Object> param, HttpServletRequest request, String appid, String mch_id) {
		SortedMap<String, Object> data = new TreeMap<String, Object>();
		data.put("appid", appid);//应用ID
		data.put("mch_id", mch_id);//商户号
		data.put("device_info", "WEB");//终端设备号(门店号或收银设备ID)，默认请传"WEB"
		data.put("nonce_str", getRandomString(32));//随机字符串
		data.put("body", param.get("body"));//商品描述
		data.put("attach", param.get("attach"));//附加数据：在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
		data.put("out_trade_no", param.get("out_trade_no"));//商户订单号：商户系统内部自定义的订单号
		data.put("total_fee", param.get("total_fee"));//总金额：单位为分
		data.put("spbill_create_ip", getRemoteIp(request));//终端IP：用户端实际ip
		data.put("notify_url", CALLBACK_URL);//通知地址：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
		Object trade_type = param.get("trade_type");
		data.put("trade_type", trade_type);//交易类型：APP支付=APP，扫码支付=NATIVE
		if("NATIVE".equals(trade_type)) {
			data.put("product_id",  param.get("product_id"));
		}
		return data;
	}
	
	/**
	 * 签名
	 * @param data
	 * @return
	 */
	public static String signParam(SortedMap<String, Object> data, String apiKey) {
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> entries = data.entrySet();
		for (Entry<String, Object> entry : entries) {
			String key = (String)entry.getKey();  
            Object value = entry.getValue();  
            if(null != value && !"".equals(value) && !"sign".equals(key) && !"key".equals(key)) {  
                sb.append(key + "=" + value + "&");  
            }  
		}
		sb.append("key=" + apiKey);
		System.out.println("签名数据：" + sb.toString());
		String sign = MD5Util.MD5(sb.toString()).toUpperCase();
		System.out.println("签名：" + sign);
		return sign;
	}
	
	/**
	 * 二次签名
	 * @param request
	 * @return
	 */
	public static SortedMap<String, Object> signParamAgain(String prepay_id, String appid, String mch_id, String apiKey) {
		long timestamp = System.currentTimeMillis()/1000;
		String nonceStr = getRandomString(32);
		SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
		parameterMap.put("appid", appid);
		parameterMap.put("partnerid", mch_id);
		parameterMap.put("prepayid", prepay_id);
		parameterMap.put("package", "Sign=WXPay");
		parameterMap.put("noncestr", nonceStr);
		parameterMap.put("timestamp", timestamp);
		String sign = signParam(parameterMap, apiKey);
		
		parameterMap.put("sign", sign);
		parameterMap.put("result", "SUCCESS");
		parameterMap.put("packageValue", "Sign=WXPay");
		return parameterMap;  
	}  
	
	/**
	 * 组装xml请求数据
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getRequestXml(Map<String, Object> requestMap) {
		StringBuffer requestXml = new StringBuffer();
		requestXml.append("<xml>");
		Set<Entry<String, Object>> entries = requestMap.entrySet();
		for (Entry<String, Object> entry : entries) {
			String key = entry.getKey();  
            Object value = entry.getValue();  
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {  
            	requestXml.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            }else {  
            	requestXml.append("<" + key + ">" + value + "</" + key + ">");
            }  
		}
		requestXml.append("</xml>");
		System.out.println("xml请求数据：" + requestXml.toString());
		return requestXml.toString();
	}
	
	/**
	 *  统一下单https请求(post)
	 * @param requestXml
	 * @return
	 * @throws IOException 
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String requestXml) throws IOException {
		String result = null;
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod(requestMethod);
		conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		if(requestXml != null) {
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(requestXml.getBytes(CHARSET));
			outputStream.close();
		}
		InputStream inputStream = conn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String tem = null;
		StringBuffer sb = new StringBuffer();
		while((tem = reader.readLine()) != null) {
			sb.append(tem);
		}
		reader.close();
		inputStreamReader.close();
		inputStream.close();
		conn.connect();
		result = sb.toString();
		//result = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[appid不存在]]></return_msg></xml>";
		System.out.println("统一下单https请求(post)返回数据：" + result);
		return result;
	}
	
	/**
	 * 解析返回的xml数据
	 * @param responseXml
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseXml(String responseXml) throws DocumentException {
		Map<String, Object> result= new HashMap<>();
		if(responseXml != null) {
			Document document = DocumentHelper.parseText(responseXml);
			Element root = document.getRootElement();
			List<Element> list = root.elements();
			for (Element element : list) {
				result.put(element.getName(), element.getText());
			}
		}
		System.out.println("解析返回的xml数据：" + result);
		return result;
	}
	
	/**
	 * 响应收到的支付结果通知
	 * @param return_code
	 * @param return_msg
	 * @param response
	 * @throws IOException
	 */
	public static void responseWechatNotify(String return_code, String return_msg, HttpServletResponse response) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<return_code><![CDATA[" + return_code + "]]></return_code>");
		sb.append("<return_msg><![CDATA[" + return_msg + "]]></return_msg>");
		sb.append("</xml>");
		System.out.println("响应收到的支付结果通知：" + sb.toString());
		response.getWriter().println(sb.toString());
	}
	
	/**
	 * 获取用户终端ip
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for"); 
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    		ip = request.getHeader("Proxy-Client-IP"); 
    	} 
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    		ip = request.getHeader("WL-Proxy-Client-IP"); 
    	} 
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    		ip = request.getHeader("HTTP_CLIENT_IP"); 
    	} 
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    		ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
    	} 
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
    		ip = request.getRemoteAddr(); 
    	}
    	System.out.println("终端用户ip：" + ip);
    	return ip; 
	}
	
	/**
	 * 获取本机id
	 * @return
	 */
	public static String getLocalIp() {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
					continue;
				} else {
					Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address) {
							return ip.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("获取本机ip失败！");
		}
		return "";
	}

	/**
	 * UUID生成32位字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getUUIDString() {
		String result = UUID.randomUUID().toString().replaceAll("-", "");
		return result.toUpperCase();
	}

	/**
	 * 随机数算法生成32位字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 查询微信支付订单
	 * @param orderNo 系统内部订单
	 * @param keyType 用来获取appid，mch_id等
	 * @throws Exception
	 */
	public static void checkOrder(String orderNo, String keyType) throws Exception {
		Map<String, String> key_param = getKeys(keyType);
		SortedMap<String, Object> data = new TreeMap<String, Object>();
		data.put("appid", key_param.get("appid"));//应用ID
		data.put("mch_id", key_param.get("mch_id"));//商户号
		data.put("nonce_str", getRandomString(32));//随机字符串
		data.put("out_trade_no", orderNo);
		String sign = signParam(data, key_param.get("apiKey"));
		data.put("sign", sign);
		String requestXml = getRequestXml(data);
		String result = null;
		URL url = new URL("https://api.mch.weixin.qq.com/pay/orderquery");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		if(requestXml != null) {
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(requestXml.getBytes(CHARSET));
			outputStream.close();
		}
		InputStream inputStream = conn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		String tem = null;
		StringBuffer sb = new StringBuffer();
		while((tem = reader.readLine()) != null) {
			sb.append(tem);
		}
		reader.close();
		inputStreamReader.close();
		inputStream.close();
		conn.connect();
		result = sb.toString();
		Map<String, Object> map = parseXml(result);
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "--->" + entry.getValue());
		}
	}
	public static void main(String[] args) throws Exception {
		checkOrder("3052591705033009", "3");
	}
}
