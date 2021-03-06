package com.chickensoup.dao;

import java.util.List;

import com.chickensoup.bean.ChickenSoup;
import com.chickensoup.bean.CommonParameter;
import com.chickensoup.interfaces.ChickenSoupInterface;
import com.chickensoup.utils.MyBatisUtils;

public class ChickenSoupDao implements ChickenSoupInterface{

	@Override
	public void insertChickenSoup(ChickenSoup chickenSoup) {
		MyBatisUtils.getSession().insert("com.chickensoup.mapping.chickenSoupMapper.insertChickSoup",chickenSoup);
	}

	@Override
	public List<ChickenSoup> getAllChickenSoup(CommonParameter commonParameter) {
		List<ChickenSoup> list=MyBatisUtils.getSession().selectList("com.chickensoup.mapping.chickenSoupMapper.getAllChickenSoup",commonParameter);
		return list;
	}

	@Override
	public List<ChickenSoup> getHotChickenSoup(CommonParameter commonParameter) {
		List<ChickenSoup> list=MyBatisUtils.getSession().selectList("com.chickensoup.mapping.chickenSoupMapper.getHotChickenSoup",commonParameter);
		return list;
	}

}
