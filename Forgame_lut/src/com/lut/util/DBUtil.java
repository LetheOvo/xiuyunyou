package com.lut.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class DBUtil {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/mydb";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	/**
	 * 获取连接对象
	 * @return
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("数据库连接失败",e);
		}
		return conn;
	}
	/**
	 * 释放资源方法
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public static void closeAll(ResultSet rs,PreparedStatement pstmt,Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt !=null) {
				pstmt.close();
			}
			if (conn !=null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置参数
	 * @param pstmt
	 * @param params
	 */
	public static void setPstmt(PreparedStatement pstmt,Object... params) {
		if (pstmt != null && params != null) {
			for (int i = 0; i < params.length; i++) {
				try {
					pstmt.setObject(i+1, params[i]);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 查询结果的转换方式
	 * @param rs
	 * @param cla
	 * @return
	 */
	public static Object convert(ResultSet rs,Class cla) {
		Object object = null;
			try {
				if (cla.getName().equals("java.lang.Object")) {
				return rs.getObject(1);
				}
				object = cla.newInstance();
				ResultSetMetaData metaData = rs.getMetaData();
				for (int i = 1; i < metaData.getColumnCount(); i++) {
					String name = metaData.getColumnLabel(i);
					BeanUtils.setProperty(object, name, rs.getObject(i));
				}
			} catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
				
				e.printStackTrace();
			}
		return object;
	}
	/**
	 * 通用增删改方法
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int myUpdate(String sql, Object... params) {
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			setPstmt(pstmt, params);
			n = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll(null, pstmt, conn);
		}
		return n;
		
	}
	/**
	 * 通用查询方法
	 * @param sql
	 * @param cla
	 * @param params
	 * @return
	 */
	public static List myQuery(String sql,Class cla, Object... params) {
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			setPstmt(pstmt, params);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Object obj = convert(rs, cla);
				list.add(obj);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
