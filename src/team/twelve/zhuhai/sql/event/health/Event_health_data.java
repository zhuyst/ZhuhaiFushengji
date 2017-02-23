package team.twelve.zhuhai.sql.event.health;

import java.util.Map;

import team.twelve.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * 存储Event_health表中单行的数据
 */

public class Event_health_data extends Data_filedname{
	private int ID;
	private String message; //事件信息
	private int effect_number; //事件发生时减少的健康值
	
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
