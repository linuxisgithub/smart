package com.sg.dao;

import com.sg.model.Img;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-25 13:49:07
 *
 */
@Repository
public class ImgDao extends BaseMybatis3Dao<Img> {


    public List<Img> findByName(String name) {
        return sqlSessionTemplate.selectList(generateStatement("findByName"), name);
    }

    public List<Img> findByBidAndDtype(long bid,Integer dtype) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("bid", bid);
        param.put("dtype", dtype);
        return sqlSessionTemplate.selectList(generateStatement("findByBidAndDtype"), param);
    }
}