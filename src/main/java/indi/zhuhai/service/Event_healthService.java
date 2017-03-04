package indi.zhuhai.service;

import indi.zhuhai.pojo.Event_health;

public interface Event_healthService {
	public Event_health getEvent_healthByID(int ID);
	public void activeEvent(String username,int event_ID);
}
