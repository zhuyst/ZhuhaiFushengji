package indi.zhuhai.sql.controller;

import java.util.Map;

import indi.zhuhai.sql.JDBC;
import indi.zhuhai.sql.dataenum.Global_enum;

/**
 * ȫ�ֱ���
 * @author zhuyst
 * ��Global��Ŀ�����
 */

public class Global_controll{
	private JDBC mysql;
	
	public Global_controll(JDBC jdbc){
		this.mysql = jdbc;
	}
	
	//��ȡ����
	public int get_data(Global_enum global_enum){
		String sql = "select " + global_enum + " from global";
		Map<String, String> result = mysql.select(sql, new String[]{global_enum.toString()});
		return Integer.parseInt(result.get(global_enum.toString()));
	}
	
	//��������
	public void update_data(Global_enum global_enum,String data){
		String sql = "update global set " + global_enum + "='" + data + "'";
		mysql.update(sql);
	}
}
