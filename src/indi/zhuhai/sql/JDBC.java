package indi.zhuhai.sql;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * @author zhuyst
 * ����JDBC����������MYSQL
 */
public class JDBC {
	
	private String url = "jdbc:mysql://localhost:3306/game?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private String username = "game_admin";
	private String password = "zhuyst";
	private Connection connection = null;
	
	public JDBC() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void close(){
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}