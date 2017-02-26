package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * �洢Event_money���е��е�����
 */

public class Event_money_data {
	private int ID;
	private String message; //�¼���Ϣ
	private char fame_handle; //�Ƚ������Ĳ�����
	private int fame_number; //�Ƚ���������ֵ
	private char health_handle; //�ȽϽ���ֵ�Ĳ�����
	private int health_number; //�ȽϽ���ֵ����ֵ
	private int effect_ID; //Ӱ�����ƷID
	private char effect_handle; //Ӱ�����Ʒʹ�õĲ�����
	private double effect_number; //Ӱ�����Ʒ����ֵ
	
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
