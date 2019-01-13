package com.sg.service;

import com.sg.dao.DishGroupDao;
import com.sg.model.DishGroup;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 *
 * @author 超享
 * @date 2019-01-03 11:02:37
 *
 */
@Service
public class DishGroupManager extends BaseManager<DishGroup> {

	@Inject
	private DishGroupDao dishGroupDao;
	@Override
	protected BaseMybatis3Dao<DishGroup> getDao() {
		return dishGroupDao;
	}

	public long checkName(String name,Long bid) {
		return dishGroupDao.checkName(name,bid);
	}
}