package com.chickensoup.interfaces;

import com.chickensoup.bean.User;
/**
 * 规定对用户有哪些操作
 * @author 54095
 *
 */
public interface UserInterface {
	User getUserByAccount(String account);
	void deleteUserByAccount(String account);
	void insertUser(User user);
}
