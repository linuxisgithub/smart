package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.SgFieldDao;
import com.sg.model.SgField;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-22 11:32:30
 *
 */
@Service
public class SgFieldManager extends BaseManager<SgField> {

	@Inject
	private SgFieldDao sgFieldDao;
	@Override
	protected BaseMybatis3Dao<SgField> getDao() {
		return sgFieldDao;
	}

}