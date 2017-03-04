package indi.zhuhai.service;

import indi.zhuhai.pojo.Player;
import indi.zhuhai.pojo.Ranking_list;

public interface Ranking_listService {
	public Ranking_list[] getRanking_list();
	public void insertNewWinnerByName(Player player);
}
