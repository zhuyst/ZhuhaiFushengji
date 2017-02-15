package team.twelve.zhuhai.sql.event.money;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import team.twelve.zhuhai.sql.JDBC;
import team.twelve.zhuhai.sql.player.Player_controll;
import team.twelve.zhuhai.sql.player.Player_data;
import team.twelve.zhuhai.sql.player.Player_enum;

/**
 * 意外得财/失财事件
 * @author zhuyst
 * 对Event_money表的控制类
 * 负责激活意外得财/失财事件
 */
public class Event_money_controll{
	private JDBC mysql;
	private Player_controll player_controll;
	
	public Event_money_controll(JDBC jdbc,Player_controll player_controll) {
		this.mysql = jdbc;
		this.player_controll = player_controll;
	}
	
	//获取关于意外得财/失财的事件的详情
	public Event_money_data get_event_money_data(int ID){
		Event_money_data data = null;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Message,Fame_handle,Fame_number,Health_handle,Health_number,Effect_ID"
				+ ",Effect_handle,Effect_number from event_money where ID = '" + ID + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			String message = rs.getString(1);
			String fame_handle = rs.getString(2);
			int fame_number = rs.getInt(3);
			String health_handle = rs.getString(4);
			int health_number = rs.getInt(5);
			int effect_ID = rs.getInt(6);
			String effect_handle = rs.getString(7);
			double effect_number = rs.getDouble(8);
			data = new Event_money_data(ID, message, fame_handle, fame_number, health_handle,
					health_number, effect_ID, effect_handle, effect_number);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	//检查是否符合激活条件
	public boolean check (String player_name,int event_ID){
		Event_money_data event_data = this.get_event_money_data(event_ID);
		Player_data player_data = player_controll.get_player_data(player_name);
		
		Boolean fame_OK = false;
		if(event_data.getFame_handle() == '>' && player_data.getFame() >= event_data.getFame_number()){
			fame_OK = true;
		}
		else if(event_data.getFame_handle() == '<' && player_data.getFame() <= event_data.getFame_number()){
			fame_OK = true;
		}
		
		Boolean health_OK = false;
		if(event_data.getHealth_handle() == '>' && player_data.getHealth() >= event_data.getHealth_number()){
			health_OK = true;
		}
		else if(event_data.getHealth_handle() == '<' && player_data.getHealth() <= event_data.getHealth_number()){
			health_OK = true;
		}
		
		return fame_OK&&health_OK;
	}
	
	//激活关于意外得财/失财的事件
	public void active (String player_name,int event_ID){	
		Event_money_data event_data = this.get_event_money_data(event_ID);
		Player_data player_data = player_controll.get_player_data(player_name);
		
		int effect_ID = event_data.getEffect_ID();
		if(effect_ID == -1){
			player_controll.update_player_data(player_name, Player_enum.Money, String.valueOf((int)(player_data.getMoney() * 
					event_data.getEffect_number())));
		}
		else if(effect_ID == -2){
			player_controll.update_player_data(player_name, Player_enum.Deposit, String.valueOf((int)(player_data.getDeposit() * 
					event_data.getEffect_number())));
		}
		else {
			if(event_data.getEffect_handle() == '+'){
				player_controll.update_player_item(player_name, effect_ID, player_data.getItem()[effect_ID - 1] + 
						(int)event_data.getEffect_number());
				player_controll.update_player_data(player_name,Player_enum.Apartment_item_number, 
						String.valueOf(player_data.getApartment_item_number() + (int)event_data.getEffect_number()));
			}
			else if(event_data.getEffect_handle() == '='){
				player_controll.update_player_item(player_name, effect_ID, (int)event_data.getEffect_number());
				player_controll.update_player_data(player_name, Player_enum.Apartment_item_number, 
						String.valueOf(player_data.getApartment_item_number() - player_data.getItem()[effect_ID - 1]));
			}
		}
	}
}
