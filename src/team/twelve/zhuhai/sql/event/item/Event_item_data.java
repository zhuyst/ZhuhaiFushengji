package team.twelve.zhuhai.sql.event.item;

import java.util.Map;

import team.twelve.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * 存放Event_item表中单行的数据
 */

public class Event_item_data extends Data_filedname{
	private int ID;
	private String message; //事件信息
	private int[] effect_item_ID; //事件发生时影响的物品ID
	private char effect_handle; //影响的操作符
	private int effect_number; //影响的数值
	
	public Event_item_data(){
		
	}
	
	public Event_item_data(Map<String, String> result){
		this.ID = Integer.parseInt(result.get("ID"));
		this.message = result.get("message");
		String[] effect_item_ID_S = result.get("effect_item_ID").split("\\.");
		effect_item_ID = new int[effect_item_ID_S.length];
		for(int i = 0;i < effect_item_ID_S.length;i++){
			this.effect_item_ID[i] = Integer.valueOf(effect_item_ID_S[i]);
		}
		this.effect_handle = result.get("effect_handle").charAt(0);
		this.effect_number = Integer.parseInt(result.get("effect_number"));
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
