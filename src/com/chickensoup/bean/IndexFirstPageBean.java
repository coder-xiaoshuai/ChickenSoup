package com.chickensoup.bean;

import java.util.List;

public class IndexFirstPageBean {
	private List<ChickenSoup> list;
	private int nextPageStartId;
	public List<ChickenSoup> getList() {
		return list;
	}
	public void setList(List<ChickenSoup> list) {
		this.list = list;
	}
	public int getNextPageStartId() {
		return nextPageStartId;
	}
	public void setNextPageStartId(int nextPageStartId) {
		this.nextPageStartId = nextPageStartId;
	}
	
}
