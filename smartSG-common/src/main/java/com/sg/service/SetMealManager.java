package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.SetMealDao;
import com.sg.model.SetMeal;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2019-01-04 14:30:28
 *
 */
@Service
public class SetMealManager extends BaseManager<SetMeal> {

	@Inject
	private SetMealDao setMealDao;
	@Override
	protected BaseMybatis3Dao<SetMeal> getDao() {
		return setMealDao;
	}

}