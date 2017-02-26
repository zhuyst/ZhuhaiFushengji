package indi.zhuhai.sql.data;

/**
 * @author zhuyst
 * �洢Player���е��е�����
 */

public class Player_data {
	private int ID;
	private String name; //����û���
	private int money; //��Ǯ
	private int deposit; //���д�� 
	private int debt; //��ծ
	private int day; //����������
	private int fame; //����
	private int health; //����
	private int apartment_item_number; //�������д�ŵ���Ʒ��
	private int apartment_item_max; //�����ݵ��������
	private int[] item; //����Ʒ���û���
	
	public Player_data(int ID,String name,int money,Integer deposit,int debt,int day,int fame,int health,int apartment_item_number,
			int apartment_item_max,int[] item,int item_number){
		this.ID = ID;
		this.name = name;
		this.money = money;
		this.deposit = deposit;
		this.debt = debt;
		this.day = day;
		this.fame = fame;
		this.health = health;
		this.apartment_item_number = apartment_item_number;
		this.apartment_item_max = apartment_item_max;
		this.item = new int[item_number];
		System.arraycopy(item, 0, this.item, 0, item_number);
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getDeposit() {
		return deposit;
	}
	
	public int getDebt() {
		return debt;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getFame() {
		return fame;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getApartment_item_number() {
		return apartment_item_number;
	}
	
	public int getApartment_item_max() {
		return apartment_item_max;
	}
	
	public int[] getItem() {
		return item;
	}
}
