package indi.zhuhai.dao;

import indi.zhuhai.pojo.Event_health;

public interface Event_healthDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Event_health record);

    int insertSelective(Event_health record);

    Event_health selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Event_health record);

    int updateByPrimaryKey(Event_health record);
}