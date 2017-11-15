package com.chickensoup.dao;

import com.chickensoup.bean.User;
import com.chickensoup.interfaces.UserInterface;
import com.chickensoup.utils.MyBatisUtils;

public class UserDao implements UserInterface{
	/**
	 * �����˺Ż�ȡ�û�
	 * @param account
	 * @return
	 */
	@Override
	public User getUserByAccount(String account){
		User user=MyBatisUtils.getSession().selectOne("com.chickensoup.mapping.userMapper.getUserByAccount",account);
		return user;
	}

	
	/**
	 * ɾ��������ʱû��
	 */
	@Override
	public void deleteUserByAccount(String account) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * �����û�
	 */
	@Override
	public void insertUser(User user) {
		MyBatisUtils.getSession().insert("com.chickensoup.mapping.userMapper.registerUser",user);
	}
}