package indi.zhuhai.service;

import indi.zhuhai.pojo.PlayerItem;

public interface PlayerItemService {
	public PlayerItem getPlayerItem(int player_id,int item_id);
	public int getItemNumber(int player_id,int item_id);
	public PlayerItem[] getPlayerAllItem(int player_id);
	public void insertNewItem(int player_id,int item_id);
	public void updateNumber(int player_id,int item_id,int number);
}
