package indi.zhuhai.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import indi.zhuhai.dao.Ranking_listDao;
import indi.zhuhai.pojo.Player;
import indi.zhuhai.pojo.Ranking_list;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.service.GlobalService;
import indi.zhuhai.service.Ranking_listService;

@Service("ranking_listService")
public class Ranking_listServiceImpl implements Ranking_listService{
	@Resource
	private Ranking_listDao ranking_listDao;
	@Resource
	private GlobalService globalService;
	
	@Override
	public Ranking_list[] getRanking_list() {
		final int LIST_NUMBER_MAX = 10;
		int list_number = 0;
		int winner_number = globalService.getNumberByVariable(Global_enum.Winner_number);
		if(winner_number >= LIST_NUMBER_MAX) list_number = LIST_NUMBER_MAX;
		else list_number = winner_number;
		Ranking_list[] data = new Ranking_list[list_number];
		List<Ranking_list> list = ranking_listDao.selectDescList();
		for(int i = 0;i < LIST_NUMBER_MAX;i++){
			data[i] = list.get(i);
		}
		return data;
	}
	@Override
	public void insertNewWinnerByName(Player player) {
		int new_ID = globalService.getNumberByVariable(Global_enum.Winner_number) + 1;
		int money = player.getMoney();
		int deposit = player.getDeposit();
		int debt = player.getDebt();
		int point = money + deposit - debt;
		
		Ranking_list record = new Ranking_list();
		record.setId(new_ID);
		record.setName(player.getName());
		record.setPoint(point);
		ranking_listDao.insert(record);
	}
}
