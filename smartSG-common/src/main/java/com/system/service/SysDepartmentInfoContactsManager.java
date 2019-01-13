package com.system.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.system.dao.SysDepartmentInfoContactsDao;
import com.system.model.SysDepartmentInfoContacts;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:18:13
 *
 */
@Service
public class SysDepartmentInfoContactsManager extends BaseManager<SysDepartmentInfoContacts> {

	@Inject
	private SysDepartmentInfoContactsDao sysDepartmentInfoContactsDao;
	@Override
	protected BaseMybatis3Dao<SysDepartmentInfoContacts> getDao() {
		return sysDepartmentInfoContactsDao;
	}

}