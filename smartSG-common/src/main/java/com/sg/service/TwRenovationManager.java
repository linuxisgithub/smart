package com.sg.service;

import javax.inject.Inject;

import com.sg.model.SgField;
import org.springframework.stereotype.Service;

import com.sg.dao.TwRenovationDao;
import com.sg.model.TwRenovation;

import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author 超享
 * @date 2018-12-29 11:09:20
 *
 */
@Service
public class TwRenovationManager extends BaseManager<TwRenovation> {

	@Inject
	private TwRenovationDao twRenovationDao;
	@Inject
	private SgFieldManager sgFieldManager;
	@Override
	protected BaseMybatis3Dao<TwRenovation> getDao() {
		return twRenovationDao;
	}

	@Transactional
	public void doSave(TwRenovation twRenovation){
		if (twRenovation.getEid()==null){
			twRenovationDao.save(twRenovation);
		}else{
			twRenovationDao.update(twRenovation);
		}
		List<SgField> fieldList = twRenovation.getFieldList();
		if (!fieldList.isEmpty()){
			for (SgField sgField : fieldList) {
				sgFieldManager.update(sgField);
			}
		}
	}

}