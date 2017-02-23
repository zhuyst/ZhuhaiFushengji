package team.twelve.zhuhai.sql.player;

import java.sql.SQLException;
import java.util.Map;

import team.twelve.zhuhai.sql.JDBC;
import team.twelve.zhuhai.sql.apartment.Apartment_controll;
import team.twelve.zhuhai.sql.apartment.Apartment_data;
import team.twelve.zhuhai.sql.apartment.Apartment_enum;
import team.twelve.zhuhai.sql.global.Global_controll;
import team.twelve.zhuhai.sql.global.Global_enum;
import team.twelve.zhuhai.sql.item.Item_controll;
import team.twelve.zhuhai.sql.item.Item_data;
import team.twelve.zhuhai.sql.pill.Pill_controll;
import team.twelve.zhuhai.sql.pill.Pill_data;
import team.twelve.zhuhai.sql.pill.Pill_enum;

import com.mysql.jdbc.PreparedStatement;

/**
 * 玩家
 * @author zhuyst
 * 对Player表的控制类
 * 负责实现玩家所有的基本操作
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

	//创建新角色，true = 创建成功，false = 有重复的用户名,创建失败 
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
	
	//验证密码是否正确
	public boolean validate_password(String name,String password){
		String sql = "select Name,Password from player where Name = '" + name + "' and Password = md5('" + password + "')";
		Map<String, String> result = mysql.select(sql, new String[]{"Name"});
		if(result == null) return false;
		else return true;
	}
	
	//验证用户名是否有重复
	private boolean validate_duplicate_name(String name){
		String sql = "select Name from player where Name = '" + name + "'";
		Map<String, String> result = mysql.select(sql, new String[]{"Name"});
		if(result == null) return true;
		else return false;
	}
	
	//获取玩家的信息
	public Player_data get_player_data(String name){
		Player_data data = new Player_data();
		int item_number = global_controll.get_data(Global_enum.Item_number);
		String sql = "select * from player where Name = '" + name + "'";
		
		String[] filedname = data.getFiledName();
		int base_number = filedname.length - 1;
		String[] params = new String[base_number + item_number];
		
		System.arraycopy(filedname, 0, params, 0, base_number);
		for(int i = 1;i <= item_number;i++){
			params[base_number - 1 + i] = "Item_" + i;
		}
		
		Map<String, String> result = mysql.select(sql, params);
		data = new Player_data(result,item_number);
		
		return data;
	}
	
	//更新玩家的信息
	public void update_player_data(String name,Player_enum player_enum,String update_data){
		String sql = "update player set " + player_enum + "='" + update_data + "' where Name = '" + name + "'";
		mysql.update(sql);
	}
	
	//更新物品数量
	public void update_player_item(String name,int item_ID,int number){
		String sql = "update player set Item_" + item_ID + "='" + number + "' where Name = '" + name + "'";
		mysql.update(sql);
	}
	
	//购买物品
	public void buy_item(String name,int item_ID,int buy_number,int item_price){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int item_number = data.getItem()[item_ID - 1];
		
		int apartment_item_number = data.getApartment_item_number();
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - item_price * buy_number));
		this.update_player_item(name, item_ID, item_number + buy_number);
		this.update_player_data(name, Player_enum.Apartment_item_number, String.valueOf(apartment_item_number + buy_number));
	}
	
	//卖出物品
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
	
	//存款
	public void deposit_money (String name,int number){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int player_deposit = data.getDeposit();
		
		this.update_player_data(name, Player_enum.Deposit, String.valueOf(player_deposit + number));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - number));
	}
	
	//取款
	public void withdraw_money (String name,int number){
		Player_data data = this.get_player_data(name);
		int player_money = data.getMoney();
		int player_deposit = data.getDeposit();
		
		this.update_player_data(name, Player_enum.Deposit, String.valueOf(player_deposit - number));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money + number));
	}
	
	//还债/捐款
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
	
	//购买出租屋
	public void buy_apartment(String name,Apartment_enum apartment_enum){
		Player_data data = get_player_data(name);
		int player_money = data.getMoney();
		int apartment_item_max = data.getApartment_item_max();
		Apartment_data apartment_data = apartment_controll.get_Apartment_data(apartment_enum);
		
		this.update_player_data(name, Player_enum.Apartment_item_max, String.valueOf(apartment_item_max + apartment_data.getSpec()));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - apartment_data.getPrice()));
	}
	
	//医院吃药
	public void eat_pill(String name,Pill_enum pill_enum){
		Player_data player_data = this.get_player_data(name);
		int player_money = player_data.getMoney();
		int player_health = player_data.getHealth();
		
		Pill_data pill_data = pill_controll.get_Pill_data(pill_enum);
		this.update_player_data(name, Player_enum.Health, String.valueOf(player_health + pill_data.getHealth()));
		this.update_player_data(name, Player_enum.Money, String.valueOf(player_money - pill_data.getPrice()));
	}
	
	//重新开始
	public void restart(String name){
		String sql = "update player set Money=3000,Deposit=0,Debt=5000,Day=0,Fame=100,Health=100,Apartment_item_number=0,"
				+ "Apartment_item_max=100";
		int item_number = global_controll.get_data(Global_enum.Item_number);
		for(int i = 1;i <= item_number;i++){
			sql += ",Item_" + i + "=0";
		}
		sql += " where Name = '" + name + "'";
		mysql.update(sql);
	}
}
