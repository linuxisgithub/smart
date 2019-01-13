package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.FieldDao;
import com.sg.model.Field;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:57:33
 *
 */
@Service
public class FieldManager extends BaseManager<Field> {

	@Inject
	private FieldDao fieldDao;
	@Override
	protected BaseMybatis3Dao<Field> getDao() {
		return fieldDao;
	}

	public List<Map<String, Object>> findTypeData(Long conpanyId,Integer type) {
		return fieldDao.findTypeData(conpanyId,type);
	}

}