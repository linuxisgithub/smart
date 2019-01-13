package javacommon.base;

import javacommon.util.Paginator;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy
 * @since 2016/4/10
 */
public abstract class BaseMybatis3Dao<T> {

	public static final String MAPPER_SAVE = "insert";
	
	public static final String MAPPER_SAVELIST = "insertList";

	public static final String MAPPER_INITSAVE = "initInsert";

	public static final String MAPPER_DELETE = "delete";

	public static final String MAPPER_IS_DELETE = "isDelete";

	public static final String MAPPER_UPDATE = "update";

	public static final String MAPPER_GET = "findById";

	public static final String MAPPER_GET_BID = "findByBid";

	public static final String MAPPER_ALL_COUNT = "findAllCount";

	public static final String MAPPER_WITH_FILE = "findByIdWithFile";

	public static final String MAPPER_WITH_CC = "findByIdWithCc";

	public static final String MAPPER_WITH_FILE_AND_CC = "findByIdWithFileAndCc";

	public static final String MAPPER_WITH_FILE_MAP = "findByIdWithFileMap";

	public static final String MAPPER_WITH_CC_MAP = "findByIdWithCcMap";

	public static final String MAPPER_WITH_FILE_AND_CC_MAP = "findByIdWithFileAndCcMap";

	public static final String MAPPER_TOTAL_COUNT = "findTotalCount";

	public static final String MAPPER_FINDLIST = "findList";

	public static final String MAPPER_FINDALL = "findAll";

	public static final String MAPPER_FINDPAGEDLIST = "findPagedList";

	public static final String MAPPER_FINDPAGECOUNT = "findPagedCount";

	@Inject
	protected SqlSessionTemplate sqlSessionTemplate;

	protected String generateStatement(String sql) {
		return this.getClass().getSimpleName() + "." + sql;
	}

	public T get(long id) {
		return sqlSessionTemplate.selectOne(generateStatement(MAPPER_GET), id);
	}

	public List<T> findByBid(long bid) {
		return sqlSessionTemplate.selectList(generateStatement(MAPPER_GET_BID), bid);
	}

	public T getWithCc(long id) {
		return sqlSessionTemplate.selectOne(generateStatement(MAPPER_WITH_CC),
				id);
	}

	public T getWithFile(Long eid) {
		return sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_WITH_FILE), eid);
	}

	public T getWithFileAndCc(Long eid) {
		return sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_WITH_FILE_AND_CC), eid);
	}

	public Map<String, Object> getWithCcMap(Long eid) {
		return sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_WITH_CC_MAP), eid);
	}

	public Map<String, Object> getWithFileMap(Long eid) {
		return sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_WITH_FILE_MAP), eid);
	}

	public Map<String, Object> getWithFileAndCcMap(Long eid) {
		return sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_WITH_FILE_AND_CC_MAP), eid);
	}

	public void save(T t) {
		sqlSessionTemplate.insert(generateStatement(MAPPER_SAVE), t);
	}
	
	public void saveList(List<T> list) {
		sqlSessionTemplate.insert(generateStatement(MAPPER_SAVELIST), list);
	}

	public void saveInitInsert(List<T> list) {
		sqlSessionTemplate.insert(generateStatement(MAPPER_INITSAVE), list);
	}

	public void update(T t) {
		sqlSessionTemplate.update(generateStatement(MAPPER_UPDATE), t);
	}

	public void delete(long id) {
		sqlSessionTemplate.delete(generateStatement(MAPPER_DELETE), id);
	}

	public void isDelete(long companyId, long eid) {
		Map<String, Object> param = new HashMap<>();
		param.put("companyId", companyId);
		param.put("eid", eid);
		sqlSessionTemplate.delete(generateStatement(MAPPER_IS_DELETE), param);
	}

	public List<T> findAll() {
		return sqlSessionTemplate.selectList(generateStatement(MAPPER_FINDALL));
	}

	public List<T> findList(Map<String, Object> param) {
		List<T> list = sqlSessionTemplate.selectList(
				generateStatement(MAPPER_FINDLIST), param);
		return list;
	}

	public void findPage(Paginator<T> paginator) {
		long count = sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_FINDPAGECOUNT), paginator);
		paginator.setTotal(count);
		if (count > 0) {
			int offset = (paginator.getCurrentPage() - 1)
					* paginator.getPageSize();
			List<T> list = sqlSessionTemplate.selectList(
					generateStatement(MAPPER_FINDPAGEDLIST), paginator,
					new RowBounds(offset, paginator.getPageSize()));
			paginator.setDataList(list);
		}
	}

	public void findPage(Paginator<T> paginator, String sqlid) {
		String countSql = sqlid + "Count"; // findMonthCount
		String listSql = sqlid + "List"; // findMonthList
		long count = sqlSessionTemplate.selectOne(generateStatement(countSql),
				paginator);
		paginator.setTotal(count);
		if (count > 0) {
			int offset = (paginator.getCurrentPage() - 1)
					* paginator.getPageSize();
			List<T> list = sqlSessionTemplate.selectList(
					generateStatement(listSql), paginator, new RowBounds(
							offset, paginator.getPageSize()));
			paginator.setDataList(list);
		}
	}

	public long getTotalCount(Map<String, Object> param) {
		long count = sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_TOTAL_COUNT), param);
		return count;
	}

	public long findAllCount(Long userId) {
		long count = sqlSessionTemplate.selectOne(
				generateStatement(MAPPER_ALL_COUNT), userId);
		return count;
	}

	public Map<String, Object> findMapById(Long eid) {
		return sqlSessionTemplate.selectOne(generateStatement("findMapById"),
				eid);
	}

	public void findPageMap(Paginator<Map<String, Object>> paginator,
                            String sqlid) {
		String countSql = sqlid + "Count"; // findMonthCount
		String listSql = sqlid + "List"; // findMonthList
		long count = sqlSessionTemplate.selectOne(generateStatement(countSql),
				paginator);
		paginator.setTotal(count);
		if (count > 0) {
			int offset = (paginator.getCurrentPage() - 1)
					* paginator.getPageSize();
			List<Map<String, Object>> list = sqlSessionTemplate.selectList(
					generateStatement(listSql), paginator, new RowBounds(
							offset, paginator.getPageSize()));
			paginator.setMapDataList(list);
		}
	}
}
