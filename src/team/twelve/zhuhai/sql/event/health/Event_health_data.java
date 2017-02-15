package team.twelve.zhuhai.sql.event.health;

/**
 * @author zhuyst
 * 存储Event_health表中单行的数据
 */

public class Event_health_data {
	private int ID;
	private String message; //事件信息
	private int effect_number; //事件发生时减少的健康值
	
	public Event_health_data(int ID,String message,int effect_number){
		this.ID = ID;
		this.message = message;
		this.effect_number = effect_number;
	}

	public int getID() {
		return ID;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getEffect_number() {
		return effect_number;
	}
}
