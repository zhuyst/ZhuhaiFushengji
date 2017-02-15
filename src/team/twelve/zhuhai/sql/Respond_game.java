package team.twelve.zhuhai.sql;

import org.json.JSONArray;

import team.twelve.zhuhai.sql.apartment.Apartment_enum;
import team.twelve.zhuhai.sql.global.Global_enum;
import team.twelve.zhuhai.sql.item.Item_data;
import team.twelve.zhuhai.sql.pill.Pill_data;
import team.twelve.zhuhai.sql.pill.Pill_enum;
import team.twelve.zhuhai.sql.player.Player_controll;
import team.twelve.zhuhai.sql.player.Player_data;
import team.twelve.zhuhai.sql.rankinglist.Ranking_list_data;

/**
 * @author zhuyst
 * 负责对服务端的POST请求进行响应
 */

public class Respond_game {
	private JDBC jdbc;
	private Main_controll main_controll;  //数据库主控制器
	private Event_timer timer;  
	private String respond;  //响应正文
	
	public Respond_game() {
		jdbc = new JDBC();
		main_controll = new Main_controll(jdbc);
		timer = new Event_timer(main_controll);
		timer.start();
	}
	
	//获取响应正文
	public String getRespond(String content){
		System.out.println("content = " + content);
		try{
			String tag = content.split(":")[0];
			String message = content.split(":")[1];
			
			//登陆
			if(tag.equals("login")){
				String name = message.split("\\|")[0];
				String password = message.split("\\|")[1];
				Boolean ok = main_controll.getPlayer_controll().validate_password(name, password);
				if(ok == true) {
					respond = "1";
					System.out.println(name + " login success");
				}
				else {
					respond = "0";
					System.out.println(name + " login fail");
				}
			}
			
			//注册
			else if(tag.equals("signin")){
				String name = message.split("\\|")[0];
				String password = message.split("\\|")[1];
				Boolean ok = main_controll.getPlayer_controll().create_new_player(name, password);
				if(ok == true) {
					respond = "1";
					System.out.println(name + " signin success");
				}
				else {
					respond = "0"; 
					System.out.println(name + " signin fail");
				}
			}
			
			//获取玩家基本信息
			else if(tag.equals("getmessage")){
				String name = message;
				respond = getPlayer_data_json(name);
			}
			
			//获取玩家物品拥有信息
			else if(tag.equals("getplayeritem")){
				String name = message;
				respond = getPlayer_item_json(name);
			}
			
			//获取排行榜数据
			else if(tag.equals("getrankinglist")){
				respond = getRanking_list_json();
			}
			
			//获取医院药丸信息
			else if(tag.equals("getpilldata")){
				respond = getPill_price_json();
			}
			
			//玩家吃下药丸，恢复生命值
			else if(tag.equals("eatpill")){
				String name = message.split("\\|")[0];
				char pill_type = message.split("\\|")[1].charAt(0);
				
				Player_controll player_controll = main_controll.getPlayer_controll();
				switch (pill_type) {
				case 'F':
					player_controll.eat_pill(name, Pill_enum.F);
					break;
				case 'W':
					player_controll.eat_pill(name, Pill_enum.W);
					break;
				case 'R':
					player_controll.eat_pill(name, Pill_enum.R);
					break;
				case 'J':
					player_controll.eat_pill(name, Pill_enum.J);
					break;
				}
				
				respond = "1";
			}
			
			//玩家存款
			else if(tag.equals("depositmoney")){
				String name = message.split("\\|")[0];
				int number = Integer.parseInt(message.split("\\|")[1]);
				
				main_controll.getPlayer_controll().deposit_money(name, number);
				respond = "1";
			}
			
			//玩家还债
			else if(tag.equals("paydebt")){
				String name = message.split("\\|")[0];
				int number = Integer.parseInt(message.split("\\|")[1]);
				
				main_controll.getPlayer_controll().pay_debt(name, number);
				respond = "1";
			}
			
			//玩家取款
			else if(tag.equals("withdrawmoney")){
				String name = message.split("\\|")[0];
				int number = Integer.parseInt(message.split("\\|")[1]);
				
				main_controll.getPlayer_controll().withdraw_money(name, number);
				respond = "1";
			}
			
			//获取黑市中6个物品的ID
			else if(tag.equals("getitemlist")){
				respond = getItemlist_json();
			}
			
			//获取黑市物品的信息
			else if(tag.equals("getitemdata")){
				int item_ID = Integer.parseInt(message);
				respond = getItemdata_json(item_ID);
			}
			
			//经过一天，更新玩家数据并且发生一件随机事件，并返回事件信息
			else if(tag.equals("oneday")){
				String name = message;
				respond = main_controll.daily_event(name);
			}
			
			//返回目前的物价变动事件的信息
			else if(tag.equals("event")){
				respond = timer.getEvent_message();
			}
			
			//玩家购买物品
			else if(tag.equals("buyitem")){
				String name = message.split("\\|")[0];
				int item_ID = Integer.parseInt(message.split("\\|")[1]);
				int buy_number = Integer.parseInt(message.split("\\|")[2]);
				int item_price = Integer.parseInt(message.split("\\|")[3]);
				
				main_controll.getPlayer_controll().buy_item(name, item_ID, buy_number, item_price);
				respond = "1";
			}
			
			//玩家卖出物品
			else if(tag.equals("sellitem")){
				String name = message.split("\\|")[0];
				int item_ID = Integer.parseInt(message.split("\\|")[1]);
				int sell_number = Integer.parseInt(message.split("\\|")[2]);
				int item_price = Integer.parseInt(message.split("\\|")[3]);
				
				main_controll.getPlayer_controll().sell_item(name, item_ID, sell_number, item_price);
				respond = "1";
			}
			
			//获取出租屋信息
			else if(tag.equals("getapartment")){
				respond = getApartment_json();
			}
			
			else if(tag.equals("buyapartment")){
				String name = message.split("\\|")[0];
				String type = message.split("\\|")[1];
				
				Player_controll player_controll = main_controll.getPlayer_controll();
				if(type.equals(Apartment_enum.small.toString())){
					player_controll.buy_apartment(name, Apartment_enum.small);
				}
				else if(type.equals(Apartment_enum.medium.toString())){
					player_controll.buy_apartment(name, Apartment_enum.medium);
				}
				else if(type.equals(Apartment_enum.big.toString())){
					player_controll.buy_apartment(name, Apartment_enum.big);
				}
				
				respond = "1";
			}
			
			//玩家重新开始
			else if(tag.equals("restart")){
				String name = message;
				main_controll.getPlayer_controll().restart(name);
				respond = "1";
			}
			
			else System.out.println("未知tag");
			
		}catch (ArrayIndexOutOfBoundsException e) {
			respond = "0";
			System.out.println("非法字符");
		}catch (NumberFormatException e) {
			respond = "0";
			System.out.println("输入错误");
		}catch (Exception e) {
			respond = "0";
			System.out.println("未知错误");
			e.printStackTrace();
		}
		
		return respond;
	}
	
	private int[] getRandonNumber(int max,int min,int n){
		int result[] = new int[n];
		int count = 0;
		while(count < n){
			int num = (int)(min + Math.random() * (max - min + 1));
			boolean flag = true;
			for(int j = 0;j < n;j++){
				if(num == result[j]){
					flag = false;
					break;
				}
			}
			if(flag){
				result[count] = num;
				count ++ ;
			}
		}
		return result; 
	}
	
	private String getJson_string(String[] data){
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data.length);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data.length];
		JSONArray[] jsonArray_3 = new JSONArray[data.length];
		for(int i = 0;i < data.length;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		for(int i = 0; i < data.length;i++){
			jsonArray_3[i].put(data[i]);
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		
		return json_string;
	}
	
	private String getPlayer_data_json(String name){
		Player_data data = main_controll.getPlayer_controll().get_player_data(name);
		final int DATA_NUMBER = 9;
		
		String[] strings = new String[DATA_NUMBER];
		
		strings[0] = data.getName();
		strings[1] = "" + data.getMoney();
		strings[2] = "" + data.getDay();
		strings[3] = "" + data.getDebt();
		strings[4] = "" + data.getDeposit();
		strings[5] = "" + data.getHealth();
		strings[6] = "" + data.getFame();
		strings[7] = "" + data.getApartment_item_number();
		strings[8] = "" + data.getApartment_item_max();
		
		if(data.getDay() == 40) 
			main_controll.getRanking_list_controll().insert_new_winner(name);
		
		return getJson_string(strings);
		
	}
	
	private String getPlayer_item_json(String name){
		Player_data data = main_controll.getPlayer_controll().get_player_data(name);
		final int ITEM_NUMBER = main_controll.getGlobal_controll().get_data(Global_enum.Item_number);
		final int DATA_NUMBER = ITEM_NUMBER * 2;
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < ITEM_NUMBER;i++){
			strings[i] = main_controll.getItem_controll().get_Item_data(i + 1).getName();
			strings[i + ITEM_NUMBER] = "" + data.getItem()[i];
		}
		
		return getJson_string(strings);
	}
	
	private String getRanking_list_json(){
		Ranking_list_data data[] = main_controll.getRanking_list_controll().get_rangking_list();
		final int LIST_NUMBER = data[0].getPoint();
		final int DATA_NUMBER = LIST_NUMBER * 2;
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < LIST_NUMBER;i++){
			strings[i] = data[i + 1].getName();
			strings[i + LIST_NUMBER] = "" + data[i + 1].getPoint();
		}
		
		return getJson_string(strings);
	}
	
	private String getPill_price_json(){
		final int DATA_NUMBER = 8;
		final int PILL_NUMBER = 4;
		
		String[] strings = new String[DATA_NUMBER];
		
		Pill_data f_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.F);
		Pill_data w_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.W);
		Pill_data r_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.R);
		Pill_data j_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.J);
		
		Pill_data[] pill_data = new Pill_data[PILL_NUMBER];
		pill_data[0] = f_pill; pill_data[1] = w_pill;
		pill_data[2] = r_pill; pill_data[3] = j_pill;
		
		for(int i = 0;i < PILL_NUMBER;i++){
			strings[i] = "" + pill_data[i].getPrice();
			strings[i + PILL_NUMBER] = "" + pill_data[i].getHealth();
		}
		
		return getJson_string(strings);
	}
	
	private String getItemlist_json(){
		final int DATA_NUMBER = 6;
		final int MAX = main_controll.getGlobal_controll().get_data(Global_enum.Item_number);
		final int MIN = 1;
		int[] randomnumber = getRandonNumber(MAX,MIN,DATA_NUMBER);
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < DATA_NUMBER;i++){
			strings[i] = "" + randomnumber[i];
		}
		
		return getJson_string(strings);
	}
	
	private String getItemdata_json(int item_ID){
		final int DATA_NUMBER = 4;
		Item_data data = main_controll.getItem_controll().get_Item_data(item_ID);
		
		String[] strings = new String[DATA_NUMBER];
		
		strings[0] = "" + data.getID();
		strings[1] = data.getName();
		strings[2] = data.getIntroduce();
		
		int base_price = data.getPrice();
		int max = (int)(base_price * 1.2);
		int min = (int)(base_price * 0.8);
		strings[3] = "" + (int)(min + Math.random() * (max - min + 1));
		
		return getJson_string(strings);
	}
	
	private String getApartment_json(){
		final int DATA_NUMBER = 3;
		
		String[] strings = new String[DATA_NUMBER];
		
		strings[0] = "" + main_controll.getApartment_controll().get_Apartment_data(Apartment_enum.small).getPrice();
		strings[1] = "" + main_controll.getApartment_controll().get_Apartment_data(Apartment_enum.medium).getPrice();
		strings[2] = "" + main_controll.getApartment_controll().get_Apartment_data(Apartment_enum.big).getPrice();
		
		return getJson_string(strings);
	}
}
