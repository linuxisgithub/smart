package com.system.controller;

import com.system.service.RegisterManager;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import javacommon.base.BaseSpringController;
import javacommon.util.HttpStatusCode;
import javacommon.util.VerificationCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 注册接口
 * 
 * @author lzj
 * 
 */
@Controller
@RequestMapping("/register")
public class RegisterController extends BaseSpringController<BaseModel> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private RedisTemplate redisTemplate;
	
	@Inject
	private Properties propertySetting;

	@Inject
	private RegisterManager registerManager;


     /**
	 * 注册保存数据
	 * 
	 * @param register
	 * @param request
	 * @param response
	 */
     /*
	@ResponseBody
	@RequestMapping(path = "/registerCompany", method = RequestMethod.POST)
	public void register(Register register, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info("公司注册手机号码：" + register.getTelephone());

			systemManager.register(register);
			
			gzipCompress.success(response);

		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("register error", e);
		}
	}*/

	/**
	 * 校验4位英文数字混合验证码
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(path = "/checkCode", method = RequestMethod.POST)
	public void checkCode(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String codeKey = request.getParameter("codeKey");
			String code = request.getParameter("code");

			if (StringUtils.isNotBlank(codeKey) && StringUtils.isNotBlank(code)) {

				String redisCode = (String) redisTemplate.opsForValue().get(
						codeKey);

				if (StringUtils.isNotBlank(redisCode)) {

					if (redisCode.equalsIgnoreCase(code)) {
						gzipCompress.fail(HttpStatusCode.SUCCESS, "match.ok",
								response);
					} else {
						gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
								"match.no", response);
					}
				} else {
					gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
							"match.no", response);
				}
			} else {
				return;
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("check code error", e);
		}
	}

/*	*//**
	 * 发送4位数字短信验证码
	 * 
	 * @param request
	 * @param response
	 *//*
	@ResponseBody
	@RequestMapping(path = "/sendMessage", method = RequestMethod.POST)
	public void sendMessage(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String phone = request.getParameter("phone");

			if (StringUtils.isNotBlank(phone)) {

				if (phone.length() != 11)
					return;

				Map<String, Object> data = registerManager.sendMessage(phone);
				String sendorNo = propertySetting.getProperty("sendorNo");
				if (!sendorNo.equalsIgnoreCase("yes")) {
					data.put("showMsg", 0);
				} else {
					data.remove("mobileCode");
				}
				gzipCompress.data(data, response);

			} else {
				return;
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("send message error", e);
		}
	}*/

	/**
	 * 验证4位数字短信验证码
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(path = "/checkMessage", method = RequestMethod.POST)
	public void checkMessage(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String code = request.getParameter("messageCode");
			String phone = request.getParameter("phone");

			if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(phone)) {

				String redisCode = (String) redisTemplate.opsForValue().get(
						phone);

				if (StringUtils.isNotBlank(redisCode)) {

					if (redisCode.equalsIgnoreCase(code)) {
						gzipCompress.fail(HttpStatusCode.SUCCESS, "match.ok",
								response);
					} else {
						gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
								"match.no", response);
					}
				} else {
					gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
							"match.no", response);
				}

			} else {
				return;
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("check message error", e);
		}
	}

	/**
	 * 邀请注册-获取4位验证码
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/getCodeImg", method = RequestMethod.GET)
	public void getCodeImg(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 生成验证码
			String randomCode = VerificationCode.getVerificationCode();

			String sessionId = request.getSession().getId();

			redisTemplate.opsForValue().set(
					"VERIFICATION_CODE_SG" + sessionId, randomCode.toString(),
					10L, TimeUnit.MINUTES);
			// 输出验证码图片
			String[] codeArr = new String[4];
			for (int i = 0; i < randomCode.length(); i++) {
				codeArr[i] = randomCode.substring(i, i + 1);
			}
			VerificationCode.getBSCodes(request, response, codeArr);
		} catch (Exception e) {
			logger.error("get code  error", e);
		}
	}

	/**
	 * 邀请注册-校验4位英文数字混合验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/checkCodeImg", method = RequestMethod.POST)
	public void checkCodeImg(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String code = request.getParameter("code");

			if (StringUtils.isNotBlank(code)) {

				String sessionId = request.getSession().getId();

				String redisCode = (String) redisTemplate.opsForValue().get(
						"VERIFICATION_CODE_SG" + sessionId);

				if (StringUtils.isNotBlank(redisCode)) {

					if (redisCode.equalsIgnoreCase(code)) {
						gzipCompress.fail(HttpStatusCode.SUCCESS, "match.ok",
								response);
					} else {
						gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
								"match.no", response);
					}
				} else {
					gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
							"match.no", response);
				}
			} else {
				return;
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("check code error", e);
		}
	}

/*	*//**
	 * 邀请注册验证短信验证码和手机号码
	 * 
	 * @param request
	 * @param response
	 *//*
	@ResponseBody
	@RequestMapping(path = "/checkMsgAndPhone", method = RequestMethod.POST)
	public void checkMsgAndPhone(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String code = request.getParameter("messageCode");
			String phone = request.getParameter("phone");

			if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(phone)) {

				String redisCode = (String) redisTemplate.opsForValue().get(
						phone);

				if (StringUtils.isNotBlank(redisCode)) {

					if (redisCode.equalsIgnoreCase(code)) {

						boolean is = registerManager.checkPhone(phone);
						if (is) {
							// 未注册
							gzipCompress.fail(HttpStatusCode.SUCCESS,
									"match.ok", response);
						} else {
							gzipCompress.fail(HttpStatusCode.EXIST_ERROR,
									"register.phone.have", response);
						}

					} else {
						gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
								"match.no", response);
					}
				} else {
					gzipCompress.fail(HttpStatusCode.NOT_MATCH_ERROR,
							"match.no", response);
				}

			} else {
				return;
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("check message error", e);
		}
	}*/
	
	/**
	 * 验证手机号码是否注册 userType 11 = 客户账号   0 = 商家
	 */
	@RequestMapping(value = "/isHavaPhone", method = RequestMethod.GET)
	public void isHavaPhone(HttpServletRequest request, HttpServletResponse response) {

		try {
			String phone = request.getParameter("phone");
			String userType = request.getParameter("userType");
			if (phone == null||"".equals(phone))
				return;

			boolean is = registerManager.checkPhone(phone,Integer.parseInt(userType));
			if (is) {
				// 未注册
				gzipCompress.fail(HttpStatusCode.NOT_EXIST_ERROR,
						"register.phone.nothave", response);
			} else {
				gzipCompress.fail(HttpStatusCode.EXIST_ERROR,
						"register.phone.have", response);
			}

		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("isHavaPhone  error", e);
		}
	}

	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}
}
