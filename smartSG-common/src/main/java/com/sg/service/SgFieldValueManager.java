package com.sg.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.sg.dao.SgFieldValueDao;
import com.sg.model.SgFieldValue;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-24 08:46:22
 *
 */
@Service
public class SgFieldValueManager extends BaseManager<SgFieldValue> {

	@Inject
	private SgFieldValueDao sgFieldValueDao;
	@Override
	protected BaseMybatis3Dao<SgFieldValue> getDao() {
		return sgFieldValueDao;
	}

}