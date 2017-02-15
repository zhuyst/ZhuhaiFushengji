package team.twelve.zhuhai.sql;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * @author zhuyst
 * Æô¶¯JDBCÇý¶¯£¬Á´½ÓMYSQL
 */
public class JDBC {
	
	private static final String URL = "jdbc:mysql://localhost:3306/game?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String USERNAME = "game_admin";
	private static final String PASSWORD = "zhuyst";
	private Connection connection = null;
	
	public JDBC() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(URL,USERNAME,PASSWORD);
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
