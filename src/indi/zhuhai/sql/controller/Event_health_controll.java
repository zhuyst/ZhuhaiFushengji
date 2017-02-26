package indi.zhuhai.sql.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Event_health_data;
import indi.zhuhai.sql.dataenum.Player_enum;

/**
 * ���������¼�
 * @author zhuyst
 * ��Event_health���Ŀ�����
 * ���𼤻�������¼�
 */

public class Event_health_controll{
	private JDBC mysql;
	private Player_controll player_controll;
	
	public Event_health_controll(JDBC jdbc,Player_controll player_controll) {
		this.mysql = jdbc;
		this.player_controll = player_controll;
	}
	
	//��ȡ���ڽ���������¼�����
	public Event_health_data get_Event_health_data(int ID){
		Event_health_data data = null;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Message,Effect_number from event_health where ID = '" + ID + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			String message = rs.getString(1);
			int effect_number = rs.getInt(2);
			data = new Event_health_data(ID, message, effect_number);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	//������ڽ���������¼�
	public void active(String player_name,int event_ID){
		int player_health = player_controll.get_player_data(player_name).getHealth();
		int effect_number = this.get_Event_health_data(event_ID).getEffect_number();
		player_controll.update_player_data(player_name, Player_enum.Health, String.valueOf(player_health - effect_number));
	}
}