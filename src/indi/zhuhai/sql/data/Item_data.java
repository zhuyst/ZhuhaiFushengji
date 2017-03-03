package indi.zhuhai.sql.data;

import java.util.Map;

import indi.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * �洢Item���е��е�����
 */

public class Item_data extends Data_filedname{
	private int ID;
	private String name; //��Ʒ����
	private String introduce; //��Ʒ����
	private int price; //��Ʒ��ʵʱ�۸�
	private int start_price; //��Ʒ�ĳ�ʼ�۸�
	private char effect_handle; //Ӱ�������Ĳ�����
	private int effect_number; //������������ֵ
	
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
