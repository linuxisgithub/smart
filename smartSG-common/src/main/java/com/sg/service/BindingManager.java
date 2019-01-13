package com.sg.service;

import javax.inject.Inject;

import com.sg.model.Binding;
import org.springframework.stereotype.Service;

import com.sg.dao.BindingDao;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-28 09:17:52
 *
 */
@Service
public class BindingManager extends BaseManager<Binding> {

	@Inject
	private BindingDao bindingDao;
	@Override
	protected BaseMybatis3Dao<Binding> getDao() {
		return bindingDao;
	}

	public List<Map<String, Object>> findDeptByBid(Long bid, String type) {
		return bindingDao.findDeptByBid(bid,type);
	}

}