package indi.zhuhai.sql.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

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
		Event_item_data data = null;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Message,Effect_item_ID,Effect_handle,Effect_number from event_item where ID = '" + ID + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			String message = rs.getString(1);
			String effect_item_ID = rs.getString(2);
			char effect_handle = rs.getString(3).charAt(0);
			int effect_number = rs.getInt(4);
			data = new Event_item_data(ID, message, effect_item_ID, effect_handle, effect_number);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
