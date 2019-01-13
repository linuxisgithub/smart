package javacommon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Paginator
 * @Description 分页器
 * @author gqjiang
 * @date 2014年10月20日下午4:35:17
 */
public class Paginator<T> {

	public static final int DEFAULT_PAGESIZE = 15;

	public static final int DEFAULT_PAGESIZE_BS = 10;

	public static final int DEFAULT_CURRENTPAGE = 1;

	private int pageSize;

	private int currentPage;

	private long total;

	private List<T> dataList; // 数据列表
	private List<Map<String, Object>> mapDataList; // Map结构的数据列表
	private Map<String, Object> otherData;// 其他的数据
	private Map<String, Object> criteria; // 查询条件

	public Paginator() {
		this(DEFAULT_CURRENTPAGE);
	}

	public Paginator(int currentPage) {
		this(currentPage, DEFAULT_PAGESIZE);
	}

	public List<Map<String, Object>> getMapDataList() {
		if (this.mapDataList == null) {
			this.mapDataList = new ArrayList<Map<String, Object>>();
		}
		return mapDataList;
	}

	public void setMapDataList(List<Map<String, Object>> mapDataList) {
		this.mapDataList = mapDataList;
	}

	public Paginator(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getStart() {
		return pageSize * (currentPage - 1);
	}

	public long getEnd() {
		return pageSize * currentPage - 1;
	}

	public List<T> getDataList() {
		if (this.dataList == null) {
			this.dataList = new ArrayList<T>();
		}
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Map<String, Object> getOtherData() {
		return otherData;
	}

	public void setOtherData(Map<String, Object> otherData) {
		this.otherData = otherData;
	}

	public void setParam(String key, Object value) {
		if (criteria == null)
			criteria = new HashMap<String, Object>();
		criteria.put(key, value);
	}

	public void setCriteria(Map<String, Object> criteria) {
		this.criteria = criteria;
	}

	public Map<String, Object> getCriteria() {
		if (criteria == null) {
			criteria = new HashMap<String, Object>();
		}
		return criteria;
	}

	public void addOtherDate(String key, Object value) {
		if (otherData == null) {
			otherData = new HashMap<>();
		}
		otherData.put(key, value);
	}

	public long getPages() {
		if (pageSize == 1) {
			return total;
		}
		if (total <= 0) {
			return 1;
		} else if (total % pageSize == 0) {
			return total / pageSize;
		} else {
			return total / pageSize + 1;
		}
	}
}
