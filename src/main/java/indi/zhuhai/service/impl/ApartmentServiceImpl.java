package indi.zhuhai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import indi.zhuhai.dao.ApartmentDao;
import indi.zhuhai.pojo.Apartment;
import indi.zhuhai.pojoenum.Apartment_enum;
import indi.zhuhai.service.ApartmentService;

@Service("apartmentService")
public class ApartmentServiceImpl implements ApartmentService{
	@Resource
	private ApartmentDao apartmentDao;
	
	@Override
	public Apartment getApartmentByType(Apartment_enum apartment_enum) {
		return this.apartmentDao.selectByPrimaryKey(apartment_enum.toString());
	}
	
}
