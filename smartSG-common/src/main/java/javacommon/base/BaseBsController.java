package javacommon.base;

import com.system.model.LoginUser;
import com.system.service.SysMenuManager;
import javacommon.util.GZIPCompress;
import javacommon.util.HttpStatusCode;
import javacommon.util.Paginator;
import javacommon.util.PlatformParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class BaseBsController<T extends BaseModel> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static String MODEL_KEY = "model";
	public final static String CC_ALL_KEY = "ccModel";
	public final static String CC_THREE_KEY = "ccThree";
	public final static String CY_ALL_KEY = "cyModel";
	public final static String CY_THREE_KEY = "cyThree";
	public final static String MODELS_KEY = "models";

	@Inject
	protected GZIPCompress gzipCompress;

	@Inject
	protected SysMenuManager rightManager;

	protected abstract BaseManager<T> getManager();

	/**
	 * 获取查询参数
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	protected Map<String, Object> getCriteriaDeprecated(
			HttpServletRequest request) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map<String, Object> getCriteria(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();

			if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					if (name.startsWith("s_")) {
						// String类型
						returnMap.put(name, values[i]);
					} else if (name.startsWith("i_")) {
						// int类型
						returnMap.put(name, Integer.parseInt(values[i]));
					} else if (name.startsWith("l_")) {
						// long类型
						returnMap.put(name, Long.parseLong(values[i]));
					} else if (name.startsWith("b_")) {
						// boolean类型
						returnMap.put(name, Boolean.parseBoolean(values[i]));
					} else if (name.startsWith("sh_")) {
						// short类型
						returnMap.put(name, Short.parseShort(values[i]));
					}
				}
			}
		}
		return returnMap;
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
	public void setCcToModel(Model model, List<Map<String, Object>> ccList) {
		setCcToModel(model, ccList, "userName");
	}
	public void setCcToModel(Model model, List<Map<String, Object>> ccList, String nameKey) {
		if(ccList.size() != 0){
			StringBuilder sb = new StringBuilder();
			String ccThree = "";
			int x = 1;
			for(Map<String,Object> map : ccList){
				String name = (String) map.get(nameKey);
				sb.append(name + "、");
				if(x <= 3){
					ccThree += name + "、";
					x++;
				}
			}
			String ccStr = sb.toString();
			ccThree = ccThree.substring(0, ccThree.length() -1);
			if(ccList.size() > 3) {
				if(ccThree.length() >= 15) {
					ccThree = ccThree.substring(0, 15) + "...";
				} else {
					ccThree = ccThree + "...";
				}
			}
			model.addAttribute(CC_ALL_KEY, ccStr.substring(0, ccStr.length() - 1));
			model.addAttribute(CC_THREE_KEY, ccThree);
		}
	}
	//参与人
	public void setCyToModel(Model model, List<Map<String, Object>> cyList) {
		setCyToModel(model, cyList, "userName");
	}
	/**
	 * 参与人（项目任务-项目管理）
	 * @param model
	 * @param ccList
	 * @param nameKey
	 */
	public void setCyToModel(Model model, List<Map<String, Object>> cyList, String nameKey) {
		if(cyList.size() != 0){
			StringBuilder sb = new StringBuilder();
			String cyThree = "";
			int x = 1;
			for(Map<String,Object> map : cyList){
				String name = (String) map.get(nameKey);
				sb.append(name + "、");
				if(x <= 4){
					cyThree += name + "、";
					x++;
				}
			}
			String cyStr = sb.toString();
			cyThree = cyThree.substring(0, cyThree.length() -1);
			if(cyThree.length() >= 12) {
				cyThree = cyThree.substring(0, 10) + "...";
			}
			model.addAttribute(CY_ALL_KEY, cyStr.substring(0, cyStr.length() - 1));
			model.addAttribute(CY_THREE_KEY, cyThree);
		}
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

	/**
	 * 获取登录用户
	 *
	 * @param request
	 * @return
	 */
	protected LoginUser getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		LoginUser user = (LoginUser) session
				.getAttribute(PlatformParam.USER_IN_SESSION);
		return user;
	}

	/**
	 * 可以绑定日期参数
	 *
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
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
			logger.error("delete error", e);
		}
	}

	/**
	 * BS-设置分页(前单后单)返回数据
	 *
	 * @param <V>
	 * @param model
	 * @param paginator
	 */
	protected <V> void setPaginatorData(Model model, Paginator<V> paginator) {
		model.addAttribute("total", paginator.getTotal());
		model.addAttribute("currentPage", paginator.getCurrentPage());
		List<V> list = paginator.getDataList();
		if (list != null && list.size() > 0) {
			model.addAttribute("data", list.get(0));
		}
		model.addAttribute("pageCount", paginator.getPages());
	}

	protected String getShareUrl(HttpServletRequest request, String url) {
		String contextPath = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+
				request.getServerPort()+contextPath;
		return basePath + url;
	}

	protected void decodeSearchParam(String paramKey, HttpServletRequest request, Model model) {
		String s_param = request.getParameter(paramKey);
    	if(s_param != null && s_param.trim().length() > 0) {
    		try {
				s_param = URLDecoder.decode(s_param, "utf-8");
				model.addAttribute(paramKey, s_param);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
	}
}
