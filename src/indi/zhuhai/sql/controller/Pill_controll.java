package indi.zhuhai.sql.controller;

import java.util.Map;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Pill_data;
import indi.zhuhai.sql.dataenum.Pill_enum;

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
		Pill_data data = new Pill_data();
		String[] params = data.getFiledName();
		String sql = "select * from pill where Type = '" + pill_enum + "'";
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Pill_data(result);
		
		return data;
	}
}
