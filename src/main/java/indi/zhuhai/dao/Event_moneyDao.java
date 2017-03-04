package indi.zhuhai.dao;

import indi.zhuhai.pojo.Event_money;

public interface Event_moneyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Event_money record);

    int insertSelective(Event_money record);

    Event_money selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Event_money record);

    int updateByPrimaryKey(Event_money record);
}