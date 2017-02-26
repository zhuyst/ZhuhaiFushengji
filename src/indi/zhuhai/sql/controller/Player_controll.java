package indi.zhuhai.sql.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.data.Apartment_data;
import indi.zhuhai.sql.data.Item_data;
import indi.zhuhai.sql.data.Pill_data;
import indi.zhuhai.sql.data.Player_data;
import indi.zhuhai.sql.dataenum.Apartment_enum;
import indi.zhuhai.sql.dataenum.Global_enum;
import indi.zhuhai.sql.dataenum.Pill_enum;
import indi.zhuhai.sql.dataenum.Player_enum;

/**
 * ���
 * @author zhuyst
 * ��Player��Ŀ�����
 * ����ʵ��������еĻ�������
 */

public class Player_controll{
	private JDBC mysql;
	private Global_controll global_controll;
	private Item_controll item_controll;
	private Apartment_controll apartment_controll;
	private Pill_controll pill_controll;
	
	public Player_controll(JDBC jdbc,Global_controll global_controll,Item_controll item_controll,
			Apartment_controll apartment_controll,Pill_controll pill_controll) {
		this.mysql = jdbc;
		this.item_controll = item_controll;
		this.global_controll = global_controll;
		this.apartment_controll = apartment_controll;
		this.pill_controll = pill_controll;
	}

	//�����½�ɫ��true = �����ɹ���false = ���ظ����û���,����ʧ�� 
	public boolean create_new_player(String name,String password){
		if(!this.validate_duplicate_name(name)){
			PreparedStatement pst;
			String sql = "insert into player (ID,Name,Password) values(?,?,md5(?))";
			try {
				pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
				int new_ID = global_controll.get_data(Global_enum.Player_number) + 1;
				pst.setInt(1, new_ID);
				pst.setString(2, name);
				pst.setString(3, password);
				pst.executeUpdate();
				pst.close();
				global_controll.update_data(Global_enum.Player_number,String.valueOf(new_ID));;
			} catch (SQLException e) {
				return false;
			}
			return true;
		}
		else return false;
	}
	
	//��֤�����Ƿ���ȷ
	public boolean validate_password(String name,String password){
		Boolean isright_password = true;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Name,Password from player where Name = '" + name + "' and Password = md5('" + password + "')";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			rs.getString(1);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			isright_password = false;
		}
		return isright_password;
	}
	
	//��֤�û����Ƿ����ظ�
	private boolean validate_duplicate_name(String name){
		Boolean isduplicate_name = true;
		PreparedStatement pst;
		ResultSet rs;
		String sql = "select Name from player where Name = '" + name + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			rs.getString(1);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			isduplicate_name = false;
		}
		return isduplicate_name;
	}
	
	//��ȡ��ҵ���Ϣ
	public Player_data get_player_data(String name){
		Player_data data = null;
		PreparedStatement pst;
		ResultSet rs;
		int item_number = global_controll.get_data(Global_enum.Item_number);
		String sql = "select ID,Money,Deposit,Debt,Day,Fame,Health,Apartment_item_number,Apartment_item_max";
		for(int i = 1;i <= item_number;i++){
			sql += ",Item_" + i;
		}
		sql += " from player where Name = '" + name + "'";
		try {
			pst = (PreparedStatement)mysql.getConnection().prepareStatement(sql);
			rs = pst.executeQuery();
			rs.next();
			int ID = rs.getInt(1);
			int money = rs.getInt(2);
			int deposit = rs.getInt(3);
			int debt = rs.getInt(4);
			int day = rs.getInt(5);
			int fame = rs.getInt(6);
			int health = rs.getInt(7);
			int apartment_item_number = rs.getInt(8);
			int apartment_item_max = rs.getInt(9);
			int[] item = new int[item_number];
			for(int i = 0;i < item_number;i++){
				item[i] = rs.getInt(i+10);
			}
			data = new Player_data(ID,name,money,deposit,debt,day,fame,health,apartment_item_number,
					apartment_item_max,item,item_number);
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	//������ҵ���Ϣ
	public void update_player_data(String name,Player_enum player_enum,String update_data){
		PreparedStatement pst;
		String sql = "update player set " + player_enum + "='" + update_data + "' where Name = '" + name + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//������Ʒ
	public void buy_item(String name,int item_ID,int buy_number,int item_price){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int item_number = data.getItem()[item_ID - 1];
		
		int apartment_item_number = data.getApartment_item_number();
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - item_price * buy_number));
		this.update_player_item(name, item_ID, item_number + buy_number);
		this.update_player_data(name, Player_enum.Apartment_item_number, String.valueOf(apartment_item_number + buy_number));
	}
	
	//������Ʒ
	public void sell_item(String name,int item_ID,Integer sell_number,int item_price){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int player_fame = data.getFame();
		int item_number = data.getItem()[item_ID - 1];
		int apartment_item_number = data.getApartment_item_number();
		
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money + item_price * sell_number));
		this.update_player_item(name, item_ID, item_number - sell_number);
		this.update_player_data(name, Player_enum.Apartment_item_number, String.valueOf(apartment_item_number - sell_number));
		
		
		Item_data item_data = item_controll.get_Item_data(item_ID);
		char effect_handle = item_data.getEffect_handle();
		int effect_number =item_data.getEffect_number();
		
		if(effect_handle == '+'){
			this.update_player_data(name, Player_enum.Fame, String.valueOf(player_fame + effect_number * sell_number));
		}
		else if(effect_handle == '-'){
			this.update_player_data(name, Player_enum.Fame, String.valueOf(player_fame - effect_number * sell_number));
		}
	}
	
	//������Ʒ����
	public void update_player_item(String name,int item_ID,int number){
		PreparedStatement pst;
		String sql = "update player set Item_" + item_ID + "='" + number + "' where Name = '" + name + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//���
	public void deposit_money (String name,int number){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int player_deposit = data.getDeposit();
		
		this.update_player_data(name, Player_enum.Deposit, String.valueOf(player_deposit + number));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - number));
	}
	
	//ȡ��
	public void withdraw_money (String name,int number){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int player_deposit = data.getDeposit();
		
		this.update_player_data(name, Player_enum.Deposit, String.valueOf(player_deposit - number));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money + number));
	}
	
	//��ծ/���
	public void pay_debt(String name,int number){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int player_debt = data.getDebt();
		int player_fame = data.getFame();
		
		if(player_debt > 0){
			this.update_player_data(name, Player_enum.Debt, String.valueOf(player_debt - number));
		}
		else {
			this.update_player_data(name, Player_enum.Fame, String.valueOf(player_fame + number/100));
		}
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - number));
	}
	
	//���������
	public void buy_apartment(String name,Apartment_enum apartment_enum){
		Player_data data = get_player_data(name);
		int player_money = data.getMoney();
		int apartment_item_max = data.getApartment_item_max();
		Apartment_data apartment_data = apartment_controll.get_Apartment_data(apartment_enum);
		
		this.update_player_data(name, Player_enum.Apartment_item_max, String.valueOf(apartment_item_max + apartment_data.getSpec()));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - apartment_data.getPrice()));
	}
	
	//ҽԺ��ҩ
	public void eat_pill(String name,Pill_enum pill_enum){
		Player_data player_data = this.get_player_data(name);
		int player_money = player_data.getMoney();
		int player_health = player_data.getHealth();
		
		Pill_data pill_data = pill_controll.get_Pill_data(pill_enum);
		this.update_player_data(name, Player_enum.Health, String.valueOf(player_health + pill_data.getHealth()));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - pill_data.getPrice()));
	}
	
	//���¿�ʼ
	public void restart(String name){
		PreparedStatement pst;
		String sql = "update player set Money=3000,Deposit=0,Debt=5000,Day=0,Fame=100,Health=100,Apartment_item_number=0,"
				+ "Apartment_item_max=100";
		int item_number = global_controll.get_data(Global_enum.Item_number);
		for(int i = 1;i <= item_number;i++){
			sql += ",Item_" + i + "=0";
		}
		sql += " where Name = '" + name + "'";
		try {
			pst = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
