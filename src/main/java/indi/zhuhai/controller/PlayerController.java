package indi.zhuhai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojo.Player;
import indi.zhuhai.pojoenum.Apartment_enum;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.pojoenum.Pill_enum;
import indi.zhuhai.service.GlobalService;
import indi.zhuhai.service.ItemService;
import indi.zhuhai.service.PlayerItemService;
import indi.zhuhai.service.PlayerService;
import indi.zhuhai.service.Ranking_listService;

@Controller
@RequestMapping("/player")
public class PlayerController extends C2_JSON{
	@Autowired
	private PlayerService playerService;
	@Autowired
	private PlayerItemService playerItemService;
	@Autowired
	private Ranking_listService ranking_listService;
	@Autowired
	private GlobalService globalService;
	@Autowired
	private ItemService itemService;
	
	private static Logger logger = Logger.getLogger(PlayerController.class);
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		
		Boolean ok = playerService.validatePassword(username, password);
		String result = "";
		if(ok == true) {
			logger.info(username + " login success");
			result = "1";
		}
		else {
			logger.info(username + " login fail");
			result = "0";
		}
		getResponse(response, result);
	}
	
	@RequestMapping(path = "/signin", method = RequestMethod.POST)
	public void siginin(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		
		Boolean ok = playerService.createNewPlayer(username, password);
		String result = "";
		if(ok == true) {
			logger.info(username + " signin success");
			result = "1";
		}
		else {
			logger.info(username + " signin fail");
			result = "0";
		}
		getResponse(response, result);
	}
	
	@RequestMapping(path = "/getdata", method = RequestMethod.POST)
	public void getData(HttpServletRequest request,HttpServletResponse response){
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
		getResponse(response, getJson_string(strings));
	}
	
	@RequestMapping(path = "/getitem", method = RequestMethod.POST)
	public void getPlayerItem(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		Player player = playerService.getPlayerByName(username);
		final int ITEM_NUMBER = globalService.getNumberByVariable(Global_enum.Item_number);
		final int DATA_NUMBER = ITEM_NUMBER * 2;
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < ITEM_NUMBER;i++){
			strings[i] = itemService.getItemByID(i + 1).getName();
			strings[i + ITEM_NUMBER] = "" + playerItemService.getItemNumber(player.getId(), i + 1);
		}
		
		getResponse(response, getJson_string(strings));
	}
	
	@RequestMapping(path = "/eatpill", method = RequestMethod.POST)
	public void eatPill(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		String pilltype = request.getParameter("type");
		
		Pill_enum pill_enum = Pill_enum.valueOf(pilltype);
		playerService.eatPill(username, pill_enum);
		getResponse(response, "1");
	}
	
	@RequestMapping(path = "/depositmoney", method = RequestMethod.POST)
	public void depositMoney(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		int number = Integer.parseInt(request.getParameter("number"));
		
		playerService.depositMoney(username, number);
		getResponse(response, "1");
	}
	
	@RequestMapping(path = "/paydebt", method = RequestMethod.POST)
	public void payDebt(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		int number = Integer.parseInt(request.getParameter("number"));
		
		playerService.payDebt(username, number);
		getResponse(response, "1");
	}
	
	@RequestMapping(path = "/withdrawmoney", method = RequestMethod.POST)
	public void withdrawMoney(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		int number = Integer.parseInt(request.getParameter("number"));
		
		playerService.withdrawMoney(username, number);
		getResponse(response, "1");
	}
	
	@RequestMapping(path = "/buyitem", method = RequestMethod.POST)
	public void buyItem(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		int item_ID = Integer.parseInt(request.getParameter("itemID"));
		int buy_number = Integer.parseInt(request.getParameter("number"));
		int item_price = Integer.parseInt(request.getParameter("itemPrice"));
		
		playerService.buyItem(username, item_ID, buy_number, item_price);
		getResponse(response, "1");
	}
	
	@RequestMapping(path = "/sellitem", method = RequestMethod.POST)
	public void sellItem(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		int item_ID = Integer.parseInt(request.getParameter("itemID"));
		int buy_number = Integer.parseInt(request.getParameter("number"));
		int item_price = Integer.parseInt(request.getParameter("itemPrice"));
		
		playerService.sellItem(username, item_ID, buy_number, item_price);
		getResponse(response, "1");
	}
	
	@RequestMapping(path = "/buyapartment", method = RequestMethod.POST)
	public void buyApartment(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		String type = request.getParameter("type");
		
		Apartment_enum apartment_enum = Apartment_enum.valueOf(type);
		playerService.buyApartment(username, apartment_enum);
		getResponse(response, "1");
	}
	
	@RequestMapping(path = "/restart", method = RequestMethod.POST)
	public void restart(HttpServletRequest request,HttpServletResponse response){
		String username = request.getParameter("name");
		playerService.restart(username);
		getResponse(response, "1");
	}
}
