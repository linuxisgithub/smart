package com.system.dao;

import com.system.model.SysParam;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chaoxiang
 * @date 2017-06-30 15:16:35
 *
 */
@Repository
public class SysParamDao extends BaseMybatis3Dao<SysParam> {

	public SysParam findByCode(String code) {
		return sqlSessionTemplate.selectOne(generateStatement("findByCode"), code);
	}

}