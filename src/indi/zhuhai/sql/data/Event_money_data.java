package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * 存储Event_money表中单行的数据
 */

public class Event_money_data {
	private int ID;
	private String message; //事件信息
	private char fame_handle; //比较声誉的操作符
	private int fame_number; //比较声誉的数值
	private char health_handle; //比较健康值的操作符
	private int health_number; //比较健康值的数值
	private int effect_ID; //影响的物品ID
	private char effect_handle; //影响的物品使用的操作符
	private double effect_number; //影响的物品的数值
	
	public Event_money_data(int ID,String message,String fame_handle,int fame_number,
			String health_handle,int health_number,int effect_ID,String effect_handle,double effect_number){
		this.ID = ID;
		this.message = message;
		this.fame_handle = fame_handle.charAt(0);
		this.fame_number = fame_number;
		this.health_handle = health_handle.charAt(0);
		this.health_number = health_number;
		this.effect_ID = effect_ID;
		this.effect_handle = effect_handle.charAt(0);
		this.effect_number = effect_number;
	}
	
	public char getEffect_handle() {
		return effect_handle;
	}
	
	public int getEffect_ID() {
		return effect_ID;
	}
	
	public double getEffect_number() {
		return effect_number;
	}
	
	public char getFame_handle() {
		return fame_handle;
	}
	
	public int getFame_number() {
		return fame_number;
	}
	
	public char getHealth_handle() {
		return health_handle;
	}
	
	public int getHealth_number() {
		return health_number;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getMessage() {
		return message;
	}
	
}
