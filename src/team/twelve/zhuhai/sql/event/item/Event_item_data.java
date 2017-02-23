package team.twelve.zhuhai.sql.event.item;

import java.util.Map;

import team.twelve.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * ���Event_item���е��е�����
 */

public class Event_item_data extends Data_filedname{
	private int ID;
	private String message; //�¼���Ϣ
	private int[] effect_item_ID; //�¼�����ʱӰ�����ƷID
	private char effect_handle; //Ӱ��Ĳ�����
	private int effect_number; //Ӱ�����ֵ
	
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
