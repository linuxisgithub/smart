package javacommon.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.system.model.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javacommon.util.CookieUtil;
import javacommon.util.HttpStatusCode;
import javacommon.util.MD5Util;
import javacommon.util.PlatformParam;
import javacommon.util.SpringContextUtil;

/**
 * @author Andy
 * @since 2016/4/11
 */
public class SharedRenderVariableInterceptor extends HandlerInterceptorAdapter
		implements InitializingBean {

	private static Logger log = LoggerFactory
			.getLogger(SharedRenderVariableInterceptor.class);


	@SuppressWarnings("rawtypes")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("请求前置处理:" + request.getRequestURL() + (request.getQueryString()==null?"":("?" + request.getQueryString())));
		HttpSession session = request.getSession();
		Object object = session.getAttribute(PlatformParam.USER_IN_SESSION);
		if(object == null) {
			String l_token = CookieUtil.getValue(PlatformParam.cookie_l_token, request);
			if(l_token != null) {
				String ip = PlatformParam.getRemoteIp(request);
				String redisToken = MD5Util.MD5(ip + "_i_p_" + l_token);
				int FNVHash1Token = MD5Util.FNVHash1(redisToken);
				RedisTemplate redisTemplate = SpringContextUtil.getBean("redisTemplate");
				String redisResult = (String) redisTemplate.opsForValue().get(PlatformParam.REDIS_LOGIN_KEY + String.valueOf(FNVHash1Token));
				if (StringUtils.isNoneBlank(redisResult)) {
					LoginUser loginUser = JSON.parseObject(redisResult, LoginUser.class);
					session.setAttribute(PlatformParam.USER_IN_SESSION, loginUser);
				} else {
					loginFalse(request, response);
					return false;
				}
			} else {
				loginFalse(request, response);
				return false;
			}
		} else {
			if(!(object instanceof LoginUser)) {
				loginFalse(request, response);
				return false;
			}
		}
        request.setAttribute("request_startTime", System.currentTimeMillis());  
		return true;
	}
	private void loginFalse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		StringBuilder builder = new StringBuilder();
		if(PlatformParam.isAjaxRequest(request)) {
			JSONObject result = new JSONObject();
			result.put("status", HttpStatusCode.LOGIN_OVER_ERROR.value());
			result.put("msg", "登录超时");
			
//			builder.append("timeout");
			builder.append(result.toJSONString());
		} else {
			
			builder.append("<script type=\"text/javascript\">");
			//builder.append("alert('登录超时，请重新登录');");
			builder.append("window.top.location.href='"+request.getContextPath()+"/login.htm';");
			builder.append("</script>");
		}
		out.print(builder.toString());
		out.flush();
		out.close();
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
