package indi.zhuhai.dao;

import indi.zhuhai.pojo.Player;

public interface PlayerDao {
    int deleteByPrimaryKey(String name);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
    
    Player validatePassword(String name);
}