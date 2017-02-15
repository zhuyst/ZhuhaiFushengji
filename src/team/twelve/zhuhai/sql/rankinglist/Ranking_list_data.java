package team.twelve.zhuhai.sql.rankinglist;

/**
 * @author zhuyst
 * 存储Ranking_list表中单行的数据
 */

public class Ranking_list_data {
	private String name; //玩家的用户名
	private int point; //分数
	
	public Ranking_list_data(String name,int point){
		this.name = name;
		this.point = point;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPoint() {
		return point;
	}
}
