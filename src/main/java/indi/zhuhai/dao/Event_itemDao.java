package indi.zhuhai.dao;

import indi.zhuhai.pojo.Event_item;

public interface Event_itemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Event_item record);

    int insertSelective(Event_item record);

    Event_item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Event_item record);

    int updateByPrimaryKey(Event_item record);
}