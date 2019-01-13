package com.sg.dao;
import org.springframework.stereotype.Repository;

import com.sg.model.Suggest;

import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-17 13:57:00
 *
 */
@Repository
public class SuggestDao extends BaseMybatis3Dao<Suggest> {

	public String findSendUserImAccount(Long bid) {
		return sqlSessionTemplate.selectOne(generateStatement("findSendUserImAccount"), bid);
	}

}