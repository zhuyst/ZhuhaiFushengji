package indi.zhuhai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import indi.zhuhai.dao.PillDao;
import indi.zhuhai.pojo.Pill;
import indi.zhuhai.pojoenum.Pill_enum;
import indi.zhuhai.service.PillService;

@Service("pillService")
public class PillServiceImpl implements PillService{
	@Resource
	private PillDao pillDao;
	
	@Override
	public Pill getPillByType(Pill_enum pill_enum) {
		return this.pillDao.selectByPrimaryKey(pill_enum.toString());
	}
}
