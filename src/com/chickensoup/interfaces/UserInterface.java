package com.chickensoup.interfaces;

import com.chickensoup.bean.User;
/**
 * �涨���û�����Щ����
 * @author 54095
 *
 */
public interface UserInterface {
	User getUserByAccount(String account);
	public User getUserById(int userId);
	void deleteUserByAccount(String account);
	void insertUser(User user);
	void updateUser(User user);
}
