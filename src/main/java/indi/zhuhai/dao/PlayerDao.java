package indi.zhuhai.dao;

import org.apache.ibatis.annotations.Param;

import indi.zhuhai.pojo.Player;

public interface PlayerDao {
    int deleteByPrimaryKey(String name);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
    
    Player validatePassword(@Param("name")String name,@Param("password")String password);
}