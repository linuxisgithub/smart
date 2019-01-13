package com.system.dao;

import com.system.model.SysDictionary;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author chaoxiang
 * @date 2017-07-03 10:39:00
 *
 */
@Repository
public class SysDictionaryDao extends BaseMybatis3Dao<SysDictionary> {

	public List<Map<String, Object>> findAllForMap() {
		return sqlSessionTemplate
				.selectList(generateStatement("findAllForMap"));
	}

	public List<SysDictionary> findDictionaryList(String code) {
		return sqlSessionTemplate
				.selectList(generateStatement("findDictionaryList"),code);
	}

	public List findLogisticsList() {
		return sqlSessionTemplate
				.selectList(generateStatement("findLogisticsList"));
	}

}