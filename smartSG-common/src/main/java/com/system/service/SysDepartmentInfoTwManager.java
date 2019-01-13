package com.system.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.system.dao.SysDepartmentInfoTwDao;
import com.system.model.SysDepartmentInfoTw;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:23:22
 *
 */
@Service
public class SysDepartmentInfoTwManager extends BaseManager<SysDepartmentInfoTw> {

	@Inject
	private SysDepartmentInfoTwDao sysDepartmentInfoTwDao;
	@Override
	protected BaseMybatis3Dao<SysDepartmentInfoTw> getDao() {
		return sysDepartmentInfoTwDao;
	}

}