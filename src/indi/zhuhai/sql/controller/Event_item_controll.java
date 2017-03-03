package indi.zhuhai.sql.controller;

import java.util.Map;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Event_item_data;

/**
 * 物价变动事件
 * @author zhuyst
 * 对Event_item表的控制类
 * 负责激活/取消物价变动事件
 */

public class Event_item_controll {
	private JDBC mysql;
	private Item_controll item_controll;
	
	public Event_item_controll(JDBC jdbc,Item_controll item_controll) {
		this.mysql = jdbc;
		this.item_controll = item_controll;
	}
	
	//获取关于物价变动的事件详情
	public Event_item_data get_Event_item_data(int ID){
		Event_item_data data = new Event_item_data();
		String[] params = data.getFiledName();
		String sql = "select * from event_item where ID = '" + ID + "'";
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Event_item_data(result);
		
		return data;
	}
	
	//激活关于物价变动的事件
	public void active(int ID){
		Event_item_data data = this.get_Event_item_data(ID);
		for(int i = 0;i < data.getEffect_item_ID().length;i++){
			item_controll.update_Item_new_price(data.getEffect_item_ID()[i], data.getEffect_handle(), data.getEffect_number());
		}
	}
	
	//关闭关于物价变动的事件
	public void passtive(int ID){
		Event_item_data data = this.get_Event_item_data(ID);
		for(int i = 0;i < data.getEffect_item_ID().length;i++){
			item_controll.update_Item_start_price(data.getEffect_item_ID()[i]);
		}
	}
}
