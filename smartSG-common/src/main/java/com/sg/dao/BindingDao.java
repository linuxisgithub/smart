package com.sg.dao;
import com.sg.model.Binding;
import org.springframework.stereotype.Repository;

import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-28 09:17:52
 *
 */
@Repository
public class BindingDao extends BaseMybatis3Dao<Binding> {

    public List<Map<String, Object>> findDeptByBid(Long bid, String type) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("bid", bid);
        param.put("type", type);
        return sqlSessionTemplate.selectList(generateStatement("findDeptByBid"), param);
    }

}