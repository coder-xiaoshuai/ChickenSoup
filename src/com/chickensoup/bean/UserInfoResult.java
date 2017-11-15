package com.chickensoup.bean;

public class UserInfoResult {
	private UserInfo userInfo;
	private String userToken;
	public UserInfoResult(){}
	
	public UserInfoResult(UserInfo userInfo, String userToken) {
		super();
		this.userInfo = userInfo;
		this.userToken = userToken;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
}
