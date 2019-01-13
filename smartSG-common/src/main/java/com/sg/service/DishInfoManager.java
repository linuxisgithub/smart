package com.sg.service;

import com.sg.dao.DishInfoDao;
import com.sg.model.DishInfo;
import com.sg.model.Img;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2019-01-03 14:50:14
 *
 */
@Service
public class DishInfoManager extends BaseManager<DishInfo> {

	@Inject
	private DishInfoDao dishInfoDao;
	@Inject
	private ImgManager imgManager;
	@Override
	protected BaseMybatis3Dao<DishInfo> getDao() {
		return dishInfoDao;
	}

	@Transactional
	public void doRemove(Long eid){
		DishInfo dishInfo = new DishInfo();
		dishInfo.setEid(eid);
		dishInfo.setIsDel(1);
		dishInfoDao.update(dishInfo);
		List<Img> imgs = imgManager.findByBidAndDtype(eid,3);
		for (Img img : imgs) {//删除菜品同时删除菜品图片
			imgManager.doDel(img.getEid(),img.getName());
		}
	}

}