package com.system.restcontroller;

import com.system.model.Register;
import com.system.service.RegisterManager;
import javacommon.base.BaseManager;
import javacommon.base.BaseSpringController;
import javacommon.util.HttpStatusCode;
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
import java.util.Map;

/**
 * 注册接口
 * 
 * @author lzj
 * 
 */
@Controller
@RequestMapping("/register")
@SuppressWarnings("all")
public class RegisterController extends BaseSpringController<Register> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private RegisterManager registerManager;

	@Inject
	private RedisTemplate redisTemplate;

	/**
	 * 注册公司
	 * @param t
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/registerCompany", method = RequestMethod.POST)
	public void save(Register t,HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//registerManager.saveUser(t);
			gzipCompress.success(response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("注册公司 error", e);
		}
	}


	/**
	 * 获取4英文数字混合验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/getCode", method = RequestMethod.GET)
	public void getCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			gzipCompress.data(registerManager.getCode(), response);
		} catch (Exception e) {
			logger.error("获取4英文数字混合验证码 error", e);
		}
	}
	

	/**
	 * 校验4位英文数字混合验证码
	 * 
	 * @param request
	 * @param response
	 */
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
				gzipCompress.fail(HttpStatusCode.VALIDATOR_ERROR,
						"match.no", response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("校验4位英文数字混合验证码 error", e);
		}
	}

	/**
	 * 发送4位数字短信验证码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/sendMessage", method = RequestMethod.POST)
	public void sendMessage(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String phone = request.getParameter("phone");

			if (StringUtils.isNotBlank(phone)/* && phone.length() == 11*/) {
				Map<String, Object> data = registerManager.sendMessage(phone);
				gzipCompress.data(data, response);
			} else {
				gzipCompress.fail(HttpStatusCode.VALIDATOR_ERROR,
						"match.no", response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("发送4位数字短信验证码 error", e);
		}
	}
	
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
	 * 验证手机号码是否注册
	 * @param phone
	 * @param userType 账号类型
	 */
	@RequestMapping(value = "/isHavaPhone", method = RequestMethod.GET)
	public void isHavaPhone(HttpServletRequest request, HttpServletResponse response) {
		try {
			String phone = request.getParameter("phone");
			String userType = request.getParameter("userType");
			if (StringUtils.isBlank(phone))
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
	protected BaseManager<Register> getManager() {
		return null;
	}
}
