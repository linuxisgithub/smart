package com.sg.service;

import com.sg.dao.DishFeaturesDao;
import com.sg.model.DishFeatures;
import com.sg.model.DishFeaturesOption;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2019-01-04 15:58:38
 *
 */
@Service
public class DishFeaturesManager extends BaseManager<DishFeatures> {

	@Inject
	private DishFeaturesDao dishFeaturesDao;
	@Inject
	private DishFeaturesOptionManager dishFeaturesOptionManager;
	@Override
	protected BaseMybatis3Dao<DishFeatures> getDao() {
		return dishFeaturesDao;
	}

	@Transactional
	public void doSave(DishFeatures dishFeatures){
		if (dishFeatures.getEid()==null){
			dishFeaturesDao.save(dishFeatures);
		}else{
			dishFeaturesDao.update(dishFeatures);
			List<DishFeaturesOption> options_update = dishFeatures.getOptions_update();
			if(!options_update.isEmpty()){
				for (DishFeaturesOption option:options_update) {
					dishFeaturesOptionManager.update(option);
				}
			}
		}
		List<DishFeaturesOption> options_insert = dishFeatures.getOptions_insert();
		if(!options_insert.isEmpty()){
			Long bid = dishFeatures.getEid();
			for (DishFeaturesOption option:options_insert) {
				option.setBid(bid);
				dishFeaturesOptionManager.save(option);
			}
		}
	}

	public List<DishFeatures> findByBidAndType(long bid,Integer type) {
		return dishFeaturesDao.findByBidAndType(bid,type);
	}

	public List<DishFeatures> handleDishFeaturesList(List<DishFeatures> dishFeaturesList) {
		if (!dishFeaturesList.isEmpty()){
			for (DishFeatures df : dishFeaturesList) {
				List<DishFeaturesOption> dfos = dishFeaturesOptionManager.findByBid(df.getEid());
				df.setOptionNumber(dfos.size());
				String str = "";
				if (!dfos.isEmpty()){
					if (df.getOptionType() == 1){
						for(DishFeaturesOption dfo:dfos){
							if (dfo.getIsDefault()==1){
								str = dfo.getName();
							}
						}
					}else{
						int i = 0;
						for(DishFeaturesOption dfo:dfos){
							if (dfo.getIsDefault()==1){
								if (i>0){
									str+=",";
								}
								str += dfo.getName();
								i++;
							}
						}
					}
				}
				df.setOptionStr(str);
			}
		}
		return dishFeaturesList;
	}

}