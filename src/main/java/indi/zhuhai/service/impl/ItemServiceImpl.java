package indi.zhuhai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.zhuhai.dao.ItemDao;
import indi.zhuhai.pojo.Item;
import indi.zhuhai.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemDao itemDao;

	@Override
	public Item getItemByID(int item_ID) {
		return this.itemDao.selectByPrimaryKey(item_ID);
	}

	@Override
	public void setItemPrice(int item_ID,char handle,int number) {
		Item item = this.itemDao.selectByPrimaryKey(item_ID);
		int price = item.getPrice();
		switch (handle) {
		case '+':
			price += number;
			break;
		case '-':
			price -= number;
			break;
		case '*':
			price *= number;
			break;
		case '/':
			price /= number;
			break;
		}
		item.setPrice(price);
		this.itemDao.updateByPrimaryKeySelective(item);
	}

	@Override
	public void backStartPrice(int item_ID) {
		Item item = this.itemDao.selectByPrimaryKey(item_ID);
		item.setPrice(item.getStartPrice());
		this.itemDao.updateByPrimaryKeySelective(item);
	}
	
}
