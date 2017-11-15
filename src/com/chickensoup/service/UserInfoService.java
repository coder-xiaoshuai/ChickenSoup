package com.chickensoup.service;

import com.chickensoup.bean.UserInfo;
import com.chickensoup.dao.UserInfoDao;

public class UserInfoService {
	public UserInfo getUserInfoById(int userId) {
		return new UserInfoDao().getUserInfoById(userId);
	}

	public void updateUserInfo(UserInfo userInfo) {
		new UserInfoDao().updateUserInfo(userInfo);
	}

	public void insertUserInfo(UserInfo userInfo) {
		new UserInfoDao().insertUserInfo(userInfo);
	}
}
