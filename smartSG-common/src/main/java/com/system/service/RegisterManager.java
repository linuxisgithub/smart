package com.system.service;

import com.system.dao.RegisterDao;
import com.system.model.Register;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 注册
 *
 * @author lzj
 */
@SuppressWarnings("unchecked")
@Service
public class RegisterManager extends BaseManager<Register> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private RegisterDao registerDao;

	@Inject
	private RedisTemplate redisTemplate;

	@Inject
	private Properties propertySetting;

	@Inject
	private SysUserManager sysUserManager;

//	@Inject
//	private CurrencyManager currencyManager;
//
//	@Inject
//	private UserDeviceManager deviceManager;

	@Override
	protected BaseMybatis3Dao<Register> getDao() {
		return registerDao;
	}

//	@Transactional(rollbackFor=Exception.class)
//	public void saveUser(Register t) throws Exception {
//
//		/** 保存用户信息 **/
//		String fourRandom = MobileCode.generate4NumCode();
//		SysUser user = new SysUser();
//		String imAccount = t.getPhone().trim() + "_erp_"+fourRandom;
//		user.setAccount(t.getPhone().trim());
//		user.setImAccount(imAccount);
//		user.setTelephone(t.getPhone().trim());
//		user.setPassword(t.getPassword().trim());
//		user.setVersionStatus("0");
//		user.setName(t.getPhone().trim());
//		user.setUseStatus("1");
//		user.setSex("1");
//		user.setStatus("3");
//		//设置为管理员
//		user.setUserType(3);
//		user.setRootId(-1L);
//		user.setPid(-1L);
//		user.setLevel(1);
//
//		sysUserDao.save(user);
//		sysUserDao.updateRootId(user.getEid());
//
//		currencyManager.saveLocal(user.getEid());// 注册时默认添加一个人民币[本位币]币种
//
//		// 注册IM
//		String url = propertySetting.getProperty("imRestful") + "register";
//		Map<String, Object> imrestParam = new HashMap<>();
//		imrestParam.put("account", imAccount);
//		imrestParam.put("password", user.getPassword());
//		imrestParam.put("name", user.getName() == null ? user.getTelephone()
//				: user.getName());
//		imrestParam.put("icon", user.getIcon() == null ? "" : user.getIcon());
//		imrestParam.put("companyId", "-1");
//		String post = HttpClientUtil.post(url, imrestParam);
//		JSONObject response = (JSONObject) JSON.parse(post);
//		logger.warn("注册到IM服务:地址:" + url + "\n参数：" + imrestParam + "\n返回:"
//				+ response);
//		if (response.getInteger("status") != HttpStatus.SC_OK
//				&& response.getInteger("status") != HttpStatus.SC_GONE) {
//			throw new RuntimeException("IM注册失败");
//		}
//
//		String content = "欢迎使用进销存·流水账，帐号："
//				+t.getPhone().trim()
//				+ "；客服：400-1042828";
//		logger.warn("用户注册,发送信息至:" + t.getPhone().trim());
//		PushMessageUtil.msmQueue(t.getPhone().trim(),
//				OaBtype.MESSAGE_SER_TYPE_004.getCode(), false, content);
//
//		deviceManager.saveForRegister(user);
//	}

	/**
	 * 手机号是否注册
	 *
	 * @param /t
	 * @return true 未注册，false 已注册
	 */
	public boolean checkPhone(String phone,Integer userType) {
		boolean flg = true;
		long userCount = sysUserManager.findCountByPhone(phone,userType);
		if (userCount > 0) {
			flg = false;
		}
		return flg;
	}

	/**
	 * 获取4位英文数字混合验证码
	 *
	 * @return
	 */

	public Map<String, Object> getCode() {

		String code = VerificationCode.getVerificationCode();
		Map<String, Object> map = new HashMap<>();

		String codeKey = UUID.getUUID();
		redisTemplate.opsForValue().set(codeKey, code, 10L, TimeUnit.MINUTES);
		map.put("codeKey", codeKey);
		map.put("code", code);

		return map;
	}

	/**
	 * 发送4位数字手机短信验证码
	 *
	 * @param phone
	 * @return
	 */
	public Map<String, Object> sendMessage(String phone) {
		Map<String, Object> map = new HashMap<>();
		// 获取4位随机数字
		String mobileCode = MobileCode.generate4NumCode();
		String content = "验证码" + mobileCode
				+ "[smartSG]。";

		logger.warn("注册/忘记密码,发送信息至:" + phone.trim()+";验证码："+mobileCode);
		// 短信队列
		PushMessageUtil.msmQueue(phone.trim(), content);
		// 存入redis下次验证
		redisTemplate.opsForValue()
				.set(phone, mobileCode, 10, TimeUnit.MINUTES);
		map.put("status", HttpStatusCode.SUCCESS.value());
		map.put("messageCode",mobileCode);
		map.put("phone", phone);
		return map;
	}

}
