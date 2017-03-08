package indi.zhuhai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.zhuhai.dao.PlayerItemDao;
import indi.zhuhai.pojo.PlayerItem;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.service.GlobalService;
import indi.zhuhai.service.PlayerItemService;

@Service("playerItemService")
public class PlayerItemServiceImpl implements PlayerItemService{
	@Autowired
	private PlayerItemDao playerItemDao;
	@Autowired
	private GlobalService globalService;
	
	@Override
	public PlayerItem getPlayerItem(int player_id, int item_id) {
		return playerItemDao.selectOneItem(player_id, item_id);
	}
	
	@Override
	public int getItemNumber(int player_id, int item_id) {
		return this.getPlayerItem(player_id, item_id).getNumber();
	}
	
	@Override
	public PlayerItem[] getPlayerAllItem(int player_id) {
		List<PlayerItem> list = playerItemDao.selectAllItem(player_id);
		int item_number = globalService.getNumberByVariable(Global_enum.Item_number);
		PlayerItem[] data = new PlayerItem[item_number];
		for(int i = 0;i < item_number;i++){
			data[i] = list.get(i);
		}
		return data;
	}

	@Override
	public void insertNewItem(int player_id, int item_id) {
		PlayerItem playerItem = new PlayerItem();
		playerItem.setPlayerId(player_id);
		playerItem.setItemId(item_id);
		playerItem.setNumber(0);
		playerItemDao.insert(playerItem);
	}

	@Override
	public void updateNumber(int player_id, int item_id, int number) {
		PlayerItem playerItem = this.getPlayerItem(player_id, item_id);
		playerItem.setNumber(number);
		playerItemDao.updateByPrimaryKey(playerItem);
	}
	
}
