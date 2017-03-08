package indi.zhuhai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.zhuhai.dao.Event_itemDao;
import indi.zhuhai.pojo.Event_item;
import indi.zhuhai.service.Event_itemService;
import indi.zhuhai.service.ItemService;

@Service("event_itemService")
public class Event_itemServiceImpl implements Event_itemService{
	@Autowired
	private Event_itemDao event_itemDao;
	@Autowired
	private ItemService itemService;
	
	@Override
	public Event_item getEvent_itemByID(int ID) {
		return this.event_itemDao.selectByPrimaryKey(ID);
	}

	@Override
	public void acitveEvent(int ID) {
		Event_item event_item = this.getEvent_itemByID(ID);
		for(int i = 0;i < event_item.getEffectItemIdArray().length;i++){
			itemService.setItemPrice(event_item.getEffectItemIdArray()[i], event_item.getEffectHandle().charAt(0),event_item.getEffectNumber());
		}
		this.event_itemDao.updateByPrimaryKeySelective(event_item);
	}

	@Override
	public void passtiveEvent(int ID) {
		Event_item event_item = this.getEvent_itemByID(ID);
		for(int i = 0;i < event_item.getEffectItemIdArray().length;i++){
			itemService.backStartPrice(event_item.getEffectItemIdArray()[i]);
		}
		this.event_itemDao.updateByPrimaryKeySelective(event_item);
	}

}
