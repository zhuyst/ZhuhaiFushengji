package indi.zhuhai.service;

import indi.zhuhai.pojo.Item;

public interface ItemService {
	public Item getItemByID(int item_ID);
	public void setItemPrice(int item_ID,char handle,int number);
	public void backStartPrice(int item_ID);
}
