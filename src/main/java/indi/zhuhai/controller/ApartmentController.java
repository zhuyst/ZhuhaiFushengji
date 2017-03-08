package indi.zhuhai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import indi.zhuhai.pojoenum.Apartment_enum;
import indi.zhuhai.service.ApartmentService;

@Controller
@RequestMapping("/apartment")
public class ApartmentController extends C2_JSON{
	@Autowired
	private ApartmentService apartmentService;
	
	@RequestMapping(path = "/getdata", method = RequestMethod.POST)
	public void getData(HttpServletRequest request,HttpServletResponse response){
		final int DATA_NUMBER = 3;
		
		String[] strings = new String[DATA_NUMBER];
		
		strings[0] = "" + apartmentService.getApartmentByType(Apartment_enum.small).getPrice();
		strings[1] = "" + apartmentService.getApartmentByType(Apartment_enum.medium).getPrice();
		strings[2] = "" + apartmentService.getApartmentByType(Apartment_enum.big).getPrice();
		
		getResponse(response, getJson_string(strings));
	}
}
