package indi.zhuhai.sql.controller;

import java.util.Map;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Event_health_data;
import indi.zhuhai.sql.dataenum.Player_enum;

/**
 * 健康受损事件
 * @author zhuyst
 * 对Event_health表的控制类
 * 负责激活健康受损事件
 */

public class Event_health_controll{
	private JDBC mysql;
	private Player_controll player_controll;
	
	public Event_health_controll(JDBC jdbc,Player_controll player_controll) {
		this.mysql = jdbc;
		this.player_controll = player_controll;
	}
	
	//获取关于健康受损的事件详情
	public Event_health_data get_Event_health_data(int ID){
		Event_health_data data = new Event_health_data();
		String[] params = data.getFiledName();
		String sql = "select * from event_health where ID = '" + ID + "'";
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Event_health_data(result);
		
		return data;
	}
	
	//激活关于健康受损的事件
	public void active(String player_name,int event_ID){
		int player_health = player_controll.get_player_data(player_name).getHealth();
		int effect_number = this.get_Event_health_data(event_ID).getEffect_number();
		player_controll.update_player_data(player_name, Player_enum.Health, String.valueOf(player_health - effect_number));
	}
}
