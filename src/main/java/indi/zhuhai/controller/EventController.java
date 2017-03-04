package indi.zhuhai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojo.Player;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.service.Event_healthService;
import indi.zhuhai.service.Event_itemService;
import indi.zhuhai.service.Event_moneyService;
import indi.zhuhai.service.GlobalService;
import indi.zhuhai.service.PlayerService;

@Controller
@RequestMapping("/event")
public class EventController {
	@Resource
	private PlayerService playerService;
	@Resource
	private Event_moneyService event_moneyService;
	@Resource
	private Event_healthService event_healthService;
	@Resource
	private Event_itemService event_itemService;
	@Resource
	private GlobalService globalService;
	
	@RequestMapping(path = "/getdailymessage", method = RequestMethod.POST)
	public String dailyEvent(String username){
		String event_message = null;
		Player player = playerService.getPlayerByName(username);
		
		player.setDay(player.getDay() + 1);
		player.setDebt((int)(player.getDebt() * 1.1));
		player.setDeposit((int)(player.getDeposit() * 1.1));
		
		int event_type = (int)(1+Math.random()*100);
		
		if(event_type >= 1 && event_type <= player.getFame()){  //��������ò�/ʧ���¼�
			Boolean can_active = false;
			int number = 0;

			int event_number = globalService.getNumberByVariable(Global_enum.Event_money_number);
			for(int i = 1;!can_active;i++){
				number = (int)(1 + Math.random() * event_number);
				can_active = event_moneyService.checkActiveCondition(username, number);
				if(can_active && i <= event_number){
					event_moneyService.activeEvent(username, number);
				}
				else {
					event_moneyService.activeEvent(username, number);
					break;
				}
			}
			event_message = event_moneyService.getEvent_moneyByID(number).getMessage();
		}
		
		else {  //�������������¼�
			int event_number = globalService.getNumberByVariable(Global_enum.Event_health_number);
			int number = (int)(1 + Math.random() * event_number);
			event_healthService.activeEvent(username, number);
			event_message = event_healthService.getEvent_healthByID(number).getMessage();
		}
		
		return event_message;
	}
	
	@RequestMapping(path = "/getitemmessage", method = RequestMethod.POST)
	public String getEvent_itemMessage(HttpServletRequest request){
		int active_number = globalService.getNumberByVariable(Global_enum.Event_item_active_ID);
		return event_itemService.getEvent_itemByID(active_number).getMessage();
	}
}