package com.sg.dao;

import com.sg.model.DishGroup;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2019-01-03 11:02:37
 *
 */
@Repository
public class DishGroupDao extends BaseMybatis3Dao<DishGroup> {

    public long checkName(String name,Long bid) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", name);
        param.put("bid", bid);
        return sqlSessionTemplate.selectOne(generateStatement("checkName"), param);
    }

}