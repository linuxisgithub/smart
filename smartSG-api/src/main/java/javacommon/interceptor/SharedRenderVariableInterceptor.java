package javacommon.interceptor;

import com.alibaba.fastjson.JSON;
import com.system.model.LoginUser;
import javacommon.util.GZIPCompress;
import javacommon.util.HttpStatusCode;
import javacommon.util.MD5Util;
import javacommon.util.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andy
 * @since 2016/4/11
 */
@SuppressWarnings("rawtypes")
public class SharedRenderVariableInterceptor extends HandlerInterceptorAdapter
		implements InitializingBean {

	private static Logger log = LoggerFactory
			.getLogger(SharedRenderVariableInterceptor.class);

	@Inject
	protected GZIPCompress gzipCompress;
	
	@Inject
	protected RedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("请求前置处理:" + request.getRequestURL() + (request.getQueryString()==null?"":("?" + request.getQueryString())));
		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			gzipCompress.fail(HttpStatusCode.TOKEN_ERROR, "token.empty",
					response);
			return false;
		} else {
			RedisTemplate redisTemplate = SpringContextUtil
					.getBean("redisTemplate");
			int hashToken = MD5Util.FNVHash1(token);
			String redisResult = (String) redisTemplate.opsForValue().get(
					String.valueOf(hashToken));
			if (StringUtils.isBlank(redisResult)) {
				gzipCompress.fail(HttpStatusCode.LOGIN_OVER_ERROR,
						"login.timeout", response);
				return false;
			}

			LoginUser loginUser = JSON
					.parseObject(redisResult, LoginUser.class);
			request.setAttribute("loginUser", loginUser);
		}
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
