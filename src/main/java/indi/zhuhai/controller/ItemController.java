package indi.zhuhai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojo.Item;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.service.GlobalService;
import indi.zhuhai.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController extends C2_JSON{
	@Resource
	private ItemService itemService;
	@Resource
	private GlobalService globalService;
	
	@RequestMapping(path = "/getlist", method = RequestMethod.POST)
	public String getItemList(HttpServletRequest request){
		final int DATA_NUMBER = 6;
		final int MAX = globalService.getNumberByVariable(Global_enum.Item_number);
		final int MIN = 1;
		int[] randomnumber = getRandonNumber(MAX,MIN,DATA_NUMBER);
		
		String[] strings = new String[DATA_NUMBER];
		
		for(int i = 0;i < DATA_NUMBER;i++){
			strings[i] = "" + randomnumber[i];
		}
		
		return getJson_string(strings);
	}
	
	@RequestMapping(path = "/getdata", method = RequestMethod.POST)
	public String getItemData(HttpServletRequest request){
		int item_ID = Integer.parseInt(request.getParameter("itemID"));
		final int DATA_NUMBER = 4;
		Item item = itemService.getItemByID(item_ID);
		
		String[] strings = new String[DATA_NUMBER];
		
		strings[0] = "" + item_ID;
		strings[1] = item.getName();
		strings[2] = item.getIntroduce();
		
		int base_price = item.getPrice();
		int max = (int)(base_price * 1.2);
		int min = (int)(base_price * 0.8);
		strings[3] = "" + (int)(min + Math.random() * (max - min + 1));
		
		return getJson_string(strings);
	}
	
	private int[] getRandonNumber(int max,int min,int n){
		int result[] = new int[n];
		int count = 0;
		while(count < n){
			int num = (int)(min + Math.random() * (max - min + 1));
			boolean flag = true;
			for(int j = 0;j < n;j++){
				if(num == result[j]){
					flag = false;
					break;
				}
			}
			if(flag){
				result[count] = num;
				count ++ ;
			}
		}
		return result; 
	}
}
