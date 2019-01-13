package com.sg.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javacommon.base.BaseBsController;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sg.dao.CommonCcDao;
import com.sg.model.Reply;
import com.sg.model.Suggest;
import com.sg.service.ReplyManager;
import com.sg.service.SuggestManager;
import com.system.model.LoginUser;

import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import javacommon.util.DateUtil;
import javacommon.util.HttpStatusCode;
import javacommon.util.OaBtype;
import javacommon.util.Paginator;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:58:03
 *
 */
@Controller
@RequestMapping("/suggestions")
public class SuggestionsController extends BaseBsController<BaseModel> {
	
	@Inject
	private SuggestManager suggestManager;
	@Inject
	private ReplyManager replyManager;
	@Inject
	private CommonCcDao commonCcDao;

	@RequestMapping(path = "/to/{business}",method = RequestMethod.GET)
	public String to(@PathVariable String business,HttpServletRequest request,HttpServletResponse response){
		StringBuffer page = new StringBuffer("/platformManagement/suggestionsManagement/");
		page.append(business).append("/list");
		return page.toString();
	}
	
	@RequestMapping(path = "/to/reply", method = RequestMethod.GET)
	public String toReply(Long eid, Model model, HttpServletRequest request){
		return "/platformManagement/suggestionsManagement/receive/reply";
	}
	
	@RequestMapping(path = "/{business}/load", method = RequestMethod.GET)
	public String load(@PathVariable String business,Long eid, Model model, HttpServletRequest request){
		String view = "/platformManagement/suggestionsManagement/"+business+"/create";
		switch(business){
		case "send":
		case "receive":
			Suggest suggest;
			if (eid != null && eid != 0L){
				suggest = suggestManager.get(eid);
				List<Map<String, Object>> ccList = commonCcDao.findListByBid(eid, OaBtype.TSJY_CODE.getBtype());
				StringBuffer receives = new StringBuffer();
				for (Map<String, Object> cc : ccList) {
					receives.append(cc.get("userId").toString()).append(",");
				}
				String useIds = receives.toString();
				useIds = !useIds.equals("") ? useIds.substring(0,useIds.length()-1) : useIds;
				suggest.setCc(useIds);
				setCcToModel(model, ccList);
			}else{
				suggest = new Suggest();
				suggest.setCreateTime(DateUtil.getDateStr());
			}
			model.addAttribute(MODEL_KEY,suggest);
			break;
		case "reply":
			Map<String,Object> reply = replyManager.findMapByEid(eid);
			model.addAttribute(MODEL_KEY,reply);
			break;
		}
		return view;
	}
	
	@RequestMapping(path = "/send", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void send(Suggest suggest,HttpServletRequest request,HttpServletResponse response){
		try{
			suggestManager.doSave(suggest,getLoginUser(request));
			gzipCompress.success(response);
		}catch(Exception e){
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "server.error", response);
			logger.error("send error", e);
		}
	}
	@RequestMapping(path = "/reply", method = RequestMethod.POST)
	public void reply(Reply reply,HttpServletRequest request,HttpServletResponse response){
		try{
			replyManager.doSave(reply,getLoginUser(request));
			gzipCompress.success(response);
		}catch(Exception e){
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "server.error", response);
			logger.error("reply error", e);
		}
	}
	
	@RequestMapping(path = "/data/list", method = RequestMethod.POST)
	public void dataList(int pageNumber,HttpServletRequest request,HttpServletResponse response){
		try{
			LoginUser user = getLoginUser(request);
			Map<String, Object> criteria = getCriteria(request);
			criteria.put("l_companyId", user.getCompany().getEid());
			criteria.put("l_userId", user.getUser().getEid());
			Paginator<Suggest> paginator = new Paginator<>(pageNumber, Paginator.DEFAULT_PAGESIZE_BS);
			paginator.setCriteria(criteria);
			suggestManager.findPage(paginator);
			if("send".equals(criteria.get("s_business").toString()) 
					&& !paginator.getDataList().isEmpty()){
				StringBuffer s = new StringBuffer();
				for (Object obj : paginator.getDataList()) {
					JSONObject each = (JSONObject)obj;
					if(each.getString("msgType").equals("suggest")){
						JSONArray ccList = each.getJSONArray("ccList");
						if(!ccList.isEmpty()){
							for (Object o : ccList) {
								s.append(((Map)o).get("userName").toString()).append("、");
							}
							String receives = s.toString();
							if(receives.length() > 0){
								receives = receives.substring(0,receives.length() - 1);
							}
							each.put("receives", receives);
							s.setLength(0);//清空字符缓冲区
						}else{
							each.put("receives", "无");
						}
					}else{
						each.put("receives", each.getString("sendName"));
					}
				}
			}
			gzipCompress.pages(paginator, response);
		}catch(Exception e){
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "server.error", response);
			logger.error("dataList error", e);
		}
	}
	
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String toSelect(Model model,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		LoginUser user = getLoginUser(request);
		model.addAttribute("companyId", user.getCompany().getEid());
		model.addAttribute("useIds", request.getParameter("useIds"));
		return "/platformManagement/suggestionsManagement/send/select";
	}
	
	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public void set(HttpServletRequest request, HttpServletResponse response) {
		try{
			LoginUser user = getLoginUser(request);
			Map<String, Object> param = getCriteria(request);
			param.put("l_userId", user.getUser().getEid());
			param.put("s_operate", "reply");
			param.put("i_status", 2);
			suggestManager.set(param);
			gzipCompress.success(response);
		}catch(Exception e){
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "server.error", response);
		}
	}
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public void remove(Long eid,String msgType,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			Map<String, Object> criteria = getCriteria(request);
			suggestManager.remove(criteria,getLoginUser(request));
			gzipCompress.success(response);
		}catch(Exception e){
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "server.error", response);
		}
	}
		
	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}
	

}