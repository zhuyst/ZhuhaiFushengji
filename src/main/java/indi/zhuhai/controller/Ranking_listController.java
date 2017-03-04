package indi.zhuhai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojo.Ranking_list;
import indi.zhuhai.service.Ranking_listService;

@Controller
@RequestMapping("/rankinglist")
public class Ranking_listController extends C2_JSON{
	@Resource
	private Ranking_listService ranking_listService;
	
	@RequestMapping(path = "/getrankinglist", method = RequestMethod.POST)
	public String getRankingList(HttpServletRequest request){
		Ranking_list[] data = ranking_listService.getRanking_list();
		final int LIST_NUMBER = data[0].getPoint();
		final int DATA_NUMBER = LIST_NUMBER * 2;
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < LIST_NUMBER;i++){
			strings[i] = data[i + 1].getName();
			strings[i + LIST_NUMBER] = "" + data[i + 1].getPoint();
		}
		
		return getJson_string(strings);
	}
}
