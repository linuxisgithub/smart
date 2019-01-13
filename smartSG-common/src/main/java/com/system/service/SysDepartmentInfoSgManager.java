package com.system.service;

import com.sg.model.*;
import com.sg.service.*;
import com.system.dao.SysDepartmentInfoSgDao;
import com.system.model.SysDepartmentInfoSg;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-11 16:19:06
 *
 */
@Service
public class SysDepartmentInfoSgManager extends BaseManager<SysDepartmentInfoSg> {

	@Inject
	private SysDepartmentInfoSgDao sysDepartmentInfoSgDao;
	@Inject
	private SgFieldManager sgFieldManager;
	@Inject
	private ImgManager imgManager;
	@Inject
	private DishGroupManager dishGroupManager;
	@Inject
	private DishInfoManager dishInfoManager;
	@Inject
	private DishFeaturesManager dishFeaturesManager;
	@Inject
	private SetMealManager setMealManager;
    @Inject
    private DishFeaturesOptionManager dishFeaturesOptionManager;
	@Override
	protected BaseMybatis3Dao<SysDepartmentInfoSg> getDao() {
		return sysDepartmentInfoSgDao;
	}

	@Transactional
	public void doSave(SysDepartmentInfoSg infoSg){
		sysDepartmentInfoSgDao.update(infoSg);
		List<SgField> sgFieldList = infoSg.getSgFieldList();
		if (!sgFieldList.isEmpty()){
			for (SgField sgField : sgFieldList) {
				sgFieldManager.update(sgField);
			}
		}
	}

	@Transactional
	public void doApply(Long currentId,String[] deptIds,String type){
		if (type.equals("dish")){ // 注意对象克隆  浅克隆：对象实现Cloneable接口 重写clone方法
		    // copy菜品分组
			List<DishGroup> dishGroups = dishGroupManager.findByBid(currentId);
			// 保存菜品信息对应绑定的分组
            Map<Long,Long> groupMap = new HashMap<>();
			if(!dishGroups.isEmpty()){
                for (DishGroup dishgroup:dishGroups) {
                    DishGroup nowDishgroup = dishgroup.clone();
                    for (String depyId:deptIds) {
                        nowDishgroup.setDeptId(Long.parseLong(depyId));
                        dishGroupManager.save(nowDishgroup);
                        groupMap.put(dishgroup.getEid(),nowDishgroup.getEid());
                    }
                }
            }
			//copy套餐设置
            List<SetMeal> setMeals = setMealManager.findByBid(currentId);
			if (!setMeals.isEmpty()){
			    SetMeal setMeal = setMeals.get(0);
                for (String depyId:deptIds) {
                    setMeal.setBid(Long.parseLong(depyId));
                    setMealManager.save(setMeal);
                }
            }
            // copy菜品信息
			List<DishInfo> dishInfos = dishInfoManager.findByBid(currentId);
			if (!dishInfos.isEmpty()){
                for (DishInfo dishInfo:dishInfos){
                    DishInfo old = dishInfo;
                    DishInfo nw = dishInfo.clone();
                    for (String depyId:deptIds) {
                        nw.setDeptId(Long.parseLong(depyId));
                        //if (groupMap.containsKey(depyId)){
                            nw.setGroupId(groupMap.get(nw.getGroupId()));
                        //}
                        dishInfoManager.save(nw);
                        // copy菜品图片
                        List<Img> imgs = imgManager.findByBidAndDtype(old.getEid(),3);
                        if (!imgs.isEmpty()){
                            for (Img img:imgs){
                                img.setBid(nw.getEid());
                                imgManager.save(img);
                            }
                        }
                        // copy菜品特性
                        List<DishFeatures> dishFeaturesList = dishFeaturesManager.findByBidAndType(old.getEid(),2);
                        if (!dishFeaturesList.isEmpty()){
                            for (DishFeatures dishFeatures : dishFeaturesList){
                                DishFeatures nowDishFeature = dishFeatures.clone();
                                nowDishFeature.setBid(nw.getEid());
                                dishFeaturesManager.save(nowDishFeature);
                                //特性选项数据
                                List<DishFeaturesOption> dishFeaturesOptions = dishFeaturesOptionManager.findByBid(dishFeatures.getEid());
                                if (!dishFeaturesOptions.isEmpty()){
                                    for (DishFeaturesOption dishFeaturesOption : dishFeaturesOptions){
                                        dishFeaturesOption.setBid(nowDishFeature.getEid());
                                        dishFeaturesOptionManager.save(dishFeaturesOption);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // copy公共特性
			List<DishFeatures> dishFeaturesList = dishFeaturesManager.findByBidAndType(currentId,1);
            if (!dishFeaturesList.isEmpty()){
                for (DishFeatures dishFeatures:dishFeaturesList){
                    DishFeatures nw = dishFeatures.clone();
                    // 公共特性选项数据
                    List<DishFeaturesOption> dishFeaturesOptions = dishFeaturesOptionManager.findByBid(dishFeatures.getEid());
                    for (String depyId:deptIds) {
                        nw.setBid(Long.parseLong(depyId));
                        dishFeaturesManager.save(nw);
                        if (!dishFeaturesOptions.isEmpty()){
                            for (DishFeaturesOption dishFeaturesOption : dishFeaturesOptions){
                                dishFeaturesOption.setBid(nw.getEid());
                                dishFeaturesOptionManager.save(dishFeaturesOption);
                            }
                        }
                    }
                }
            }
		}else{
			// 复制应用食阁字段
			List<SgField> sgFields = sgFieldManager.findByBid(currentId);
			for (SgField sgField: sgFields) {
				for (String depyId:deptIds) {
					sgField.setBid(Long.parseLong(depyId));
					sgFieldManager.save(sgField);
				}
			}
			/*// 复制应用食阁图片
			List<Img> imgs = imgManager.findByBid(currentSgId);
			for (Img img: imgs) {
				for (String depyId:deptIds) {
					img.setBid(Long.parseLong(depyId));
					imgManager.save(img);
				}
			}*/
		}
	}

}