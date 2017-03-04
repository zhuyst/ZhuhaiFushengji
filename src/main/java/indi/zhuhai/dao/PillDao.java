package indi.zhuhai.dao;

import indi.zhuhai.pojo.Pill;

public interface PillDao {
    int deleteByPrimaryKey(String type);

    int insert(Pill record);

    int insertSelective(Pill record);

    Pill selectByPrimaryKey(String type);

    int updateByPrimaryKeySelective(Pill record);

    int updateByPrimaryKey(Pill record);
}