package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * 存储Item表中单行的数据
 */

public class Item_data {
	private int ID;
	private String name; //物品名称
	private String introduce; //物品介绍
	private int price; //物品的实时价格
	private int start_price; //物品的初始价格
	private char effect_handle; //影响名誉的操作符
	private int effect_number; //减少名誉的数值
	
	public Item_data(int ID,String name,String introduce,int price,int start_price,char effect_handle,int effect_number) {
		this.ID = ID;
		this.name = name;
		this.introduce = introduce;
		this.price = price;
		this.start_price = start_price;
		this.effect_handle = effect_handle;
		this.effect_number = effect_number;
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
