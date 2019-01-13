package com.sg.dao;
import org.springframework.stereotype.Repository;

import com.sg.model.Report;

import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:58:03
 *
 */
@Repository
public class ReportDao extends BaseMybatis3Dao<Report> {

    public List<Map<String, Object>> findKindData(Long companyId, Integer kind) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyId", companyId);
        param.put("kind", kind);
        return sqlSessionTemplate.selectList(generateStatement("findKindData"), param);
    }

}