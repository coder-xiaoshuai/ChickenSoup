package com.chickensoup.interfaces;

import java.util.List;

import com.chickensoup.bean.ChickenSoup;
import com.chickensoup.bean.CommonParameter;

public interface ChickenSoupInterface {
	void insertChickenSoup(ChickenSoup chickenSoup);
	List<ChickenSoup> getAllChickenSoup(CommonParameter commonParameter);
	List<ChickenSoup> getHotChickenSoup(CommonParameter commonParameter);
}
