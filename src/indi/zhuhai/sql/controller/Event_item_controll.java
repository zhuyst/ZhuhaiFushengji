package indi.zhuhai.sql.controller;

import java.util.Map;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Event_item_data;

/**
 * ��۱䶯�¼�
 * @author zhuyst
 * ��Event_item��Ŀ�����
 * ���𼤻�/ȡ����۱䶯�¼�
 */

public class Event_item_controll {
	private JDBC mysql;
	private Item_controll item_controll;
	
	public Event_item_controll(JDBC jdbc,Item_controll item_controll) {
		this.mysql = jdbc;
		this.item_controll = item_controll;
	}
	
	//��ȡ������۱䶯���¼�����
	public Event_item_data get_Event_item_data(int ID){
		Event_item_data data = new Event_item_data();
		String[] params = data.getFiledName();
		String sql = "select * from event_item where ID = '" + ID + "'";
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Event_item_data(result);
		
		return data;
	}
	
	//���������۱䶯���¼�
	public void active(int ID){
		Event_item_data data = this.get_Event_item_data(ID);
		for(int i = 0;i < data.getEffect_item_ID().length;i++){
			item_controll.update_Item_new_price(data.getEffect_item_ID()[i], data.getEffect_handle(), data.getEffect_number());
		}
	}
	
	//�رչ�����۱䶯���¼�
	public void passtive(int ID){
		Event_item_data data = this.get_Event_item_data(ID);
		for(int i = 0;i < data.getEffect_item_ID().length;i++){
			item_controll.update_Item_start_price(data.getEffect_item_ID()[i]);
		}
	}
}
