package team.twelve.zhuhai.sql.apartment;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import team.twelve.zhuhai.sql.JDBC;

/**
 * ������
 * @author zhuyst
 * ��Apartment��Ŀ�����
 *
 */

public class Apartment_controll {
	private JDBC mysql;
	
	public Apartment_controll(JDBC jdbc) {
		this.mysql = jdbc;
	}
	
	public Apartment_data get_Apartment_data(Apartment_enum apartment_enum){
		Apartment_data data = null;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Spec,Price from apartment where Type = '" + apartment_enum + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			int spec = rs.getInt(1);
			int price = rs.getInt(2);
			data = new Apartment_data(apartment_enum.toString(), spec, price);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
}
