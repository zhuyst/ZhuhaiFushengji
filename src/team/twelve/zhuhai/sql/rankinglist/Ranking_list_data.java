package team.twelve.zhuhai.sql.rankinglist;

/**
 * @author zhuyst
 * �洢Ranking_list���е��е�����
 */

public class Ranking_list_data {
	private String name; //��ҵ��û���
	private int point; //����
	
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
