package com.sg.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sg.dao.CommonCcDao;
import com.sg.dao.ReplyDao;
import com.sg.dao.SuggestDao;
import com.sg.model.Reply;
import com.sg.model.Suggest;
import com.system.model.LoginUser;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.DateUtil;
import javacommon.util.OaBtype;
import javacommon.util.PushMessageUtil;

/**
 *
 * @author 超享
 * @date 2018-12-17 14:03:48
 *
 */
@Service
public class ReplyManager extends BaseManager<Reply> {

	@Inject
	private ReplyDao replyDao;
	@Inject
	private SuggestDao suggestDao;
	@Inject
	private CommonCcDao  commonCcDao;
	
	@Override
	protected BaseMybatis3Dao<Reply> getDao() {
		return replyDao;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void doSave(Reply reply, LoginUser loginUser) {
		final Integer STATUS = 1;
		reply.setCompanyId(loginUser.getCompany().getEid());
		reply.setDeptId(loginUser.getDepartment().getEid());
		reply.setSendId(loginUser.getUser().getEid());
		reply.setReplyDate(DateUtil.getDate_TImeStr());
		replyDao.save(reply);
		Suggest t = new Suggest(reply.getBid(),STATUS,null);
		suggestDao.update(t);
		Map<String,Object> param = new HashMap<>();
		param.put("l_userId", loginUser.getUser().getEid());
		param.put("l_bid", reply.getBid());
		param.put("i_status", STATUS);
		param.put("s_operate", "reply");
		commonCcDao.setStatus(param);
		
		// IM内部推送
		PushMessageUtil.pushMsg(suggestDao.findSendUserImAccount(reply.getBid()), OaBtype.PUSH_APPROVE.getCode(),
				PushMessageUtil.getSendMessageByBtype(OaBtype.TSJY_CODE.getBtype()));
	}

	public Map<String, Object> findMapByEid(Long eid) {
		return replyDao.findMapByEid(eid);
	}

}