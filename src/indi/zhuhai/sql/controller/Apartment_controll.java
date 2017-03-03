package indi.zhuhai.sql.controller;

import java.util.Map;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Apartment_data;
import indi.zhuhai.sql.dataenum.Apartment_enum;

/**
 * 出租屋
 * @author zhuyst
 * 对Apartment表的控制类
 *
 */

public class Apartment_controll {
	private JDBC mysql;
	
	public Apartment_controll(JDBC jdbc) {
		this.mysql = jdbc;
	}
	
	public Apartment_data get_Apartment_data(Apartment_enum apartment_enum){
		Apartment_data data = new Apartment_data();
		String[] params = data.getFiledName();
		String sql = "select * from apartment where Type = '" + apartment_enum + "'";
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Apartment_data(result);
		
		return data;
	}
	
}
