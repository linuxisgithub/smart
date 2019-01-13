package com.sg.dao;
import org.springframework.stereotype.Repository;

import com.sg.model.Field;

import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:57:33
 *
 */
@Repository
public class FieldDao extends BaseMybatis3Dao<Field> {

    public List<Map<String, Object>> findTypeData(Long conpanyId,Integer type) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", conpanyId);
        param.put("type", type);
        return sqlSessionTemplate.selectList(generateStatement("findTypeData"), param);
    }

}