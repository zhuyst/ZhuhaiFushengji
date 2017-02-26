package indi.zhuhai.sql.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Item_data;

/**
 * 物品
 * @author zhuyst
 * 对Item表的控制类
 * 负责更改物品的价格以及将价格恢复为初始价格
 */

public class Item_controll{
	private JDBC mysql;
	
	public Item_controll(JDBC jdbc) {
		this.mysql = jdbc;
	}
	
	//获取物品的信息
	public Item_data get_Item_data(int ID){
		Item_data data = null;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Name,Introduce,Price,Start_Price,Effect_handle,Effect_number from item where ID = '" + ID + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			String name = rs.getString(1);
			String introduce = rs.getString(2);
			int price = rs.getInt(3);
			int start_price = rs.getInt(4);
			char effect_handle = rs.getString(5).charAt(0);
			int effect_number = rs.getInt(6);
			data = new Item_data(ID, name, introduce,price,start_price,effect_handle,effect_number);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	//更新物品的价格
	public void update_Item_new_price(int ID,char handle,int number){
		PreparedStatement pst;
		Item_data data = this.get_Item_data(ID);
		int price = data.getPrice();
		switch (handle) {
		case '+':
			price += number;
			break;
		case '-':
			price -= number;
			break;
		case '*':
			price *= number;
			break;
		case '/':
			price /= number;
			break;
		}
		String sql = "update item set Price='" + price + "' where ID = '" + ID + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//使物品的价格恢复到初始价格
	public void update_Item_start_price(int ID){
		PreparedStatement pst;
		Item_data data = this.get_Item_data(ID);
		int start_price = data.getStart_price();
		String sql = "update item set Price='" + start_price + "' where ID = '" + ID + "'";
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
