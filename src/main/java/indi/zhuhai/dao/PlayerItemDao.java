package indi.zhuhai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.zhuhai.pojo.PlayerItem;

public interface PlayerItemDao {
    int insert(PlayerItem record);

    int insertSelective(PlayerItem record);
    
    PlayerItem selectOneItem(@Param("playerid")int player_id,@Param("itemid")int item_id);
    
    List<PlayerItem> selectAllItem(int player_id);

    int updateByPrimaryKey(PlayerItem record);
    
    int deleteOneItem(@Param("playerid")int player_id,@Param("itemid")int item_id);
    
    int deleteAllItem(int player_id);
}