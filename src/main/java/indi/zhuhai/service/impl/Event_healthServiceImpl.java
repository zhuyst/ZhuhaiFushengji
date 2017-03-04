package indi.zhuhai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import indi.zhuhai.dao.Event_healthDao;
import indi.zhuhai.pojo.Event_health;
import indi.zhuhai.pojo.Player;
import indi.zhuhai.service.Event_healthService;
import indi.zhuhai.service.PlayerService;

@Service("event_healthService")
public class Event_healthServiceImpl implements Event_healthService{
	@Resource
	private Event_healthDao event_healthDao;
	@Resource
	private PlayerService playerService;
	
	@Override
	public Event_health getEvent_healthByID(int ID) {
		return this.event_healthDao.selectByPrimaryKey(ID);
	}

	@Override
	public void activeEvent(String username, int event_ID) {
		Player player = playerService.getPlayerByName(username);
		player.setHealth(player.getHealth() - this.getEvent_healthByID(event_ID).getEffectNumber());
		this.playerService.setPlayer(player);
	}
}
