package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * �洢Apartment���е��е�����
 */
public class Apartment_data {
	private String type; //�����ݵĴ�С
	private int spec; //�����ݵ�����
	private int price; //��������������۸�
	
	public Apartment_data(String type,int spec,int price){
		this.type = type;
		this.spec = spec;
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getSpec() {
		return spec;
	}
	
	public String getType() {
		return type;
	}
}
