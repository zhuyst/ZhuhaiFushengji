package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * 存储Apartment表中单行的数据
 */
public class Apartment_data {
	private String type; //出租屋的大小
	private int spec; //出租屋的容量
	private int price; //升级出租屋所需价格
	
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
