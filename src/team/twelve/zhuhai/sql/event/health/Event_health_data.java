package team.twelve.zhuhai.sql.event.health;

import java.util.Map;

import team.twelve.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * �洢Event_health���е��е�����
 */

public class Event_health_data extends Data_filedname{
	private int ID;
	private String message; //�¼���Ϣ
	private int effect_number; //�¼�����ʱ���ٵĽ���ֵ
	
	public Event_health_data(){
		
	}
	
	public Event_health_data(Map<String, String> result){
		this.ID = Integer.parseInt(result.get("ID"));
		this.message = result.get("message");
		this.effect_number = Integer.parseInt(result.get("effect_number"));
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
