package team.twelve.zhuhai.sql.event.money;

import java.util.Map;

import team.twelve.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * �洢Event_money���е��е�����
 */

public class Event_money_data extends Data_filedname{
	private int ID;
	private String message; //�¼���Ϣ
	private char fame_handle; //�Ƚ������Ĳ�����
	private int fame_number; //�Ƚ���������ֵ
	private char health_handle; //�ȽϽ���ֵ�Ĳ�����
	private int health_number; //�ȽϽ���ֵ����ֵ
	private int effect_ID; //Ӱ�����ƷID
	private char effect_handle; //Ӱ�����Ʒʹ�õĲ�����
	private double effect_number; //Ӱ�����Ʒ����ֵ
	
	public Event_money_data(){
		
	}
	
	public Event_money_data(Map<String, String> result){
		this.ID = Integer.parseInt(result.get("ID"));
		this.message = result.get("message");
		this.fame_handle = result.get("fame_health").charAt(0);
		this.fame_number = Integer.parseInt(result.get("fame_number"));
		this.health_handle = result.get("health_handle").charAt(0);
		this.health_number = Integer.parseInt(result.get("health_number"));
		this.effect_ID = Integer.parseInt(result.get("effect_ID"));
		this.effect_handle = result.get("effect_handle").charAt(0);
		this.effect_number = Double.parseDouble(result.get("effect_number"));
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
