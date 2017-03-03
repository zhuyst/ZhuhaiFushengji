package indi.zhuhai.sql.controller;

import java.util.Map;

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
		Item_data data = new Item_data();
		String[] params = data.getFiledName();
		String sql = "select * from item where ID = '" + ID + "'";
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Item_data(result);
		
		return data;
	}
	
	//更新物品的价格
	public void update_Item_new_price(int ID,char handle,int number){
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
		mysql.update(sql);
	}
	
	//使物品的价格恢复到初始价格
	public void update_Item_start_price(int ID){
		Item_data data = this.get_Item_data(ID);
		int start_price = data.getStart_price();
		String sql = "update item set Price='" + start_price + "' where ID = '" + ID + "'";
		mysql.update(sql);
	}
	
}
