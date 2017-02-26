package indi.zhuhai.sql;

import org.json.JSONArray;

import indi.zhuhai.sql.controller.Main_controll;
import indi.zhuhai.sql.controller.Player_controll;
import indi.zhuhai.sql.data.Item_data;
import indi.zhuhai.sql.data.Pill_data;
import indi.zhuhai.sql.data.Player_data;
import indi.zhuhai.sql.data.Ranking_list_data;
import indi.zhuhai.sql.dataenum.Apartment_enum;
import indi.zhuhai.sql.dataenum.Global_enum;
import indi.zhuhai.sql.dataenum.Pill_enum;

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
	
	private String getPlayer_data_json(String name){
		Player_data data = main_controll.getPlayer_controll().get_player_data(name);
		int data_number = 9;
		
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data_number);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data_number];
		JSONArray[] jsonArray_3 = new JSONArray[data_number];
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		jsonArray_3[0].put(data.getName());
		jsonArray_3[1].put("" + data.getMoney());
		jsonArray_3[2].put("" + data.getDay());
		jsonArray_3[3].put("" + data.getDebt());
		jsonArray_3[4].put("" + data.getDeposit());
		jsonArray_3[5].put("" + data.getHealth());
		jsonArray_3[6].put("" + data.getFame());
		jsonArray_3[7].put("" + data.getApartment_item_number());
		jsonArray_3[8].put("" + data.getApartment_item_max());
		
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		
		if(data.getDay() == 40) 
			main_controll.getRanking_list_controll().insert_new_winner(name);
		
		return json_string;
		
	}
	
	private String getPlayer_item_json(String name){
		Player_data data = main_controll.getPlayer_controll().get_player_data(name);
		int item_number = main_controll.getGlobal_controll().get_data(Global_enum.Item_number);
		int data_number = item_number * 2;
		
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data_number);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data_number];
		JSONArray[] jsonArray_3 = new JSONArray[data_number];
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		for(int i = 0;i < item_number;i++){
			jsonArray_3[i].put(main_controll.getItem_controll().get_Item_data(i + 1).getName());
			jsonArray_3[i + item_number].put("" + data.getItem()[i]);
		}
		
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		return json_string;
	}
	
	private String getRanking_list_json(){
		Ranking_list_data data[] = main_controll.getRanking_list_controll().get_rangking_list();
		int list_number = data[0].getPoint();
		
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(list_number * 2);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[list_number * 2];
		JSONArray[] jsonArray_3 = new JSONArray[list_number * 2];
		for(int i = 0;i < list_number * 2;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		for(int i = 0;i < list_number;i++){
			jsonArray_3[i].put(data[i + 1].getName());
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		for(int i = 0;i < list_number;i++){
			jsonArray_3[i + list_number].put("" + data[i + 1].getPoint());
			jsonArray_2[i + list_number].put(jsonArray_3[i + list_number]);
			jsonArray_1.put(jsonArray_2[i + list_number]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		return json_string;
	}
	
	private String getPill_price_json(){
		int data_number = 8;
		int pill_number = 4;
		
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data_number);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data_number];
		JSONArray[] jsonArray_3 = new JSONArray[data_number];
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		Pill_data f_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.F);
		Pill_data w_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.W);
		Pill_data r_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.R);
		Pill_data j_pill = main_controll.getPill_controll().get_Pill_data(Pill_enum.J);
		
		Pill_data[] pill_data = new Pill_data[pill_number];
		pill_data[0] = f_pill; pill_data[1] = w_pill;
		pill_data[2] = r_pill; pill_data[3] = j_pill;
		
		for(int i = 0;i < pill_number;i++){
			jsonArray_3[i].put("" + pill_data[i].getPrice());
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		for(int i = 0;i < pill_number;i++){
			jsonArray_3[i + pill_number].put("" + pill_data[i].getHealth());
			jsonArray_2[i + pill_number].put(jsonArray_3[i + pill_number]);
			jsonArray_1.put(jsonArray_2[i + pill_number]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		return json_string;
	}
	
	private String getItemlist_json(){
		int data_number = 6;
		int max = main_controll.getGlobal_controll().get_data(Global_enum.Item_number);
		int min = 1;
		int[] randomnumber = getRandonNumber(max,min,data_number);
		
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data_number);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data_number];
		JSONArray[] jsonArray_3 = new JSONArray[data_number];
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		for(int i = 0;i < data_number;i++){
			jsonArray_3[i].put("" + randomnumber[i]);
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		return json_string;
	}
	
	private String getItemdata_json(int item_ID){
		int data_number = 4;
		Item_data data = main_controll.getItem_controll().get_Item_data(item_ID);
		
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data_number);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data_number];
		JSONArray[] jsonArray_3 = new JSONArray[data_number];
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		jsonArray_3[0].put(data.getID());
		jsonArray_3[1].put(data.getName());
		jsonArray_3[2].put(data.getIntroduce());
		
		int base_price = data.getPrice();
		int max = (int)(base_price * 1.4);
		int min = (int)(base_price * 0.6);
		jsonArray_3[3].put("" + (int)(min + Math.random() * (max - min + 1)));
		
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		return json_string;
	}
	
	private String getApartment_json(){
		int data_number = 3;
		
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data_number);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data_number];
		JSONArray[] jsonArray_3 = new JSONArray[data_number];
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		jsonArray_3[0].put("" + main_controll.getApartment_controll().get_Apartment_data(Apartment_enum.small).getPrice());
		jsonArray_3[1].put("" + main_controll.getApartment_controll().get_Apartment_data(Apartment_enum.medium).getPrice());
		jsonArray_3[2].put("" + main_controll.getApartment_controll().get_Apartment_data(Apartment_enum.big).getPrice());
		
		for(int i = 0;i < data_number;i++){
			jsonArray_2[i].put(jsonArray_3[i]);
			jsonArray_1.put(jsonArray_2[i]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		return json_string;
	}
}
