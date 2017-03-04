package indi.zhuhai.dao;

import java.util.List;

import indi.zhuhai.pojo.Ranking_list;

public interface Ranking_listDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ranking_list record);

    int insertSelective(Ranking_list record);

    Ranking_list selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ranking_list record);

    int updateByPrimaryKey(Ranking_list record);
    
    List<Ranking_list> selectDescList();
}