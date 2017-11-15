package com.chickensoup.interfaces;

import com.chickensoup.bean.UserInfo;

public interface UserInfoInterface {
	public UserInfo getUserInfoById(int userId);
	public void updateUserInfo(UserInfo userInfo);
	public void insertUserInfo(UserInfo userInfo);
}
