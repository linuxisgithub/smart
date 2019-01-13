package org.demo.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * jdbc获取连接工具类
 * @ClassName: JdbcUtil
 * @Description: TODO
 * @author duwufeng
 * @date 2015年7月27日 上午11:02:30
 */
public class JdbcUtil {
	/** mysql */
	public static final String MYSQL = "mysql";
	/** oracle */
	public static final String ORACLE = "oracle";
	/**
	 * 根据数据库类型获取数据库连接
	 * @author duwufeng
	 * @update
	 * @param type (mysql | oracle)
	 * @return
	 */
	public static Connection getConnection(String type) {
		if(MYSQL.equals(type)) {
			return getMySqlConnection();
		} else if(ORACLE.equals(type)) {
			return getMySqlConnection();
		}
		return null;
	}
	/**
	 * 获取数据库连接
	 * @author duwufeng
	 * @update
	 * @return
	 */
	private static Connection getMySqlConnection() {
		Connection conn = null;
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://mairadb.chinacloudapp.cn:3306/smartSG?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;relaxAutoCommit=true&amp;allowMultiQueries=true";
		String userName = "root";
		String password = "dbpass";
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e1) {
		} catch (SQLException e) {
		}
		return conn;
	}
	/**
	 * 关闭资源
	 * @author duwufeng
	 * @update
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
        }
        try {
            if(stmt != null) {
            	stmt.close();
            }
        } catch (SQLException e) {
        }
        try {
            if(conn != null) {
            	conn.close();
            }
        } catch (SQLException e) {
        }
    }
	private static Connection getConn() {
		return getConnection(JdbcUtil.MYSQL);
	}
	public static void main(String[] args) throws SQLException {
		//System.out.println(JdbcUtil.getConnection(JdbcUtil.MYSQL));
		Connection conn = JdbcUtil.getConn();
		System.out.println(conn);
	}
}
