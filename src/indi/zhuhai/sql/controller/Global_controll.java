package indi.zhuhai.sql.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.dataenum.Global_enum;

/**
 * 全局变量
 * @author zhuyst
 * 对Global表的控制类
 */

public class Global_controll{
	private JDBC mysql;
	
	public Global_controll(JDBC jdbc){
		this.mysql = jdbc;
	}
	
	//获取数据
	public int get_data(Global_enum global_enum){
		int number = 0;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select " + global_enum + " from global";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			number = rs.getInt(1);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	//更改数据
	public void update_data(Global_enum global_enum,String data){
		PreparedStatement pst;
		String sql = "update global set " + global_enum + "='" + data + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
