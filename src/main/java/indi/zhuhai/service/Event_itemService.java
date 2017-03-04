package indi.zhuhai.service;

import indi.zhuhai.pojo.Event_item;

public interface Event_itemService {
	public Event_item getEvent_itemByID(int ID);
	public void acitveEvent(int ID);
	public void passtiveEvent(int ID);
}
