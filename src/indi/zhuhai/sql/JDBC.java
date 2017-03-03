package indi.zhuhai.sql;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * @author zhuyst
 * Æô¶¯JDBCÇý¶¯£¬Á´½ÓMYSQL
 */
public class JDBC {
	
	private static final String URL = "jdbc:mysql://localhost:3306/game?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String USERNAME = "game_admin";
	private static final String PASSWORD = "zhuyst";
	private Connection connection;
	
	public JDBC() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection)DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
		return connection;
	}
	
	public boolean update(String sql){
		Connection connection = null;
		PreparedStatement pst = null;
		
		try {
			connection = this.getConnection();
			pst = (PreparedStatement) connection.prepareStatement(sql);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			release(pst, null);
		}
		
	}
	
	public Map<String,String> select(String sql,String[] params){
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			pst = (PreparedStatement) connection.prepareStatement(sql);
			
			Map<String, String> final_result = new HashMap<String,String>();
			String[] result = new String[params.length];
			rs = pst.executeQuery();
			rs.next();
			
			for(int i = 0;i < params.length;i++){
				result[i] = rs.getString(params[i]);
				final_result.put(params[i], result[i]);
			}
			
			return final_result;
		} catch (SQLException e) {
			return null;
		} finally {
			release(pst, rs);
		}
	}
	
	public static void release(PreparedStatement pst,ResultSet rs){
		try {
			
			if(pst != null) pst.close();
			if(rs != null) rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
