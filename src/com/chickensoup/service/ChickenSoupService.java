package com.chickensoup.service;

import com.chickensoup.bean.ChickenSoup;
import com.chickensoup.dao.ChickenSoupDao;

public class ChickenSoupService {
	
	public void publish(ChickenSoup chickenSoup){
		new ChickenSoupDao().insertChickenSoup(chickenSoup);
	}
}
