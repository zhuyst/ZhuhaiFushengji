package indi.zhuhai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojo.Player;
import indi.zhuhai.pojoenum.Apartment_enum;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.pojoenum.Pill_enum;
import indi.zhuhai.service.GlobalService;
import indi.zhuhai.service.ItemService;
import indi.zhuhai.service.PlayerService;
import indi.zhuhai.service.Ranking_listService;

@Controller
@RequestMapping("/player")
public class PlayerController extends C2_JSON{
	@Resource
	private PlayerService playerService;
	@Resource
	private Ranking_listService ranking_listService;
	@Resource
	private GlobalService globalService;
	@Resource
	private ItemService itemService;
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request){
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		
		Boolean ok = playerService.validatePassword(username, password);
		if(ok == true) return "1";
		else return "0";
	}
	
	@RequestMapping(path = "/signin", method = RequestMethod.POST)
	public String siginin(HttpServletRequest request){
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		
		Boolean ok = playerService.createNewPlayer(username, password);
		if(ok == true) return "1";
		else return "0";
	}
	
	@RequestMapping(path = "/getdata", method = RequestMethod.POST)
	public String getData(HttpServletRequest request){
		String username = request.getParameter("name");
		Player player = playerService.getPlayerByName(username);
		final int DATA_NUMBER = 9;
		
		String[] strings = new String[DATA_NUMBER];
		
		strings[0] = player.getName();
		strings[1] = "" + player.getMoney();
		strings[2] = "" + player.getDay();
		strings[3] = "" + player.getDebt();
		strings[4] = "" + player.getDeposit();
		strings[5] = "" + player.getHealth();
		strings[6] = "" + player.getFame();
		strings[7] = "" + player.getApartmentItemNumber();
		strings[8] = "" + player.getApartmentItemMax();
		
		if(player.getDay() == 40) 
			ranking_listService.insertNewWinnerByName(player);
		return getJson_string(strings);
	}
	
	@RequestMapping(path = "/getitem", method = RequestMethod.POST)
	public String getPlayerItem(HttpServletRequest request){
		String username = request.getParameter("name");
		Player player = playerService.getPlayerByName(username);
		final int ITEM_NUMBER = globalService.getNumberByVariable(Global_enum.Item_number);
		final int DATA_NUMBER = ITEM_NUMBER * 2;
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < ITEM_NUMBER;i++){
			strings[i] = itemService.getItemByID(i + 1).getName();
			strings[i + ITEM_NUMBER] = "" + player.getItem(i + 1);
		}
		
		return getJson_string(strings);
	}
	
	@RequestMapping(path = "/eatpill", method = RequestMethod.POST)
	public String eatPill(HttpServletRequest request){
		String username = request.getParameter("name");
		String pilltype = request.getParameter("pilltype");
		
		Pill_enum pill_enum = Pill_enum.valueOf(pilltype);
		playerService.eatPill(username, pill_enum);
		return "1";
	}
	
	@RequestMapping(path = "/depositmoney", method = RequestMethod.POST)
	public String depositMoney(HttpServletRequest request){
		String username = request.getParameter("name");
		int number = Integer.parseInt(request.getParameter("number"));
		
		playerService.depositMoney(username, number);
		return "1";
	}
	
	@RequestMapping(path = "/paydebt", method = RequestMethod.POST)
	public String payDebt(HttpServletRequest request){
		String username = request.getParameter("name");
		int number = Integer.parseInt(request.getParameter("number"));
		
		playerService.payDebt(username, number);
		return "1";
	}
	
	@RequestMapping(path = "/withdrawmoney", method = RequestMethod.POST)
	public String withdrawMoney(HttpServletRequest request){
		String username = request.getParameter("name");
		int number = Integer.parseInt(request.getParameter("number"));
		
		playerService.withdrawMoney(username, number);
		return "1";
	}
	
	@RequestMapping(path = "/buyitem", method = RequestMethod.POST)
	public String buyItem(HttpServletRequest request){
		String username = request.getParameter("name");
		int item_ID = Integer.parseInt(request.getParameter("itemID"));
		int buy_number = Integer.parseInt(request.getParameter("number"));
		int item_price = Integer.parseInt(request.getParameter("itemPrice"));
		
		playerService.buyItem(username, item_ID, buy_number, item_price);
		return "1";
	}
	
	@RequestMapping(path = "/sellitem", method = RequestMethod.POST)
	public String sellItem(HttpServletRequest request){
		String username = request.getParameter("name");
		int item_ID = Integer.parseInt(request.getParameter("itemID"));
		int buy_number = Integer.parseInt(request.getParameter("number"));
		int item_price = Integer.parseInt(request.getParameter("itemPrice"));
		
		playerService.sellItem(username, item_ID, buy_number, item_price);
		return "1";
	}
	
	@RequestMapping(path = "/buyapartment", method = RequestMethod.POST)
	public String buyApartment(HttpServletRequest request){
		String username = request.getParameter("name");
		String type = request.getParameter("type");
		
		Apartment_enum apartment_enum = Apartment_enum.valueOf(type);
		playerService.buyApartment(username, apartment_enum);
		return "1";
	}
	
	@RequestMapping(path = "/restart", method = RequestMethod.POST)
	public String restart(HttpServletRequest request){
		String username = request.getParameter("name");
		playerService.restart(username);
		return "1";
	}
}
