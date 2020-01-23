package library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	// 贾琏欲执事
	private final static String className = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/library";
	private final static String USER = "root";
	private final static String PASSWORD = "lqx2877354362";
	public static Connection con;
	public static PreparedStatement ps;
	public static Statement st;
	public static ResultSet rs;
	// 加载内省机制
	static {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 创建连接对象
	public static Connection getConnection() {
		try {
			return con = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 创建执行语句
	 * 
	 * @param sql 语句
	 * @param val 传入?的值
	 * @return
	 */
	// 创建语句
	public static Statement createPreparedStatement(String sql, Object... val) {
		try {
			if (val != null) {
				ps = getConnection().prepareStatement(sql);
				for (int i = 0; i < val.length; i++) {
					ps.setObject(i + 1, val[i]);
				}
				return ps;
			} else {
				st = getConnection().createStatement();
				return st;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通用的增删改 同时返回是否执行成功
	 * 
	 * @param sql
	 * @param val
	 * @return
	 */
	// 执行语句
	public static boolean executeUpdate(String sql, Object... val) {
		try {
			if (val != null) {
				ps = (PreparedStatement) createPreparedStatement(sql, val);
				return ps.executeUpdate() > 0 ? true : false;
			} else {
				st = createPreparedStatement(sql);
				return st.executeUpdate(sql) > 0 ? true : false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 创建查询的返回结果集
	 * 
	 * @param sql
	 * @param val
	 * @return
	 */
	public static ResultSet createResultSet(String sql, Object... val) {
		try {
			if (val != null) {
				ps = (PreparedStatement) createPreparedStatement(sql, val);
				return ps.executeQuery();
			} else {
				st = createPreparedStatement(sql);
				return st.executeQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 关闭资源
	 * @param rs
	 * @param st
	 * @param con
	 */
	// 关闭资源
	public static void closeAll(ResultSet rs, Statement st, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}