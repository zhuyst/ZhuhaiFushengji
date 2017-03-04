package indi.zhuhai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojo.Pill;
import indi.zhuhai.pojoenum.Pill_enum;
import indi.zhuhai.service.PillService;

@Controller
@RequestMapping("/pill")
public class PillController extends C2_JSON{
	@Resource
	private PillService pillService;
	
	@RequestMapping(path = "/getdata", method = RequestMethod.POST)
	public String getPillData(HttpServletRequest request){
		final int DATA_NUMBER = 8;
		final int PILL_NUMBER = 4;
		
		String[] strings = new String[DATA_NUMBER];
		
		Pill f_pill = pillService.getPillByType(Pill_enum.F);
		Pill w_pill = pillService.getPillByType(Pill_enum.W);
		Pill r_pill = pillService.getPillByType(Pill_enum.R);
		Pill j_pill = pillService.getPillByType(Pill_enum.J);
		
		Pill[] pill = new Pill[PILL_NUMBER];
		pill[0] = f_pill; pill[1] = w_pill;
		pill[2] = r_pill; pill[3] = j_pill;
		
		for(int i = 0;i < PILL_NUMBER;i++){
			strings[i] = "" + pill[i].getPrice();
			strings[i + PILL_NUMBER] = "" + pill[i].getHealth();
		}
		
		return getJson_string(strings);
	}
}
