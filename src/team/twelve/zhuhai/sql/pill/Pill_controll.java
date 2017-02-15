package team.twelve.zhuhai.sql.pill;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import team.twelve.zhuhai.sql.JDBC;

/**
 * 药丸
 * @author zhuyst
 * 对Pill表的控制类
 */

public class Pill_controll {
	private JDBC mysql;
	
	public Pill_controll(JDBC jdbc) {
		this.mysql = jdbc;
	}
	
	public Pill_data get_Pill_data(Pill_enum pill_enum){
		Pill_data data = null;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Health,Price from pill where Type = '" + pill_enum + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			int health = rs.getInt(1);
			int price = rs.getInt(2);
			data = new Pill_data(pill_enum.toString(), health,price);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
