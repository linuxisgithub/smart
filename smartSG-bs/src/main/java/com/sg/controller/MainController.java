package com.sg.controller;

import com.system.model.LoginUser;
import com.system.model.SysDictionary;
import com.system.model.SysUser;
import com.system.service.SysDictionaryManager;
import com.system.service.SysUserManager;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * 公共处理类
 *
 * @author lzj
 *
 * @2017年6月27日
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseBsController<BaseModel> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private SysUserManager sysUserManager;

	@Inject
	private SysDictionaryManager sysDictionaryManager;





	@RequestMapping("/toCc")
	public String toCc(Long pid, HttpServletRequest request,
			Model model) throws UnsupportedEncodingException {

		LoginUser loginUser = getLoginUser(request);
		String title = request.getParameter("title");
		if(title != null) {
			title = URLDecoder.decode(title, "utf-8");
		}
		List<SysUser> list = sysUserManager.findAllByCompanyId(loginUser);
		model.addAttribute("userList", list);
		model.addAttribute("title", title);
		return "/common/cc";
	}



	/**
	 * 获取静态数据
	 * @param btype
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/dictionaryList/{btype}", method=RequestMethod.POST)
	public void findDictionaryList(@PathVariable String btype,
			HttpServletRequest request, HttpServletResponse response) {
		List<SysDictionary> data = sysDictionaryManager.findDictionaryList(btype);
		gzipCompress.data(data, response);
	}


	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}

}
