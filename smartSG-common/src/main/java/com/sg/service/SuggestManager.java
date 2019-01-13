package com.sg.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sg.dao.CommonCcDao;
import com.sg.dao.ReplyDao;
import com.sg.dao.SuggestDao;
import com.sg.model.Reply;
import com.sg.model.Suggest;
import com.system.dao.SysUserDao;
import com.system.model.LoginUser;
import com.system.model.SysUser;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.BeanUtil;
import javacommon.util.DateUtil;
import javacommon.util.OaBtype;
import javacommon.util.PushMessageUtil;

/**
 *
 * @author 超享
 * @date 2018-12-17 13:57:00
 *
 */
@Service
public class SuggestManager extends BaseManager<Suggest> {

	@Inject
	private SuggestDao suggestDao;
	@Inject 
	private CommonCcDao commonCcDao;
	@Inject
	private ReplyDao replyDao;
	
	@Override
	protected BaseMybatis3Dao<Suggest> getDao() {
		return suggestDao;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void doSave(Suggest suggest, LoginUser loginUser) {
		if(suggest.getEid() == null){
			suggest.setSendDate(DateUtil.getDate_TImeStr());
			suggest.setCompanyId(loginUser.getCompany().getEid());
			suggest.setDeptId(loginUser.getDepartment().getEid());
			suggest.setSendId(loginUser.getUser().getEid());
			suggestDao.save(suggest);
			
			// 保存抄送人
			String ccjson = suggest.getCc();
			JSONArray obj = null;
			StringBuffer imAccounts = new StringBuffer();
			if (StringUtils.isNotBlank(ccjson) && !"[]".equals(ccjson)) {
				obj = JSON.parseArray(ccjson);
				Map<String, Object> map = new HashMap<>();
				map.put("cc", obj.toArray());
				map.put("bid", suggest.getEid());
				map.put("type", OaBtype.TSJY_CODE.getBtype());
				commonCcDao.saveCC(map, null);
				for (int i = 0; i < obj.size(); i++) {
					imAccounts.append(obj.getJSONObject(i).getString("imAccount")).append(",");
				}
			}
			String ims = imAccounts.toString();
			ims = !ims.equals("") ? ims.substring(0,ims.length()-1) : ims;
			System.out.println(ims);
			// IM内部推送
			PushMessageUtil.pushMsg(ims, OaBtype.PUSH_APPROVE.getCode(),
					PushMessageUtil.getSendMessageByBtype(OaBtype.TSJY_CODE.getBtype()));
		}else{
			suggestDao.update(suggest);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void set(Map<String, Object> param) {
		commonCcDao.setStatus(param);
	}

	@Transactional(rollbackFor = Exception.class)
	public void remove(Map<String, Object> criteria,LoginUser user) {
		final Integer STATUS = 1;
		switch ((String)criteria.get("s_msgType")) {
		case "suggest":
			Suggest suggest = suggestDao.get((long)criteria.get("l_eid"));
			if(suggest != null){
				if(suggest.getSendId().longValue() == user.getUser().getEid().longValue()){
					Suggest s = new Suggest((long)criteria.get("l_eid"), null, STATUS);
					suggest.setIsDel(STATUS);
					suggestDao.update(s);
				}else{
					criteria.put("s_operate", "delete");
					criteria.put("l_bid", criteria.get("l_eid"));
					criteria.put("l_userId", user.getUser().getEid());
					criteria.put("i_status", STATUS);
					commonCcDao.setStatus(criteria);
				}
			}
			break;
		case "reply":
			Reply reply = replyDao.get((long)criteria.get("l_eid"));
			if(reply != null){
				Reply r;
				if(reply.getSendId().longValue() == user.getUser().getEid().longValue()){
					r = new Reply((long)criteria.get("l_eid"), STATUS, null);
				}else{
					r = new Reply((long)criteria.get("l_eid"), null, STATUS);
				}
				replyDao.update(r);
			}
			break;
		default:
			break;
		}
	}
	
}