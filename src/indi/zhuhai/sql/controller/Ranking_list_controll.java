package indi.zhuhai.sql.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Player_data;
import indi.zhuhai.sql.data.Ranking_list_data;
import indi.zhuhai.sql.dataenum.Global_enum;

/**
 * 排行榜
 * @author zhuyst
 * 对Ranking_list表的控制类
 * 负责添加新的通关玩家以及返回排行榜
 */

public class Ranking_list_controll{
	private JDBC mysql;
	private Player_controll player_controll;
	private Global_controll global_controll;
	
	public Ranking_list_controll(JDBC jdbc,Player_controll player_controll,Global_controll global_controll) {
		this.mysql = jdbc;
		this.player_controll = player_controll;
		this.global_controll = global_controll;
	}
	
	//添加新的通关玩家
	public void insert_new_winner(String name){
		Player_data data = player_controll.get_player_data(name);
		int new_ID = global_controll.get_data(Global_enum.Winner_number) + 1;
		int money = data.getMoney();
		int deposit = data.getDeposit();
		int debt = data.getDebt();
		int point = money + deposit - debt;
		PreparedStatement pst;
		String sql = "insert into ranking_list (ID,Name,Point) values(?,?,?)";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			pst.setInt(1, new_ID);
			pst.setString(2, name);
			pst.setInt(3, point);
			pst.executeUpdate();
			pst.close();
			global_controll.update_data(Global_enum.Winner_number,String.valueOf(new_ID));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获取排行榜,data[0]的point存放排行榜的人数,排行榜最多为10人
	public Ranking_list_data[] get_rangking_list(){
		int list_number = 0;
		int winner_number = global_controll.get_data(Global_enum.Winner_number);
		if(winner_number >= 10) list_number = 10;
		else list_number = winner_number;
		Ranking_list_data[] data = new Ranking_list_data[list_number+1];
		data[0] = new Ranking_list_data(null, list_number);
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Name,Point from ranking_list order by Point desc";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			for(int i = 1;i <= list_number;i++){
				rs.next();
				String name = rs.getString(1);
				int point = rs.getInt(2);
				data[i] = new Ranking_list_data(name,point);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		
		return data;
	}
}
