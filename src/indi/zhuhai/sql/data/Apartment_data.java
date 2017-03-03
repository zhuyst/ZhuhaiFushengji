package indi.zhuhai.sql.data;

import java.util.Map;

import indi.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * �洢Apartment���е��е�����
 */
public class Apartment_data extends Data_filedname{
	private String type; //�����ݵĴ�С
	private int spec; //�����ݵ�����
	private int price; //��������������۸�
	
	public Apartment_data(){
		
	}
	
	public Apartment_data(Map<String, String> result){
		this.type = result.get("result");
		this.spec = Integer.parseInt(result.get("spec"));
		this.price = Integer.parseInt(result.get("price"));
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
