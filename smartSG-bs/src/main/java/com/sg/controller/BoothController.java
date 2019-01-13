package com.sg.controller;

import com.sg.model.Binding;
import com.sg.model.Img;
import com.sg.model.SgField;
import com.sg.model.TwRenovation;
import com.sg.service.BindingManager;
import com.sg.service.ImgManager;
import com.sg.service.SgFieldManager;
import com.sg.service.TwRenovationManager;
import com.system.model.LoginUser;
import com.system.model.SysUser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 摊位管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(path = "/tw")
public class BoothController extends BaseBsController<BaseModel>{

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private SysUserManager sysUserManager;
	@Inject
    private BindingManager bindingManager;
	@Inject
	private ImgManager imgManager;
	@Inject
	private SgFieldManager sgFieldManager;
	@Inject
	private TwRenovationManager twRenovationManager;
	
	@RequestMapping(path = "/to/{function}/list" , method = RequestMethod.GET)
	public String to(String function,Model model, HttpServletRequest request){
		String page =  "/platformManagement/foodCourtManagement/type/list";
		switch (function) {
		case "decoration":
			
			break;
//		case "":
//			
//			break;
//		case "":
//			
//			break;
//		case "":
//			
//			break;
		default:
			break;
		}
		return page;
	}

	@RequestMapping(path = "/toBinding/{type}",method = RequestMethod.GET)
	public String toBinding(@PathVariable String type, HttpServletRequest request, Model model){
		String view = "/tw/binding";
		model.addAttribute("type",type);
		return view;
	}

	@RequestMapping(value = "/binding", method = RequestMethod.POST)
	public void login(String account, String password,String type,
					  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	    LoginUser loginUser = getLoginUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser sysUser = sysUserManager.findByAccount(account);
		Integer uType = 3;
		String strType = "食阁";
		if(type.equals("tw")){
			uType = 4;
			strType = "摊位";
		}
		if (sysUser != null) {
			if (sysUser.getUserType() != uType){
				result.put("result",4);
				result.put("msg", "该账号不是"+strType+"账号！");
			}else if(sysUser.getLevel()!=1){
				result.put("result",5);
				result.put("msg", "该账号不是领导层账号！");
			} else{
				String _password = sysUser.getPassword();
				if (password.equals(_password)) {
					Integer status = sysUser.getStatus();
					if(1 != status) {
						result.put("result", 1);
						result.put("msg", "该账号已经停用！");
					} else if (loginUser.getUser().getDeptId() == sysUser.getDeptId()){
						result.put("result", 6);
						result.put("msg", "不能绑定当前摊位账号！");
					}else {
                        Binding binding = new Binding();
                        binding.setBid(loginUser.getUser().getDeptId());
                        binding.setBbid(sysUser.getDeptId());
                        binding.setUserId(loginUser.getUser().getEid());
                        binding.setType(type);
                        bindingManager.save(binding);
						Map<String, Object> menu = new HashMap<>();
						String id = "501";
						if (type.equals("tw")){
							id = "401";
						}
						menu.put("id",id.concat(sysUser.getDeptId().toString()));
						menu.put("pid",id);
						menu.put("name",sysUser.getDeptName());
						menu.put("level",2);
						menu.put("menuShow",1);
						menu.put("hasRight",1);
						result.put("menu",menu);
						result.put("result", 0);
						result.put("msg", "绑定成功！");
					}
				} else {
					result.put("msg", "账号与密码不匹配！");
					result.put("result", 3);// 密码不存在
				}
			}
		} else {
			result.put("result", 2);// 账号不存在
			result.put("msg", "账号与密码不匹配！");
		}
		gzipCompress.data(result, response);
	}

	/**报表 */
	@RequestMapping(path = "/to/report" , method = RequestMethod.GET)
	public String toReport(Model model, HttpServletRequest request){
		LoginUser user = getLoginUser(request);
		String page =  "/tw/report";
		String sgid = request.getParameter("id");
		String type = request.getParameter("type");
		model.addAttribute("sgId",sgid);
		model.addAttribute("type",type);
		return page;
	}

	/** 摊位装修 */
	@RequestMapping(path = "/to/renovation" , method = RequestMethod.GET)
	public String toRenovation(Model model, HttpServletRequest request){
		LoginUser user = getLoginUser(request);
		String page =  "/tw/renovation/renovation";
		String id = request.getParameter("id");
		model.addAttribute("twId",id);
		String type = request.getParameter("type");
		model.addAttribute("type",type);
		List<TwRenovation> twRenovations = twRenovationManager.findByBid(Long.parseLong(id));
		TwRenovation twRenovation;
		if (twRenovations.isEmpty()){
			twRenovation = new TwRenovation();
		}else{
			twRenovation = twRenovations.get(0);
		}
		List<SgField> fields = sgFieldManager.findByBid(Long.parseLong(id));
		List<Img> imgs = imgManager.findByBidAndDtype(Long.parseLong(id),2);
		model.addAttribute(MODEL_KEY,twRenovation);
		model.addAttribute("imgs",imgs);
		model.addAttribute("fields",fields);
		return page;
	}

	@RequestMapping(value = "/renovation/save", method=RequestMethod.POST)
	public void saveSgInfo(TwRenovation twRenovation, HttpServletRequest request, HttpServletResponse response) {
		twRenovationManager.doSave(twRenovation);
		gzipCompress.success(response);
	}

	@RequestMapping(value = "/bindingData", method=RequestMethod.POST)
	public void bindingData(String type,
								HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<Map<String,Object>> data = bindingManager.findDeptByBid(user.getUser().getDeptId(),type);
		gzipCompress.data(data, response);
	}


	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}

}
