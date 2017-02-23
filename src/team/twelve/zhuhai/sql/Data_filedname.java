package team.twelve.zhuhai.sql;

import java.lang.reflect.Field;

public abstract class Data_filedname {
	
	public String[] getFiledName(){
		Field[] field = this.getClass().getDeclaredFields();
		String[] filedName = new String[field.length];
		for(int i = 0;i < field.length;i++){
			filedName[i] = field[i].getName();
		}
		return filedName;
	}
	
}
