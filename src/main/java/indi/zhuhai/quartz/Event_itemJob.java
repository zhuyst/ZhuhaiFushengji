package indi.zhuhai.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.service.Event_itemService;
import indi.zhuhai.service.GlobalService;

public class Event_itemJob extends QuartzJobBean{
	@Autowired
	private GlobalService globalService;
	@Autowired
	private Event_itemService event_itemService;
	
	private int active_number = 0;  //目前激活的事件的ID

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		active_number = globalService.getNumberByVariable(Global_enum.Event_item_active_ID);
		int event_number = globalService.getNumberByVariable(Global_enum.Event_item_number);
		int number = (int)(1 + Math.random() * event_number);
		
		if(active_number != 0){
			event_itemService.passtiveEvent(active_number);
		}
		event_itemService.acitveEvent(number);
		active_number = number;
		globalService.setNumberByVariable(Global_enum.Event_item_active_ID, active_number);
	}
}
