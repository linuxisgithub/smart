package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.TwVarietiesDao;
import com.sg.model.TwVarieties;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-14 14:27:12
 *
 */
@Service
public class TwVarietiesManager extends BaseManager<TwVarieties> {

	@Inject
	private TwVarietiesDao twVarietiesDao;
	@Override
	protected BaseMybatis3Dao<TwVarieties> getDao() {
		return twVarietiesDao;
	}

	public List<Map<String, Object>> findVarietiesData(Long conpanyId) {
		return twVarietiesDao.findVarietiesData(conpanyId);
	}
}