package team.twelve.zhuhai.sql.pill;

import java.util.Map;

import team.twelve.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * 存储Pill表中单行的数据
 */

public class Pill_data extends Data_filedname{
	private String type;
	private int health;
	private int price;
	
	public Pill_data(){
		
	}
	
	public Pill_data(Map<String, String> result){
		this.type = result.get("type");
		this.health = Integer.parseInt(result.get("health"));
		this.price = Integer.parseInt(result.get("price"));
	}
	
	public int getHealth() {
		return health;
	}
	
	public String getType() {
		return type;
	}
	
	public int getPrice() {
		return price;
	}
}
