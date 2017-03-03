package indi.zhuhai.sql.controller;

import java.util.Map;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Item_data;

/**
 * ��Ʒ
 * @author zhuyst
 * ��Item��Ŀ�����
 * ���������Ʒ�ļ۸��Լ����۸�ָ�Ϊ��ʼ�۸�
 */

public class Item_controll{
	private JDBC mysql;
	
	public Item_controll(JDBC jdbc) {
		this.mysql = jdbc;
	}
	
	//��ȡ��Ʒ����Ϣ
	public Item_data get_Item_data(int ID){
		Item_data data = new Item_data();
		String[] params = data.getFiledName();
		String sql = "select * from item where ID = '" + ID + "'";
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Item_data(result);
		
		return data;
	}
	
	//������Ʒ�ļ۸�
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
	
	//ʹ��Ʒ�ļ۸�ָ�����ʼ�۸�
	public void update_Item_start_price(int ID){
		Item_data data = this.get_Item_data(ID);
		int start_price = data.getStart_price();
		String sql = "update item set Price='" + start_price + "' where ID = '" + ID + "'";
		mysql.update(sql);
	}
	
}
