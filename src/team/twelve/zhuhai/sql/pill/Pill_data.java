package team.twelve.zhuhai.sql.pill;

/**
 * @author zhuyst
 * 存储Pill表中单行的数据
 */

public class Pill_data {
	private String type;
	private int health;
	private int price;
	
	public Pill_data(String type,int health,int price){
		this.type = type;
		this.health = health;
		this.price = price;
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
