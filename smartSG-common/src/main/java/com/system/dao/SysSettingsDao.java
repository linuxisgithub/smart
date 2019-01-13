package com.system.dao;

import com.system.model.SysSettings;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author duwufeng
 * @date 2017-07-14 15:29:37
 *
 */
@Repository
public class SysSettingsDao extends BaseMybatis3Dao<SysSettings> {

	public long getConfirmNum(Long companyId, List<String> idIds) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", companyId);
		param.put("idIds", idIds);
		return sqlSessionTemplate.selectOne(generateStatement("getConfirmNum"), param);
	}

	public boolean hasMenuConfirm(Long companyId, Long confirmId, String code) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", companyId);
		param.put("bid", confirmId);
		param.put("code", code);
		int num = sqlSessionTemplate.selectOne(generateStatement("hasMenuConfirm"), param);
		if(num > 0) {
			return true;
		}
		return false;
	}

}