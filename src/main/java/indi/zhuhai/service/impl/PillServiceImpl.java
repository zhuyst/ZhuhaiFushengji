package indi.zhuhai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.zhuhai.dao.PillDao;
import indi.zhuhai.pojo.Pill;
import indi.zhuhai.pojoenum.Pill_enum;
import indi.zhuhai.service.PillService;

@Service("pillService")
public class PillServiceImpl implements PillService{
	@Autowired
	private PillDao pillDao;
	
	@Override
	public Pill getPillByType(Pill_enum pill_enum) {
		return this.pillDao.selectByPrimaryKey(pill_enum.toString());
	}
}
