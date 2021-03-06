package indi.zhuhai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojo.Ranking_list;
import indi.zhuhai.service.Ranking_listService;

@Controller
@RequestMapping("/rankinglist")
public class Ranking_listController extends C2_JSON{
	@Autowired
	private Ranking_listService ranking_listService;
	
	@RequestMapping(path = "/getdata", method = RequestMethod.POST)
	public void getRankingList(HttpServletRequest request,HttpServletResponse response){
		Ranking_list[] data = ranking_listService.getRanking_list();
		final int LIST_NUMBER = 10;
		final int DATA_NUMBER = LIST_NUMBER * 2;
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < LIST_NUMBER;i++){
			strings[i] = data[i].getName();
			strings[i + LIST_NUMBER] = "" + data[i].getPoint();
		}
		
		getResponse(response, getJson_string(strings));
	}
}
