package indi.zhuhai.service;

import indi.zhuhai.pojo.Event_money;

public interface Event_moneyService {
	public Event_money getEvent_moneyByID(int ID);
	public boolean checkActiveCondition(String username,int event_ID);
	public void activeEvent(String username,int event_ID);
}
