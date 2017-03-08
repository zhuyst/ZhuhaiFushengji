package indi.zhuhai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.zhuhai.dao.GlobalDao;
import indi.zhuhai.pojo.Global;
import indi.zhuhai.pojoenum.Global_enum;
import indi.zhuhai.service.GlobalService;

@Service("globalService")
public class GlobalServiceImpl implements GlobalService{
	@Autowired
	private GlobalDao globalDao;

	@Override
	public int getNumberByVariable(Global_enum global_enum) {
		return this.globalDao.selectByPrimaryKey(global_enum.toString()).getNumber();
	}

	@Override
	public void setNumberByVariable(Global_enum global_enum, int newNumber) {
		Global global = this.globalDao.selectByPrimaryKey(global_enum.toString());
		global.setNumber(newNumber);
		this.globalDao.updateByPrimaryKeySelective(global);
	}
	
	
}
