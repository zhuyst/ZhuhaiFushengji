package indi.zhuhai.service;

import indi.zhuhai.pojo.Apartment;
import indi.zhuhai.pojoenum.Apartment_enum;

public interface ApartmentService {
	public Apartment getApartmentByType(Apartment_enum apartment_enum);
}
