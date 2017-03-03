package indi.zhuhai.sql.data;

import java.util.Map;

import indi.zhuhai.sql.Data_filedname;

/**
 * @author zhuyst
 * 存储Apartment表中单行的数据
 */
public class Apartment_data extends Data_filedname{
	private String type; //出租屋的大小
	private int spec; //出租屋的容量
	private int price; //升级出租屋所需价格
	
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
