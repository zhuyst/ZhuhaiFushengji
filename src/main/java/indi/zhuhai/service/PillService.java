package indi.zhuhai.service;

import indi.zhuhai.pojo.Pill;
import indi.zhuhai.pojoenum.Pill_enum;

public interface PillService {
	public Pill getPillByType(Pill_enum pill_enum);
}
