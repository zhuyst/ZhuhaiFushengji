package indi.zhuhai.sql.data;

import java.util.Map;

import indi.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * 存储Player表中单行的数据
 */

public class Player_data extends Data_filedname{
	private int ID;
	private String name; //玩家用户名
	private int money; //金钱
	private int deposit; //银行存款 
	private int debt; //负债
	private int day; //经历的天数
	private int fame; //声誉
	private int health; //健康
	private int apartment_item_number; //出租屋中存放的物品数
	private int apartment_item_max; //出租屋的最大存放量
	private int[] item; //各物品的用户数
	
	public Player_data(){
		
	}
	
	public Player_data(Map<String, String> result,int item_number){
		this.ID = Integer.parseInt(result.get("ID"));
		this.name = result.get("Name");
		this.money = Integer.parseInt(result.get("money"));
		this.deposit = Integer.parseInt(result.get("deposit"));
		this.debt = Integer.parseInt(result.get("debt"));
		this.day = Integer.parseInt(result.get("day"));
		this.fame = Integer.parseInt(result.get("fame"));
		this.health = Integer.parseInt(result.get("health"));
		this.apartment_item_number = Integer.parseInt(result.get("apartment_item_number"));
		this.apartment_item_max = Integer.parseInt(result.get("apartment_item_max"));
		this.item = new int[item_number];
		for(int i = 1;i <= item_number;i++){
			item[i - 1] = Integer.parseInt(result.get("Item_" + i));
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getDeposit() {
		return deposit;
	}
	
	public int getDebt() {
		return debt;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getFame() {
		return fame;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getApartment_item_number() {
		return apartment_item_number;
	}
	
	public int getApartment_item_max() {
		return apartment_item_max;
	}
	
	public int[] getItem() {
		return item;
	}
}
