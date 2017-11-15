package com.chickensoup.dao;

import com.chickensoup.bean.UserInfo;
import com.chickensoup.interfaces.UserInfoInterface;
import com.chickensoup.utils.MyBatisUtils;

public class UserInfoDao implements UserInfoInterface{

	@Override
	public UserInfo getUserInfoById(int userId) {
		UserInfo userInfo=MyBatisUtils.getSession().selectOne("com.chickensoup.mapping.userMapper.getUserInfoById",userId);
		return userInfo;
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		MyBatisUtils.getSession().update("com.chickensoup.mapping.userMapper.updateUserInfo",userInfo);
	}

	@Override
	public void insertUserInfo(UserInfo userInfo) {
		MyBatisUtils.getSession().insert("com.chickensoup.mapping.userMapper.insertUserInfo", userInfo);
	}

}
