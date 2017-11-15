package com.chickensoup.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {
	private static SqlSession session=null;
	
	public static SqlSession getSession(){
		if(session==null){
			String resource="conf.xml";
			InputStream inputStream=null;
			try {
				inputStream = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
	                .build(inputStream);
	        session = sqlSessionFactory.openSession();
		}
		return session;
	}
}
