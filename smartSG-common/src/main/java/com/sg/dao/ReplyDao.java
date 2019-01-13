package com.sg.dao;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sg.model.Reply;

import javacommon.base.BaseMybatis3Dao;

/**
 *
 * @author 超享
 * @date 2018-12-17 14:03:48
 *
 */
@Repository
public class ReplyDao extends BaseMybatis3Dao<Reply> {

	public Map<String, Object> findMapByEid(Long eid) {
		return sqlSessionTemplate.selectOne(generateStatement("findMapByEid"), eid);
	}

}