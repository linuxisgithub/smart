package com.system.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.system.dao.SysDepartmentInfoSgyyzDao;
import com.system.model.SysDepartmentInfoSgyyz;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:56
 *
 */
@Service
public class SysDepartmentInfoSgyyzManager extends BaseManager<SysDepartmentInfoSgyyz> {

	@Inject
	private SysDepartmentInfoSgyyzDao sysDepartmentInfoSgyyzDao;
	@Override
	protected BaseMybatis3Dao<SysDepartmentInfoSgyyz> getDao() {
		return sysDepartmentInfoSgyyzDao;
	}

}