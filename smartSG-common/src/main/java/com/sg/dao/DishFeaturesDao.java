package com.sg.dao;

import com.sg.model.DishFeatures;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2019-01-04 15:58:38
 *
 */
@Repository
public class DishFeaturesDao extends BaseMybatis3Dao<DishFeatures> {

    public List<DishFeatures> findByBidAndType(long bid,Integer type) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("bid", bid);
        param.put("type", type);
        return sqlSessionTemplate.selectList(generateStatement("findByBidAndType"), param);
    }

}