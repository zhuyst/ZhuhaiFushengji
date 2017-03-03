package indi.zhuhai.sql.data;

import java.util.Map;

import indi.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * 存储Item表中单行的数据
 */

public class Item_data extends Data_filedname{
	private int ID;
	private String name; //物品名称
	private String introduce; //物品介绍
	private int price; //物品的实时价格
	private int start_price; //物品的初始价格
	private char effect_handle; //影响名誉的操作符
	private int effect_number; //减少名誉的数值
	
	public Item_data(){
		
	}
	
	public Item_data(Map<String, String> result) {
		this.ID = Integer.parseInt(result.get("ID"));
		this.name = result.get("Name");
		this.introduce = result.get("Introduce");
		this.price = Integer.parseInt(result.get("Price"));
		this.start_price = Integer.parseInt(result.get("Start_Price"));
		this.effect_handle = result.get("Effect_handle").charAt(0);
		this.effect_number = Integer.parseInt(result.get("Effect_number"));
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIntroduce() {
		return introduce;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getStart_price() {
		return start_price;
	}
	
	public char getEffect_handle() {
		return effect_handle;
	}
	
	public int getEffect_number() {
		return effect_number;
	}
}
