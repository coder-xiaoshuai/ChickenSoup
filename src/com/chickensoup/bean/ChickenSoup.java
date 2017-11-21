package com.chickensoup.bean;

import java.sql.Timestamp;

public class ChickenSoup {
	private int chickenSoupId;
	private String content;
	private int createUserId;
	private String createUserName;
	private int praiseCount;
	private int dissCount;
	private int commentCount;
	private Timestamp createTime;
	private boolean isParise;
	public int getChickenSoupId() {
		return chickenSoupId;
	}
	public void setChickenSoupId(int chickenSoupId) {
		this.chickenSoupId = chickenSoupId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public int getDissCount() {
		return dissCount;
	}
	public void setDissCount(int dissCount) {
		this.dissCount = dissCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public boolean isParise() {
		return isParise;
	}
	public void setParise(boolean isParise) {
		this.isParise = isParise;
	}
	
}
