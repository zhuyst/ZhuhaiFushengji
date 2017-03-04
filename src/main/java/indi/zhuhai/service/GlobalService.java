package indi.zhuhai.service;

import indi.zhuhai.pojoenum.Global_enum;

public interface GlobalService {
	public int getNumberByVariable(Global_enum global_enum);
	public void setNumberByVariable(Global_enum global_enum,int newNumber);
}
