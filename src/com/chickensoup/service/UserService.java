package com.chickensoup.service;

import com.chickensoup.bean.User;
import com.chickensoup.dao.UserDao;

public class UserService {
	public User getUserByAccount(String account){
		return new UserDao().getUserByAccount(account);
	}
	public int insertUser(User user){
		System.out.println("-----����ǰuserid-------"+user.getUserId());
		new UserDao().insertUser(user);
		System.out.println("-----�����userid-------"+user.getUserId());
		return user.getUserId();
	}
}
