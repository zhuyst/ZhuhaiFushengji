package indi.zhuhai.task;

import java.util.TimerTask;

import javax.annotation.Resource;

import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.service.Event_itemService;
import indi.zhuhai.service.GlobalService;

public class Event_itemTask extends TimerTask{
	@Resource
	private GlobalService globalService;
	@Resource
	private Event_itemService event_itemService;
	
	private int active_number;  //目前激活的事件的ID
	
	@Override
	public void run() {
		active_number = globalService.getNumberByVariable(Global_enum.Event_item_active_ID);
		int event_number = globalService.getNumberByVariable(Global_enum.Event_item_number);
		int number = (int)(1 + Math.random() * event_number);
		
		if(active_number != 0){
			event_itemService.passtiveEvent(event_number);
		}
		event_itemService.acitveEvent(number);
		active_number = number;
		globalService.setNumberByVariable(Global_enum.Event_item_active_ID, active_number);
	}
}
