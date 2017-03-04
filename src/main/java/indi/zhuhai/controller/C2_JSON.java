package indi.zhuhai.controller;

import com.alibaba.fastjson.JSONArray;

public abstract class C2_JSON {
	protected String getJson_string(String[] data){
		String json_string = "{\"c2array\":true,\"size\":[";
		json_string += String.valueOf(data.length);
		json_string += ",1,1],\"data\":";
		
		JSONArray jsonArray_1 = new JSONArray();
		JSONArray[] jsonArray_2 = new JSONArray[data.length];
		JSONArray[] jsonArray_3 = new JSONArray[data.length];
		for(int i = 0;i < data.length;i++){
			jsonArray_2[i] = new JSONArray();
			jsonArray_3[i] = new JSONArray();
		}
		
		for(int i = 0; i < data.length;i++){
			jsonArray_3[i].add(data[i]);
			jsonArray_2[i].add(jsonArray_3[i]);
			jsonArray_1.add(jsonArray_2[i]);
		}
		
		json_string += jsonArray_1.toString() + "}";
		
		return json_string;
	}
}
