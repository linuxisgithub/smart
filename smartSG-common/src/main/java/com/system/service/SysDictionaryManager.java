package com.system.service;

import com.system.dao.SysDictionaryDao;
import com.system.model.SysDictionary;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chaoxiang
 * @date 2017-07-03 10:39:00
 *
 */
@Service
public class SysDictionaryManager extends BaseManager<SysDictionary> {

	@Inject
	private SysDictionaryDao dictionaryDao;

	@Override
	protected BaseMybatis3Dao<SysDictionary> getDao() {
		return dictionaryDao;
	}

	public List<Map<String, Object>> findAllForMap() {
		return dictionaryDao.findAllForMap();
	}

	public List<SysDictionary> findDictionaryList(String btype) {
		return dictionaryDao.findDictionaryList(btype);
	}

	public List findLogisticsList() {
		return dictionaryDao.findLogisticsList();
	}

}