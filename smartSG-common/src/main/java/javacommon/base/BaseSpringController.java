package javacommon.base;

import com.alibaba.fastjson.JSONObject;
import com.system.model.LoginUser;
import com.system.service.SysMenuManager;
import javacommon.util.GZIPCompress;
import javacommon.util.HttpStatusCode;
import javacommon.util.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy
 * @since 2016/4/10
 */
public abstract class BaseSpringController<T extends BaseModel> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("rawtypes")
	@Inject
	protected RedisTemplate redisTemplate;

	@Inject
	private RabbitTemplate erpTemplate;

	@Inject
	protected GZIPCompress gzipCompress;

	@Inject
	protected SysMenuManager rightManager;

	protected abstract BaseManager<T> getManager();

	protected boolean beforeSave(T t, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return true;
	}

	protected void afterSave(T t, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

	@RequestMapping(method = RequestMethod.POST)
	public void save(@Valid T t, BindingResult br, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String validator = getErrors(br);
			if (StringUtils.isNotBlank(validator)) {
				gzipCompress.validator(validator, response);
				return;
			}
			if (beforeSave(t, request, response)) {
				// BaseModel model = (BaseModel) t;
				logger.info("获取的数据对象:" + t.toString());
				if (t.getEid() == null || t.getEid() == 0) {

					getManager().save(t);

				} else
					getManager().update(t);

				Map<String, Object> returnParam = t.getReturnParam();
				if (returnParam == null)
					gzipCompress.success(response);
				else {
					gzipCompress.data(returnParam, response);
				}

				afterSave(t, request, response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("save error", e);
		}
	}

	@RequestMapping(path = "/{pageNumber}", method = RequestMethod.POST)
	public void findPage(@PathVariable int pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> criteria = getCriteria(request);
			LoginUser loginUser = getLoginUser(request);
			criteria.put("l_userId", loginUser.getUser().getEid());
			criteria.put("l_companyId", loginUser.getCompany().getEid());
			Paginator<T> paginator = new Paginator<>(pageNumber);
			paginator.setCriteria(criteria);
			logger.info("获取的查询参数:" + criteria + "\t页码:" + pageNumber);
			getManager().findPage(paginator);
			gzipCompress.pages(paginator, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("save error", e);
		}
	}

	@RequestMapping(path = "/{eid}", method = RequestMethod.GET)
	public void findById(@PathVariable Long eid, HttpServletResponse response) {
		try {
			T t = getManager().get(eid);
			if (t != null)
				gzipCompress.data(t, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("findById error", e);
		}
	}

	/**
	 * 逻辑删除，（update状态）
	 * @param eid
	 * @param response
	 */
	@RequestMapping(path = "/isDelete/{eid}", method = RequestMethod.POST)
	public void delete(@PathVariable Long eid, HttpServletRequest request,HttpServletResponse response) {
		try {
			LoginUser loginUser = getLoginUser(request);
			Integer userType = loginUser.getUser().getUserType();
			int right = 0;
			if(userType > 1) {
				right = 1;
			} else {
				int num = rightManager.getSpecialRight(loginUser, 10903L);
				if(num > 0) {
					right = 1;
				}
			}
			if(right > 0) {
				getManager().isDelete(loginUser.getCompany().getEid(), eid);
				gzipCompress.success(response);
			} else {
				gzipCompress.fail(HttpStatusCode.ILLEGAL_ACTION, "delete.illegal", response);
			}

		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("findById error", e);
		}
	}

	@RequestMapping(path = "/{type}/findByIdWithFile/{eid}", method = RequestMethod.GET)
	public void findByIdAndFile(@PathVariable String type, @PathVariable Long eid,
			HttpServletResponse response) {
		try {
			if("model".equals(type)) {
				gzipCompress.data(getManager().getWithFile(eid), response);
			} else if("map".equals(type)){
				gzipCompress.data(getManager().getWithFileMap(eid), response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("findByIdAndFile error", e);
		}
	}
	@RequestMapping(path = "/{type}/findByIdWithCc/{eid}", method = RequestMethod.GET)
	public void findByIdWithCc(@PathVariable String type, @PathVariable Long eid,
			HttpServletResponse response) {
		try {
			if("model".equals(type)) {
				gzipCompress.data(getManager().getWithCc(eid), response);
			} else if("map".equals(type)){
				gzipCompress.data(getManager().getWithCcMap(eid), response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("findByIdWithCc error", e);
		}
	}
	@RequestMapping(path = "/{type}/findByIdWithFileAndCc/{eid}", method = RequestMethod.GET)
	public void findByIdWithFileAndCc(@PathVariable String type, @PathVariable Long eid,
			HttpServletResponse response) {
		try {
			if("model".equals(type)) {
				gzipCompress.data(getManager().getWithFileAndCc(eid), response);
			} else if("map".equals(type)){
				gzipCompress.data(getManager().getWithFileAndCcMap(eid), response);
			}
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("findByIdWithFileAndCc error", e);
		}
	}

	/**
	 * 前单/后单
	 *
	 * @param pageNumber
	 *            页码
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/PreOrNext/{pageNumber}", method = RequestMethod.POST)
	public void findPreOrNext(@PathVariable("pageNumber") int pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (pageNumber < 1) {

				gzipCompress.fail(HttpStatusCode.VALIDATOR_ERROR,
						"request.param.error", response);

			} else {

				Map<String, Object> criteria = getCriteria(request);
				Paginator<T> paginator = new Paginator<>(pageNumber, 1);

				LoginUser user = getLoginUser(request);

				criteria.put("l_userId", user.getUser().getEid());
				criteria.put("l_companyId", user.getCompany().getEid());
				paginator.setCriteria(criteria);

				logger.info("获取的查询参数:" + criteria + "\t页码:" + pageNumber);

				getManager().findByPageNumAndSize(paginator);
				gzipCompress.pages(paginator, response);
			}

		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR,
					"server.error", response);
			logger.error("findPreOrNext error", e);
		}
	}

	/**
	 * 获取查询参数
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> getCriteria(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> criteria = new HashMap<>();
		if (parameterMap != null && parameterMap.size() > 0) {

			for (String key : parameterMap.keySet()) {
				String[] parameters = parameterMap.get(key);
				if (parameters.length == 1) {
					if (key.startsWith("s_")) {
						// String类型
						criteria.put(key, parameters[0]);
					} else if (key.startsWith("i_")) {
						// int类型
						criteria.put(key, Integer.parseInt(parameters[0]));
					} else if (key.startsWith("l_")) {
						// long类型
						criteria.put(key, Long.parseLong(parameters[0]));
					} else if (key.startsWith("b_")) {
						// boolean类型
						criteria.put(key, Boolean.parseBoolean(parameters[0]));
					} else if (key.startsWith("sh_")) {
						// short类型
						criteria.put(key, Short.parseShort(parameters[0]));
					}
				} else {
					criteria.put(key, parameters);
				}
			}
		}
		return criteria;
	}

	public void pushMsg(String to, String type, Object msg) throws Exception {
		JSONObject pushMsg = new JSONObject();
		JSONObject data = new JSONObject();
		pushMsg.put("to", to);
		data.put(type, msg);
		pushMsg.put("datas", data);
		logger.info("推出的消息：" + pushMsg);
		erpTemplate.convertAndSend("erpMessageQueue", pushMsg.toJSONString());
	}

	/**
	 * 获取登录用户
	 *
	 * @param request
	 * @return
	 */
	protected LoginUser getLoginUser(HttpServletRequest request) {
		LoginUser user = (LoginUser) request.getAttribute("loginUser");
		return user;
	}

	public String getErrorMessages(BindingResult br) {
		if (br.hasFieldErrors()) {
			StringBuilder msg = new StringBuilder();
			List<FieldError> fieldErrors = br.getFieldErrors();
			for (int i = 0; i < fieldErrors.size(); i++) {
				if (i > 0) {
					msg.append(",");
				}
				msg.append(fieldErrors.get(i).getDefaultMessage());
			}
			return msg.toString();
		}
		return null;
	}
	public String getErrors(BindingResult br) {
		if (br.hasFieldErrors()) {
			StringBuilder msg = new StringBuilder();
			List<FieldError> fieldErrors = br.getFieldErrors();
			for (int i = 0; i < fieldErrors.size(); i++) {
				if (i > 0) {
					msg.append(",");
				}
				msg.append(fieldErrors.get(i).getField()).append(":")
						.append(fieldErrors.get(i).getDefaultMessage());
			}
			return msg.toString();
		}
		return null;
	}

}
