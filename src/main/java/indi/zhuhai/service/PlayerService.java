package indi.zhuhai.service;

import indi.zhuhai.pojo.Player;
import indi.zhuhai.pojoenum.Apartment_enum;
import indi.zhuhai.pojoenum.Pill_enum;

public interface PlayerService {
	public Player getPlayerByName(String username);
	public void setPlayer(Player player);
	public boolean createNewPlayer(String username,String password);
	public boolean validatePassword(String username,String password);
	public boolean validateDuplicateName(String username);
	public void buyItem(String name,int item_ID,int buy_number,int item_price);
	public void sellItem(String name,int item_ID,int sell_number,int item_price);
	public void depositMoney(String name,int number);
	public void withdrawMoney(String name,int number);
	public void payDebt(String name,int number);
	public void buyApartment(String name,Apartment_enum apartment_enum);
	public void eatPill(String name,Pill_enum pill_enum);
	public void restart(String name);
}
