package indi.zhuhai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.zhuhai.dao.Event_moneyDao;
import indi.zhuhai.pojo.Event_money;
import indi.zhuhai.pojo.Player;
import indi.zhuhai.service.Event_moneyService;
import indi.zhuhai.service.PlayerItemService;
import indi.zhuhai.service.PlayerService;

@Service("event_moneyService")
public class Event_moneyServiceImpl implements Event_moneyService{
	@Autowired
	private Event_moneyDao event_moneyDao;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private PlayerItemService playerItemService;
	
	@Override
	public Event_money getEvent_moneyByID(int ID) {
		return this.event_moneyDao.selectByPrimaryKey(ID);
	}

	@Override
	public boolean checkActiveCondition(String username, int event_ID) {
		Event_money event_money = this.getEvent_moneyByID(event_ID);
		Player player = playerService.getPlayerByName(username);
		
		Boolean fame_OK = false;
		if(event_money.getFameHandle().charAt(0) == '>' && player.getFame() >= event_money.getFameNumber()){
			fame_OK = true;
		}
		else if(event_money.getFameHandle().charAt(0) == '<' && player.getFame() <= event_money.getFameNumber()){
			fame_OK = true;
		}
		
		Boolean health_OK = false;
		if(event_money.getHealthHandle().charAt(0) == '>' && player.getHealth() >= event_money.getHealthNumber()){
			health_OK = true;
		}
		else if(event_money.getHealthHandle().charAt(0) == '<' && player.getHealth() <= event_money.getHealthNumber()){
			health_OK = true;
		}
		
		return fame_OK&&health_OK;
	}

	@Override
	public void activeEvent(String username, int event_ID) {
		Event_money event_money = this.getEvent_moneyByID(event_ID);
		Player player = playerService.getPlayerByName(username);
		
		int effect_ID = event_money.getEffectId();
		if(effect_ID == -1){
			player.setMoney((int)(player.getMoney() * event_money.getEffectNumber()));
		}
		else if(effect_ID == -2){
			player.setDeposit((int)(player.getDeposit() * event_money.getEffectNumber()));
		}
		else {
			if(event_money.getEffectHandle().charAt(0) == '+'){
				if(playerItemService.getPlayerItem(player.getId(), effect_ID) == null) 
					playerItemService.insertNewItem(player.getId(), effect_ID);
				playerItemService.updateNumber(player.getId(), effect_ID, event_money.getEffectNumber().intValue());
				player.setApartmentItemNumber(player.getApartmentItemNumber() + event_money.getEffectNumber().intValue());
			}
			else if(event_money.getEffectHandle().charAt(0) == '='){
				int itemNumber = playerItemService.getItemNumber(player.getId(), effect_ID);
				playerItemService.deleteItem(player.getId(), effect_ID);
				player.setApartmentItemNumber(player.getApartmentItemNumber() - itemNumber);
			}
		}
		this.playerService.setPlayer(player);
	}

}
