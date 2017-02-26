package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * 存放Event_item表中单行的数据
 */

public class Event_item_data {
	private int ID;
	private String message; //事件信息
	private int[] effect_item_ID; //事件发生时影响的物品ID
	private char effect_handle; //影响的操作符
	private int effect_number; //影响的数值
	
	public Event_item_data(int ID,String message,String effect_item_ID_S,char effect_handle,int effect_number){
		this.ID = ID;
		this.message = message;
		String[] effect_item_ID_SS = effect_item_ID_S.split("\\.");
		effect_item_ID = new int[effect_item_ID_SS.length];
		for(int i = 0;i < effect_item_ID_SS.length;i++){
			this.effect_item_ID[i] = Integer.valueOf(effect_item_ID_SS[i]);
		}
		this.effect_handle = effect_handle;
		this.effect_number = effect_number;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int[] getEffect_item_ID() {
		return effect_item_ID;
	}
	
	public int getEffect_number() {
		return effect_number;
	}
	
	public char getEffect_handle() {
		return effect_handle;
	}
	
}
