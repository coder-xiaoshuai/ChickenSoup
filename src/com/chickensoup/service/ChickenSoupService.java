package com.chickensoup.service;

import java.util.List;

import com.chickensoup.bean.ChickenSoup;
import com.chickensoup.bean.CommonParameter;
import com.chickensoup.dao.ChickenSoupDao;

public class ChickenSoupService {
	
	public void publish(ChickenSoup chickenSoup){
		new ChickenSoupDao().insertChickenSoup(chickenSoup);
	}
	/**
	 * 获取全部数据
	 * @param commonParameter
	 * @return
	 */
	public List<ChickenSoup> getAllData(CommonParameter commonParameter){
		return new ChickenSoupDao().getAllChickenSoup(commonParameter);
	}
	
	/**
	 * 获取热门数据
	 * @param commonParameter
	 * @return
	 */
	public List<ChickenSoup> getHotData(CommonParameter commonParameter){
		return new ChickenSoupDao().getHotChickenSoup(commonParameter);
	}
}
