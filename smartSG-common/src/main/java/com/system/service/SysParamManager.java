package com.system.service;

import com.system.dao.SysParamDao;
import com.system.model.SysParam;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.DataModul;
import javacommon.util.Paginator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author chaoxiang
 * @date 2017-06-30 15:16:35
 *
 */
@Service
public class SysParamManager extends BaseManager<SysParam> {

	@Inject
	private SysParamDao sysParamDao;
	@Override
	protected BaseMybatis3Dao<SysParam> getDao() {
		return sysParamDao;
	}
	public SysParam findByCode(String code) {
		return sysParamDao.findByCode(code);
	}
	public DataModul<SysParam> btajax(HttpServletRequest request) {
		int limit = Integer.parseInt(request.getParameter("limit"));
		int offset = Integer.parseInt(request.getParameter("offset")) + 1;
		int currentPage = offset/limit + 1;
		String search = request.getParameter("search");
		Paginator<SysParam> paginator = new Paginator<>(currentPage, limit);
//		String fliter_sql = request.getParameter("fliter_sql");
		if(search != null && search != "") {
			search = searchSql(search);
			Map<String, Object> criteria = paginator.getCriteria();
			criteria.put("search", search);
		}
		sysParamDao.findPage(paginator);
		DataModul<SysParam> dataModul = new DataModul<>(paginator.getTotal(), paginator.getDataList());
		return dataModul;
	}

	private String searchSql(String search) {
		String sql = " where 1 = 1 ";
		String[] params = search.split("&");
		for (String param : params) {
			param = param.trim();
			if(param.length() > 0) {
				param = param.trim().replaceAll("-flag-", "");
				sql += " and " + param + " ";
			}
		}
		return sql;
	}
}