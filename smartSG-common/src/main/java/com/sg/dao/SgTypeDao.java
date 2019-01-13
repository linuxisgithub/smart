package com.sg.dao;
import org.springframework.stereotype.Repository;

import com.sg.model.SgType;

import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-12 14:47:11
 *
 */
@Repository
public class SgTypeDao extends BaseMybatis3Dao<SgType> {


    public List<Map<String, Object>> findTypeData(Long conpanyId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", conpanyId);
        return sqlSessionTemplate.selectList(generateStatement("findTypeData"), param);
    }
}