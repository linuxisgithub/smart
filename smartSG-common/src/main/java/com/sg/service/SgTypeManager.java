package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.SgTypeDao;
import com.sg.model.SgType;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-12 14:47:11
 *
 */
@Service
public class SgTypeManager extends BaseManager<SgType> {

	@Inject
	private SgTypeDao sgTypeDao;
	@Override
	protected BaseMybatis3Dao<SgType> getDao() {
		return sgTypeDao;
	}

	public List<Map<String, Object>> findTypeData(Long conpanyId) {
		return sgTypeDao.findTypeData(conpanyId);
	}

}