package indi.zhuhai.dao;

import indi.zhuhai.pojo.Apartment;

public interface ApartmentDao {
    int deleteByPrimaryKey(String type);

    int insert(Apartment record);

    int insertSelective(Apartment record);

    Apartment selectByPrimaryKey(String type);

    int updateByPrimaryKeySelective(Apartment record);

    int updateByPrimaryKey(Apartment record);
}