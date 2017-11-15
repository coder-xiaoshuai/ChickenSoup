package com.chickensoup.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

public class ActionUtils {
	private static Gson gson;
	/**
	 * 返回数据
	 * @param jsonData
	 */
	public static void returnData(String jsonData){
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out=response.getWriter();
			out.println(jsonData);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 将实体类先转成json字符串再返回
	 * @param t
	 */
	public static <T> void returnData(T t){
		if(gson==null){
			gson=new Gson();
		}
		String jsonData=gson.toJson(t);
		returnData(jsonData);
	}
}
