package indi.zhuhai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import indi.zhuhai.dao.PlayerDao;
import indi.zhuhai.pojo.Apartment;
import indi.zhuhai.pojo.Item;
import indi.zhuhai.pojo.Pill;
import indi.zhuhai.pojo.Player;
import indi.zhuhai.pojoenum.Apartment_enum;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.pojoenum.Pill_enum;
import indi.zhuhai.service.ApartmentService;
import indi.zhuhai.service.GlobalService;
import indi.zhuhai.service.ItemService;
import indi.zhuhai.service.PillService;
import indi.zhuhai.service.PlayerService;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService{
	@Resource
	private PlayerDao playerDao;
	@Resource
	private GlobalService globalService;
	@Resource
	private ItemService itemService;
	@Resource
	private ApartmentService apartmentService;
	@Resource
	private PillService pillService;

	@Override
	public Player getPlayerByName(String username) {
		return this.playerDao.selectByPrimaryKey(username);
	}
	
	@Override
	public void setPlayer(Player player) {
		this.playerDao.updateByPrimaryKeySelective(player);
	}
	
	@Override
	public boolean createNewPlayer(String username, String password) {
		if(!this.validateDuplicateName(username)){
			Player player = new Player();
			player.setName(username);
			player.setPassword(password);
			
			int newID = globalService.getNumberByVariable(Global_enum.Player_number) + 1;
			player.setId(newID);
			
			this.playerDao.insertSelective(player);
			return true;
		}
		else return false;
	}

	@Override
	public boolean validatePassword(String username, String password) {
		Player player = this.getPlayerByName(username);
		if(player == null) return false;
		else return true;
	}

	@Override
	public boolean validateDuplicateName(String username) {
		Player player = this.getPlayerByName(username);
		if(player != null) return true;
		else return false;
	}

	@Override
	public void buyItem(String name, int item_ID, int buy_number, int item_price) {
		Player player = this.getPlayerByName(name);
		player.setItem(item_ID, player.getItem(item_ID) + buy_number);
		player.setMoney(player.getMoney() - item_price * buy_number);
		player.setApartmentItemNumber(player.getApartmentItemNumber() + buy_number);
		playerDao.updateByPrimaryKeySelective(player);
	}

	@Override
	public void sellItem(String name, int item_ID, int sell_number, int item_price) {
		Player player = this.getPlayerByName(name);
		player.setItem(item_ID, player.getItem(item_ID) - sell_number);
		player.setMoney(player.getMoney() + item_price * sell_number);
		player.setApartmentItemNumber(player.getApartmentItemNumber() - sell_number);
		
		Item item = itemService.getItemByID(item_ID);
		char effectHandle = item.getEffectHandle().charAt(0);
		int effectNumber =item.getEffectNumber();
		
		if(effectHandle == '+'){
			player.setFame(player.getFame() + effectNumber * sell_number);
		}
		else if(effectHandle == '-'){
			player.setFame(player.getFame() - effectNumber * sell_number);
		}
		playerDao.updateByPrimaryKeySelective(player);
	}

	@Override
	public void depositMoney(String name, int number) {
		Player player = this.getPlayerByName(name);
		player.setDeposit(player.getDeposit() + number);
		player.setMoney(player.getMoney() - number);
		playerDao.updateByPrimaryKeySelective(player);
	}

	@Override
	public void withdrawMoney(String name, int number) {
		Player player = this.getPlayerByName(name);
		player.setDeposit(player.getDeposit() - number);
		player.setMoney(player.getMoney() + number);
		playerDao.updateByPrimaryKeySelective(player);
	}

	@Override
	public void payDebt(String name, int number) {
		Player player = this.getPlayerByName(name);
		player.setDebt(player.getDebt() - number);
		player.setMoney(player.getMoney() - number);
		playerDao.updateByPrimaryKeySelective(player);
	}

	@Override
	public void buyApartment(String name, Apartment_enum apartment_enum) {
		Player player = this.getPlayerByName(name);
		Apartment apartment = apartmentService.getApartmentByType(apartment_enum);
		player.setApartmentItemMax(player.getApartmentItemMax() + apartment.getSpec());
		player.setMoney(player.getMoney() - apartment.getPrice());
		playerDao.updateByPrimaryKeySelective(player);
	}

	@Override
	public void eatPill(String name, Pill_enum pill_enum) {
		Player player = this.getPlayerByName(name);
		Pill pill = pillService.getPillByType(pill_enum);
		player.setHealth(player.getHealth() + pill.getHealth());
		player.setMoney(player.getMoney() - pill.getPrice());
		playerDao.updateByPrimaryKeySelective(player);
	}

	@Override
	public void restart(String name) {
		Player player = new Player();
		player.setMoney(3000);
		player.setDeposit(0);
		player.setDebt(5000);
		player.setDay(0);
		player.setFame(100);
		player.setHealth(100);
		player.setApartmentItemNumber(0);
		player.setApartmentItemMax(100);
		for(int i = 1;i <= globalService.getNumberByVariable(Global_enum.Item_number);i++){
			player.setItem(i, 0);
		}
		this.playerDao.updateByPrimaryKey(player);
	}

}
