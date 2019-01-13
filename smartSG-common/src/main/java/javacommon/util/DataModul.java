package javacommon.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装bootstrap table返回的数据
 * @ClassName: DataModul 
 * @Description:TODO  
 * @author WF 
 * @date 2015年10月21日 下午10:52:47 
 * 
 * @param <T>
 */
public class DataModul<T> {
	private long total;
	private List<T> rows = new ArrayList<T>();
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public DataModul(long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	
}
