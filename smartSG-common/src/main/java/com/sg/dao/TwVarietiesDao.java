package com.sg.dao;
import org.springframework.stereotype.Repository;

import com.sg.model.TwVarieties;

import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-14 14:27:12
 *
 */
@Repository
public class TwVarietiesDao extends BaseMybatis3Dao<TwVarieties> {

    public List<Map<String, Object>> findVarietiesData(Long conpanyId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", conpanyId);
        return sqlSessionTemplate.selectList(generateStatement("findVarietiesData"), param);
    }

}