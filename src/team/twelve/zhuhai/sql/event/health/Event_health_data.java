package team.twelve.zhuhai.sql.event.health;

/**
 * @author zhuyst
 * �洢Event_health���е��е�����
 */

public class Event_health_data {
	private int ID;
	private String message; //�¼���Ϣ
	private int effect_number; //�¼�����ʱ���ٵĽ���ֵ
	
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
