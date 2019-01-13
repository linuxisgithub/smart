package com.sg.dao;
import org.springframework.stereotype.Repository;

import com.sg.model.ReportField;

import javacommon.base.BaseMybatis3Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-15 09:59:22
 *
 */
@Repository
public class ReportFieldDao extends BaseMybatis3Dao<ReportField> {
    public List<Long> findByReportId(Long reportId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("reportId", reportId);
        return sqlSessionTemplate.selectList(generateStatement("findByReportId"), param);
    }

    public List<Map<String,Object>> findFieldNameByReportId(Long reportId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("reportId", reportId);
        return sqlSessionTemplate.selectList(generateStatement("findFieldNameByReportId"), param);
    }

    public void delByReportId(Long reportId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("reportId", reportId);
        sqlSessionTemplate.delete(generateStatement("delByReportId"), param);
    }
}