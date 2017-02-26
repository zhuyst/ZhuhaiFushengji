package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * �洢Item���е��е�����
 */

public class Item_data {
	private int ID;
	private String name; //��Ʒ����
	private String introduce; //��Ʒ����
	private int price; //��Ʒ��ʵʱ�۸�
	private int start_price; //��Ʒ�ĳ�ʼ�۸�
	private char effect_handle; //Ӱ�������Ĳ�����
	private int effect_number; //������������ֵ
	
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
