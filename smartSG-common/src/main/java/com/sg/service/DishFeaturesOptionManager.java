package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.DishFeaturesOptionDao;
import com.sg.model.DishFeaturesOption;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2019-01-04 15:59:41
 *
 */
@Service
public class DishFeaturesOptionManager extends BaseManager<DishFeaturesOption> {

	@Inject
	private DishFeaturesOptionDao dishFeaturesOptionDao;
	@Override
	protected BaseMybatis3Dao<DishFeaturesOption> getDao() {
		return dishFeaturesOptionDao;
	}

}