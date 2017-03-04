package indi.zhuhai.dao;

import indi.zhuhai.pojo.Global;

public interface GlobalDao {
    int deleteByPrimaryKey(String variable);

    int insert(Global record);

    int insertSelective(Global record);

    Global selectByPrimaryKey(String variable);

    int updateByPrimaryKeySelective(Global record);

    int updateByPrimaryKey(Global record);
}