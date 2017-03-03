package indi.zhuhai.sql;

import indi.zhuhai.sql.controller.Apartment_controll;
import indi.zhuhai.sql.controller.Event_health_controll;
import indi.zhuhai.sql.controller.Event_item_controll;
import indi.zhuhai.sql.controller.Event_money_controll;
import indi.zhuhai.sql.controller.Global_controll;
import indi.zhuhai.sql.controller.Item_controll;
import indi.zhuhai.sql.controller.Pill_controll;
import indi.zhuhai.sql.controller.Player_controll;
import indi.zhuhai.sql.controller.Ranking_list_controll;
import indi.zhuhai.sql.data.Player_data;
import indi.zhuhai.sql.dataenum.Global_enum;
import indi.zhuhai.sql.dataenum.Player_enum;

/**
 * @author zhuyst
 * 游戏数据库的主控制类
 */

public class Main_controll {
	private JDBC mysql;
	private Player_controll player_controll; 
	private Item_controll item_controll;
	private Apartment_controll apartment_controll;
	private Global_controll global_controll;
	private Event_health_controll event_health_controll;
	private Event_item_controll event_item_controll;
	private Event_money_controll event_money_controll;
	private Ranking_list_controll ranking_list_controll;
	private Pill_controll pill_controll;
		
	public Main_controll(JDBC jdbc) {
		this.mysql = jdbc;
		this.item_controll = new Item_controll(jdbc);
		this.global_controll = new Global_controll(jdbc);
		this.apartment_controll = new Apartment_controll(jdbc);
		this.pill_controll = new Pill_controll(jdbc);
		this.player_controll = new Player_controll(jdbc, global_controll,item_controll,apartment_controll,pill_controll);
		this.event_health_controll = new Event_health_controll(jdbc, player_controll);
		this.event_item_controll = new Event_item_controll(jdbc, item_controll);
		this.event_money_controll = new Event_money_controll(jdbc, player_controll);
		this.ranking_list_controll = new Ranking_list_controll(jdbc, player_controll, global_controll);
	}
	
	public String daily_event(String player_name){
		String event_message = null;
		Player_data player_data = this.player_controll.get_player_data(player_name);
		
		this.player_controll.update_player_data(player_name, Player_enum.Day, String.valueOf(player_data.getDay() + 1));
		this.player_controll.update_player_data(player_name, Player_enum.Debt, String.valueOf(player_data.getDebt() * 1.1));
		this.player_controll.update_player_data(player_name, Player_enum.Deposit, String.valueOf(player_data.getDeposit() * 1.1));
		
		int event_type = (int)(1+Math.random()*100);
		
		if(event_type >= 1 && event_type <= player_data.getFame()){  //触发意外得财/失财事件
			Boolean can_active = false;
			int number = 0;

			int event_number = this.global_controll.get_data(Global_enum.Event_money_number);
			for(int i = 1;!can_active;i++){
				number = (int)(1 + Math.random() * event_number);
				can_active = this.event_money_controll.check(player_name, number);
				if(can_active && i <= event_number){
					this.event_money_controll.active(player_name, number);
				}
				else {
					this.event_money_controll.active(player_name, number);
					break;
				}
			}
			event_message = this.event_money_controll.get_event_money_data(number).getMessage();
		}
		
		else {  //触发健康受损事件
			int event_number = this.global_controll.get_data(Global_enum.Event_health_number);
			int number = (int)(1 + Math.random() * event_number);
			this.event_health_controll.active(player_name, number);
			event_message = this.event_health_controll.get_Event_health_data(number).getMessage();
		}
		
		return event_message;
	}
	
	public Event_health_controll getEvent_health_controll() {
		return event_health_controll;
	}
	
	public Event_item_controll getEvent_item_controll() {
		return event_item_controll;
	}
	
	public Global_controll getGlobal_controll() {
		return global_controll;
	}
	
	public Item_controll getItem_controll() {
		return item_controll;
	}
	
	public Player_controll getPlayer_controll() {
		return player_controll;
	}
	
	public Event_money_controll getEvent_money_controll() {
		return event_money_controll;
	}
	
	public JDBC getMysql() {
		return mysql;
	}
	
	public Ranking_list_controll getRanking_list_controll() {
		return ranking_list_controll;
	}
	
	public Apartment_controll getApartment_controll() {
		return apartment_controll;
	}
	
	public Pill_controll getPill_controll() {
		return pill_controll;
	}
	
}
