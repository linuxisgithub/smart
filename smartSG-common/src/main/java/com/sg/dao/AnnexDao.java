package com.sg.dao;

import com.sg.model.Annex;
import javacommon.base.BaseModel;
import javacommon.base.BaseMybatis3Dao;
import javacommon.util.Paginator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy
 * @since 2016/10/14 0014.
 */
@Repository
public class AnnexDao extends BaseMybatis3Dao<BaseModel> {

	public void save(List<Annex> list) {
		sqlSessionTemplate.insert(generateStatement(MAPPER_SAVE), list);
	}

	/**
	 * 获取附件列表
	 * 
	 * @param bid
	 *            业务ID
	 * @param btype
	 *            业务类型
	 * @return
	 */
	public List<Map<String, Object>> findAnnex(Long bid, Integer btype) {
		Map<String, Object> param = new HashMap<>();
		param.put("bid", bid);
		param.put("btype", btype);
		return sqlSessionTemplate.selectList(generateStatement(MAPPER_FINDLIST), param);
	}

	/**
	 * 删除附件
	 * 
	 * @param bid
	 *            业务ID
	 * @param btype
	 *            业务类型
	 */
	public void delete(Long bid, Integer btype) {
		Map<String, Object> param = new HashMap<>();
		param.put("bid", bid);
		param.put("btype", btype);
		sqlSessionTemplate.delete(generateStatement(MAPPER_DELETE), param);
	}

	/**
	 * 根据主表ID删除附件
	 * 
	 * @param pid
	 *            主表ID
	 * @param btypes
	 *            主/从类型数组
	 */
	public void deleteByPid(Long pid, int... btypes) {
		Map<String, Object> param = new HashMap<>();
		param.put("pid", pid);
		param.put("btypes", btypes);
		sqlSessionTemplate.delete(generateStatement("deleteByPid"), param);
	}

	public List<String> findNameListByBid(Long bid, Integer btype) {
		Map<String, Object> param = new HashMap<>();
		param.put("bid", bid);
		param.put("btype", btype);
		return sqlSessionTemplate.selectList(generateStatement("findNameListByBid"), param);
	}

	public List<String> findNameListByPid(Long pid, int... btypes) {
		Map<String, Object> param = new HashMap<>();
		param.put("pid", pid);
		param.put("btypes", btypes);
		return sqlSessionTemplate.selectList(generateStatement("findNameListByPid"), param);
	}

	public List<Map<String, Object>> findFileCountByType(Map<String, Object> requestParam) {
		return sqlSessionTemplate.selectList(generateStatement("findFileCountByType"), requestParam);
	}

	public Map<String, Object> findFileCount(Map<String, Object> requestParam) {
		return sqlSessionTemplate.selectOne(generateStatement("findFileCount"), requestParam);
	}

	public void findPageFileByType(Paginator<BaseModel> paginator) {
		super.findPage(paginator, "findPageFileByType");
	}

	public void saveBatch(Map<String, Object> map) {
		sqlSessionTemplate.insert(generateStatement("saveBatch"), map);
	}

	public List<String> findAllName() {
		return sqlSessionTemplate.selectList(generateStatement("findAllName"));
	}

	public void deleteAll() {
		sqlSessionTemplate.delete(generateStatement("deleteAll"));
	}

	public List<Map<String, Object>> getUpdateFiles(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("findList"), param);
	}

	public List<Map<String, Object>> getUpdateFilesMarket(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("findListMarket"), param);
	}

	public List<String> findNameList(Map<String, Object> param) {
		return sqlSessionTemplate.selectList(generateStatement("findNameList"), param);
	}

	public List<String> findNameByEids(String[] annexarry) {
		return sqlSessionTemplate.selectList(generateStatement("findNameByEids"), annexarry);
	}

	public void deleteByEids(String[] eidArry) {
		sqlSessionTemplate.delete(generateStatement("deleteByEids"), eidArry);
	}

	public double countCompanyFileSize(Long companyId) {
		return sqlSessionTemplate.selectOne(generateStatement("countCompanyFileSize"), companyId);
	}

	public void addDownCount(Long eid) {
		sqlSessionTemplate.update(generateStatement("addDownCount"), eid);

	}

}
