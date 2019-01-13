package com.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sg.service.AnnexManager;
import com.system.model.LoginUser;
import com.system.model.SysUser;
import com.system.service.SysUserManager;
import javacommon.util.UUID;
import javacommon.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/DDX")
public class DDXController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	protected GZIPCompress gzipCompress;
	
	@Inject
	protected RedisTemplate redisTemplate;

	@Inject
	private AnnexManager annexManager;

	@Inject
	private SysUserManager sysUserManager;

	@Inject
	private RabbitTemplate erpTemplate;

	@Inject
	private Properties propertySetting;

	private static EmojiConverter converter;

	static {
		converter = new EmojiConverter.Builder()
				.from(EmojiConverter.Type.UNICODE)
				.to(EmojiConverter.Type.SOFTBANK).build();
	}

	/*** --USER_IN_SESSION不要改-- ***/
	private static final String USER_IN_SESSION = "USER_IN_SESSION";

	/**
	 * @author Chris
	 * @date 2016年4月01日 上午10:45:05
	 * @version 1.0
	 * @description 从配置文件中获取websocket和im地址
	 */
	@RequestMapping(path = "/getUrls")
	public void getUrls(HttpServletRequest request, HttpServletResponse response) {

		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {
			String wsUrl = (String) propertySetting.get("ws_url");
			String imUrl = (String) propertySetting.get("imRestful");
			result.put("wsUrl", wsUrl);
			result.put("imUrl", imUrl);
		} catch (Exception e) {
			result.put("status", 500);
			result.put("msg", e.getMessage());
			logger.error("DDXController : get urls  error", e);
		}
		gzipCompress.writeToString(result, response);

	}

	/**
	 * @author Chris
	 * @date 2016年4月01日 上午10:45:05
	 * @version 1.0
	 * @description 获取通讯录数据(异步)
	 */
	@RequestMapping(path = "/contactList")
	public void contactList(HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			if (loginUser != null && loginUser.getUser().getEid() != null) {
				List<Map<String, Object>> dataList = sysUserManager
						.findUsersByDeptId(loginUser, -1L);
				Map<String, List<JSONObject>> resultList = new TreeMap<String, List<JSONObject>>();// 默认是升序
				if (dataList != null && dataList.size() > 0) {
					JSONObject userJson = null;
					for (Map<String, Object> item : dataList) {
						userJson = new JSONObject();
						userJson.putAll(item);
						String userName = (String) userJson.get("name");
						String imAccount = (String) userJson.get("imAccount");
						if(!imAccount.equals(loginUser.getUser().getImAccount())) {
							if (StringUtils.isNotBlank(userName)) {
								// 1 首大写字母或者数字
								String chatFirst = FirstLetterUtil
										.getFirstSpell(userName.trim());
								// 2 判断map是否有该key
								if (resultList.containsKey(chatFirst)) {
									List<JSONObject> arraylist = (ArrayList<JSONObject>) resultList
											.get(chatFirst);
									arraylist.add(userJson);
								} else {
									List<JSONObject> arraylist = new ArrayList<JSONObject>();
									arraylist.add(userJson);
									resultList.put(chatFirst, arraylist);
								}
							}
						}
					}
				}
				result.put("contacts", resultList);
			}
		} catch (Exception e) {
			result.put("status", 500);
			logger.error("DDXController : get contact list error", e);
		}
		gzipCompress.writeToString(result, response);
	}

	/**
	 * 获取部门员工数
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/deptInfoCount")
	public void deptInfoCount(HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			if (loginUser != null && loginUser.getUser().getEid() != null) {
				List<Map<String, Object>> dataList = sysUserManager
						.deptContactsCount(loginUser.getCompany().getEid());
				result.put("deptInfo", dataList);
			}
		} catch (Exception e) {
			result.put("status", 500);
			logger.error("DDXController : get contact list error", e);
		}
		gzipCompress.writeToString(result, response);
	}

	/**
	 * 获取部门通讯录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(path = "/getDeptContactList")
	public void getDeptContactList(Long deptId, HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			List<Map<String, Object>> dataList = sysUserManager
					.findUsersByDeptId(loginUser, deptId);
			Map<String, List<JSONObject>> resultList = new TreeMap<String, List<JSONObject>>();// 默认是升序
			if (dataList != null && dataList.size() > 0) {
				JSONObject userJson = null;
				for (Map<String, Object> item : dataList) {
					userJson = new JSONObject();
					userJson.putAll(item);
					String userName = (String) userJson.get("name");
					String imAccount = (String) userJson.get("imAccount");
					if(!imAccount.equals(loginUser.getUser().getImAccount())) {
						if (StringUtils.isNotBlank(userName)) {
							// 1 首大写字母或者数字
							String chatFirst = FirstLetterUtil
									.getFirstSpell(userName.trim());
							// 2 判断map是否有该key
							if (resultList.containsKey(chatFirst)) {
								List<JSONObject> arraylist = (ArrayList<JSONObject>) resultList
										.get(chatFirst);
								arraylist.add(userJson);
							} else {
								List<JSONObject> arraylist = new ArrayList<JSONObject>();
								arraylist.add(userJson);
								resultList.put(chatFirst, arraylist);
							}
						}
					}
				}
			}
			result.put("deptContactList", resultList);
		} catch (Exception e) {
			result.put("status", 500);
			logger.error("DDXController : get contact list error", e);
		}
		gzipCompress.writeToString(result, response);
	}

	/**
	 * @author Chris
	 * @date 2016年4月01日 上午10:45:05
	 * @version 1.0
	 * @description 获取客服(异步)
	 */
	@RequestMapping(path = "/kefuList")
	public void kefuList(HttpServletRequest request,
			HttpServletResponse response) {

		// Map<String, Object> result = new HashMap<String, Object>();
		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {
			// 查询客服
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			// String versionStatus = loginUser.getVersionStatus();
			List<Map<String, Object>> resultMap = sysUserManager.findKefuList();
			result.put("contacts", resultMap);
		} catch (Exception e) {
			result.put("status", 500);
			result.put("msg", e.getMessage());
			logger.error("DDXController : get kefu list error", e);
		}
		// GZIPCompress.WriteToString(JSON.toJSON(result).toString(), response);
		gzipCompress.writeToString(result, response);

	}

	/**
	 * @author Chris
	 * @date 2016年4月01日 上午10:45:05
	 * @version 1.0
	 * @description 获取当前用户信息(异步)
	 */
	@RequestMapping(path = "/currentUser")
	public void currentUser(HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {
			// 获取当前登录用户
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			if (loginUser != null && loginUser.getUser().getEid() != null) {
				SysUser user = sysUserManager.get(loginUser.getUser().getEid());
				Map<String, Object> userInfo = new HashMap<String, Object>();
				userInfo.put("companyId", user.getCompanyId());
				userInfo.put("name", user.getName());
				// user.put("dpName", loginUser.getDpName());
				// user.put("jobRole", loginUser.getJobRole());
				userInfo.put("icon", user.getIcon());
				userInfo.put("imAccount", user.getImAccount());
				userInfo.put("password", user.getPassword());
				// user.put("dpName", loginUser.getDpName());
				userInfo.put("id", user.getEid());
				result.put("user", userInfo);
			}

		} catch (Exception e) {
			result.put("status", 500);
		}
		gzipCompress.writeToString(result, response);

	}

	/**
	 * @author Chris
	 * @date 2016年4月01日 上午10:45:05
	 * @version 1.0
	 * @description 根据用户ID获取用户数据(异步)
	 */
	@RequestMapping(path = "/getUserByUserId")
	public void getUserByUserId(HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {
			String userId = request.getParameter("userId");
			if (userId != null && !"".equals(userId)) {
				Long id = Long.parseLong(userId);
				SysUser user = sysUserManager.get(id);
				if (user != null) {
					result.put("companyId", user.getCompanyId());
					result.put("imAccount", user.getImAccount());
					result.put("icon", user.getIcon());
					result.put("name", user.getName());
				}
			}
		} catch (Exception e) {
			result.put("status", 500);
			result.put("msg", e.getMessage());
			logger.error("DDXController : get user by userId error", e);
		}
		gzipCompress.writeToString(result, response);
	}

	/**
	 * @author Chris
	 * @date 2016年6月10日 上午10:45:05
	 * @version 1.0
	 * @description 跳转选择联系人页面
	 */
	@RequestMapping(path = "/selectContacts")
	public ModelAndView selectContacts(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView result = new ModelAndView("/chat/selectContacts");
		try {
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			if (loginUser != null) {
				// 获取通讯录
				List<Map<String, Object>> dataList = sysUserManager
						.findUsersByDeptId(loginUser, -1L);
				Map<String, List<JSONObject>> resultList = new TreeMap<String, List<JSONObject>>();// 默认是升序
				if (dataList != null && dataList.size() > 0) {
					JSONObject userJson = null;
					for (Map<String, Object> item : dataList) {
						userJson = new JSONObject();
						userJson.putAll(item);
						String userName = (String) userJson.get("name");
						String imAccount = (String) userJson.get("imAccount");
						if(!imAccount.equals(loginUser.getUser().getImAccount())) {
							if (StringUtils.isNotBlank(userName)) {
								// 1 首大写字母或者数字
								String chatFirst = FirstLetterUtil
										.getFirstSpell(userName.trim());
								// 2 判断map是否有该key
								if (resultList.containsKey(chatFirst)) {
									List<JSONObject> arraylist = (ArrayList<JSONObject>) resultList
											.get(chatFirst);
									arraylist.add(userJson);
								} else {
									List<JSONObject> arraylist = new ArrayList<JSONObject>();
									arraylist.add(userJson);
									resultList.put(chatFirst, arraylist);
								}
							}
						}
					}
				}
				result.addObject("users", resultList);
				result.addObject("currentUser", loginUser);
			}
		} catch (Exception e) {
			logger.error("DDXController : get contacts error", e);
		}
		return result;
	}

	/**
	 * @author Chris
	 * @date 2016年6月10日 上午10:45:05
	 * @version 1.0
	 * @description 跳转选择发送人页面
	 */
	@RequestMapping(path = "/selectSend")
	public ModelAndView selectSend(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView result = new ModelAndView("/chat/send");
		try {
			Map<String, Object> stringObjectMap = (Map) request
					.getAttribute("params");
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			if (loginUser != null) {
				// 获取通讯录
				List<Map<String, Object>> dataList = sysUserManager
						.findUsersByDeptId(loginUser, -1L);

				Map<String, List<JSONObject>> resultList = new TreeMap<String, List<JSONObject>>();// 默认是升序
				if (dataList != null && dataList.size() > 0) {
					JSONObject userJson = null;
					for (Map item : dataList) {
						userJson = new JSONObject();
						userJson.putAll(item);

						String userName = (String) userJson.get("name");
						if (StringUtils.isNotBlank(userName)) {
							// 1 首大写字母或者数字
							String chatFirst = FirstLetterUtil
									.getFirstSpell(userName.trim());
							// 2 判断map是否有该key
							if (resultList.containsKey(chatFirst)) {
								List<JSONObject> arraylist = (ArrayList<JSONObject>) resultList
										.get(chatFirst);
								arraylist.add(userJson);
							} else {
								List<JSONObject> arraylist = new ArrayList<JSONObject>();
								arraylist.add(userJson);
								resultList.put(chatFirst, arraylist);
							}
						}
					}
				}
				result.addObject("users", resultList);
				result.addObject("currentUser", loginUser.getUser());
			}
		} catch (Exception e) {
			logger.error("DDXController : get contacts error", e);
		}
		return result;
	}

	/**
	 * @author Chris
	 * @date 2016年7月01日 上午10:45:05
	 * @version 1.0
	 * @description emoji表情转换
	 */
	@RequestMapping(path = "/ajaxConvert")
	public void ajaxConvert(HttpServletRequest request,
			HttpServletResponse response) {

		JSONObject result = new JSONObject();
		result.put("status", 200);
		String imgpath = request.getContextPath() + "/chat/images/emoji/";
		String text = request.getParameter("text");
		try {
			String convertedText = converter.convert(20, 20, imgpath, text);
			result.put("convertedText", convertedText);
		} catch (Exception e) {
			result.put("status", 500);
			result.put("msg", e.getMessage());
		}
		gzipCompress.writeToString(result, response);

	}

	/**
	 * 到个人信息页面
	 */
	@RequestMapping(path = "/userInfo")
	public ModelAndView userInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/chat/user/editUserInfo");
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute(
				USER_IN_SESSION);
		SysUser user = sysUserManager.get(loginUser.getUser().getEid());
		mv.addObject("data", user);
		return mv;
	}

	/**
	 * 个人信息修改
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/editUserInfo")
	public ModelAndView editUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/chat/user/editUserInfo");
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute(
				USER_IN_SESSION);
		SysUser user = sysUserManager.get(loginUser.getUser().getEid());
		mv.addObject("status", 200);
		try {

			String img_files = request.getParameter("img_files");
			String img_type = request.getParameter("img_type");
			String img_size = request.getParameter("img_size");
			String img_name = request.getParameter("img_name");

			if (!"".equals(img_files) && img_files != null) {
				// 上传头像
				Map result = annexManager.uplodImg(img_name, img_type,
						img_size, user.getEid(), img_files);

				String iconPath = (String) result.get("path");

				if (iconPath != null && !"".equals(iconPath)) {
					// 设置新头像
					loginUser.getUser().setIcon(iconPath);
					user.setIcon(iconPath);
				}
			}

			// 修改用户数据
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			user.setName(name);
			user.setSex(sex == null ? 1 : Integer.parseInt(sex));
			user.setEmail(email);
			user.setTelephone(telephone);
			sysUserManager.updateUser(loginUser, user);
			loginUser.setUser(user);
			
			String ip = PlatformParam.getRemoteIp(request);
			String uuid = UUID.getUUID();
			String token = MD5Util.base64Encode(uuid);
			String redisToken = MD5Util.MD5(ip + "_i_p_" + token);
			int FNVHash1Token = MD5Util.FNVHash1(redisToken);
			redisTemplate.opsForValue().set(
					PlatformParam.REDIS_LOGIN_KEY
							+ String.valueOf(FNVHash1Token),
					JSON.toJSONString(loginUser), 7L, TimeUnit.DAYS);
			CookieUtil.setCookie(PlatformParam.cookie_l_token, token, response);
			
			// 更新session用户
			loginUser.getUser().setName(name);
			request.getSession().removeAttribute(USER_IN_SESSION);
			request.getSession().setAttribute(USER_IN_SESSION, loginUser);
		} catch (Exception e) {
			mv.addObject("status", 500);
			mv.addObject("msg", e.getMessage());
		}
		mv.addObject("data", user);
		return mv;
	}
	
	/**
	 * 个人头像修改
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/editUserIcon")
	public void editUserIcon(HttpServletRequest request,
			HttpServletResponse response) {
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute(
				USER_IN_SESSION);
		SysUser user = sysUserManager.get(loginUser.getUser().getEid());
		String iconPath = "";
		try {
			String img_files = request.getParameter("img_files");
			String img_type = request.getParameter("img_type");
			String img_size = request.getParameter("img_size");
			String img_name = request.getParameter("img_name");

			if (!"".equals(img_files) && img_files != null) {
				// 上传头像
				Map result = annexManager.uplodImg(img_name, img_type,
						img_size, loginUser.getUser().getEid(), img_files);

				 iconPath = (String) result.get("path");

				if (iconPath != null && !"".equals(iconPath)) {
					// 设置新头像
					loginUser.getUser().setIcon(iconPath);
					
					user.setIcon(iconPath);
					sysUserManager.updateUser(loginUser, user);
					loginUser.setUser(user);
					
					String ip = PlatformParam.getRemoteIp(request);
					String uuid = UUID.getUUID();
					String token = MD5Util.base64Encode(uuid);
					String redisToken = MD5Util.MD5(ip + "_i_p_" + token);
					int FNVHash1Token = MD5Util.FNVHash1(redisToken);
					redisTemplate.opsForValue().set(
							PlatformParam.REDIS_LOGIN_KEY
									+ String.valueOf(FNVHash1Token),
							JSON.toJSONString(loginUser), 7L, TimeUnit.DAYS);
					CookieUtil.setCookie(PlatformParam.cookie_l_token, token, response);
					
					// 更新session用户
					request.getSession().removeAttribute(USER_IN_SESSION);
					request.getSession().setAttribute(USER_IN_SESSION, loginUser);
				}
			}
			gzipCompress.data(user.getIcon(),response);
		} catch (Exception e) {
			logger.error("DDXController : editUserIcon error", e);
		}
	}

	/**
	 * 到修改密码页面
	 */
	@RequestMapping(path = "/toEditPwd")
	public ModelAndView toEditPwd(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("/chat/user/editPwd");
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(path = "/editPwd")
	public ModelAndView editPwd(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/chat/user/editPwd");
		mv.addObject("status", 200);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		try {
			if (!StringUtils.isEmpty(oldPassword)
					&& !StringUtils.isEmpty(newPassword)
					&& !StringUtils.isEmpty(confirmPassword)
					&& newPassword.equals(confirmPassword)) {
				LoginUser loginUser = (LoginUser) request.getSession()
						.getAttribute(USER_IN_SESSION);
				SysUser user = sysUserManager.get(loginUser.getUser().getEid());
				if (user != null
						&& user.getPassword() != null
						&& MD5Util.MD5(MD5Util.MD5(oldPassword)).equals(
								user.getPassword())) {
					// 原密码匹配成功 执行修改
					user.setUserType(loginUser.getUser().getUserType());
					user.setPassword(MD5Util.MD5(MD5Util.MD5(newPassword)));
					sysUserManager.updatePwdByEid(user);
				} else {
					// 原密码错误
					mv.addObject("status", 501);
				}
			}
		} catch (Exception e) {
			mv.addObject("status", 500);
			mv.addObject("msg", e.getMessage());
			logger.error("修改密码失败:", e);
		}
		return mv;
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
	 * @author Chris
	 * @date 2016年4月01日 上午10:45:05
	 * @version 1.0
	 * @description 上传文件(异步)
	 */
	@RequestMapping(path = "/uploadFile")
	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		Long userId = 1l;
		Long companyId = 1l;
		JSONObject result = new JSONObject();
		result.put("status", 200);
		try {

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> multipartFileList = multipartRequest
					.getFiles("file");

			Map<String, Object> data = FileHandleUtils.MultipartFileUpload(
					multipartFileList, userId, companyId);
			Object path = data.get("path");
			Object fileSize = data.get("fileSize");
			result.put("filePath", path);
			result.put("fileSize", fileSize);

		} catch (Exception e) {
			result.put("status", 500);
			result.put("msg", e.getMessage());
			logger.error("DDXController : upload file error", e);
		}

		gzipCompress.writeToString(result, response);
	}

	@RequestMapping(path = "/selectContactsForShare")
	public ModelAndView selectContactsForShare(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView result = new ModelAndView("/chat/shareSend");
		try {
			LoginUser loginUser = (LoginUser) request.getSession()
					.getAttribute(USER_IN_SESSION);
			if (loginUser != null) {
				// 获取通讯录
				/*
				 * List<Map> dataList = sysUserFriendManager
				 * .findSendList(loginUser.getUser().getEid());
				 */
				List<Map> dataList = null;
				Map<String, List<JSONObject>> resultList = new TreeMap<String, List<JSONObject>>();// 默认是升序
				if (dataList != null && dataList.size() > 0) {
					JSONObject userJson = null;
					for (Map item : dataList) {
						userJson = new JSONObject();
						userJson.putAll(item);
						String userName = (String) userJson.get("name");
						if (StringUtils.isNotBlank(userName)) {
							// 1 首大写字母或者数字
							String chatFirst = FirstLetterUtil
									.getFirstSpell(userName.trim());
							// 2 判断map是否有该key
							if (resultList.containsKey(chatFirst)) {
								List<JSONObject> arraylist = (ArrayList<JSONObject>) resultList
										.get(chatFirst);
								arraylist.add(userJson);
							} else {
								List<JSONObject> arraylist = new ArrayList<JSONObject>();
								arraylist.add(userJson);
								resultList.put(chatFirst, arraylist);
							}
						}
					}
				}
				result.addObject("users", resultList);
				result.addObject("currentUser", loginUser);
			}
		} catch (Exception e) {
			logger.error("DDXController : get contacts error", e);
		}
		return result;
	}

}