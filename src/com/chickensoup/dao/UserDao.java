package com.chickensoup.dao;

import com.chickensoup.bean.User;
import com.chickensoup.interfaces.UserInterface;
import com.chickensoup.utils.MyBatisUtils;

public class UserDao implements UserInterface{
	/**
	 * 根据账号获取用户
	 * @param account
	 * @return
	 */
	@Override
	public User getUserByAccount(String account){
		User user=MyBatisUtils.getSession().selectOne("com.chickensoup.mapping.userMapper.getUserByAccount",account);
		return user;
	}
	/**
	 * 根据id获取用户
	 * @param userId
	 * @return
	 */
	@Override
	public User getUserById(int userId){
		User user=MyBatisUtils.getSession().selectOne("com.chickensoup.mapping.userMapper.getUserById",userId);
		return user;
	}
	
	/**
	 * 删除操作暂时没有
	 */
	@Override
	public void deleteUserByAccount(String account) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 插入用户
	 */
	@Override
	public void insertUser(User user) {
		MyBatisUtils.getSession().insert("com.chickensoup.mapping.userMapper.registerUser",user);
	}


	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		MyBatisUtils.getSession().update("com.chickensoup.mapping.userMapper.updateUser",user);
	}
}
