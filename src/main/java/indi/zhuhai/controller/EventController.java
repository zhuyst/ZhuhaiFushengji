package indi.zhuhai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
public class EventController extends C2_JSON{
	@Autowired
	private PlayerService playerService;
	@Autowired
	private Event_moneyService event_moneyService;
	@Autowired
	private Event_healthService event_healthService;
	@Autowired
	private Event_itemService event_itemService;
	@Autowired
	private GlobalService globalService;
	
	@RequestMapping(path = "/getdailymessage", method = RequestMethod.POST)
	public void dailyEvent(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		String event_message = null;
		Player player = playerService.getPlayerByName(username);
		
		player.setDay(player.getDay() + 1);
		player.setDebt((int)(player.getDebt() * 1.1));
		player.setDeposit((int)(player.getDeposit() * 1.1));
		playerService.setPlayer(player);
		
		int event_type = (int)(1+Math.random()*100);
		
		if(event_type >= 1 && event_type <= player.getFame()){  //触发意外得财/失财事件
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
		
		else {  //触发健康受损事件
			int event_number = globalService.getNumberByVariable(Global_enum.Event_health_number);
			int number = (int)(1 + Math.random() * event_number);
			event_healthService.activeEvent(username, number);
			event_message = event_healthService.getEvent_healthByID(number).getMessage();
		}
		
		getResponse(response, event_message);
	}
	
	@RequestMapping(path = "/getitemmessage", method = RequestMethod.POST)
	public void getEvent_itemMessage(HttpServletRequest request,HttpServletResponse response){
		int active_number = globalService.getNumberByVariable(Global_enum.Event_item_active_ID);
		getResponse(response, event_itemService.getEvent_itemByID(active_number).getMessage());
	}
}
