package com.system.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.system.dao.SysDepartmentInfoSgglDao;
import com.system.model.SysDepartmentInfoSggl;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:34
 *
 */
@Service
public class SysDepartmentInfoSgglManager extends BaseManager<SysDepartmentInfoSggl> {

	@Inject
	private SysDepartmentInfoSgglDao sysDepartmentInfoSgglDao;
	@Override
	protected BaseMybatis3Dao<SysDepartmentInfoSggl> getDao() {
		return sysDepartmentInfoSgglDao;
	}

}