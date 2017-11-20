package com.chickensoup.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.chickensoup.base.ResultBean;
import com.chickensoup.bean.ChickenSoup;
import com.chickensoup.service.ChickenSoupService;
import com.chickensoup.utils.ActionUtils;
import com.chickensoup.utils.StringUtils;
import com.opensymphony.xwork2.ActionSupport;


public class PublishChickenSoupAction extends ActionSupport{
	/**
	 * 参数相关
	 */
	private String content;
	private int createUserId;
	private String createUserName;
	
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

	public void publish(){
		System.out.println("===========进入发布流程===========");
		// 第一步判断请求方式
		HttpServletRequest request = ServletActionContext.getRequest();
		ResultBean rb = new ResultBean<>();
		rb.setCode(400);
		// 先放空数据
		rb.setData("{}");
		if ("GET".equals(request.getMethod())) {
			rb.setMsg("发布鸡汤不支持get方式访问");
			ActionUtils.returnData(rb);
			return;
		}
		System.out.println("--------接收到的content----------"+content);
		if(!StringUtils.isEmpty(content)){
			//插入到数据库
			ChickenSoup chickenSoup=new ChickenSoup();
			chickenSoup.setContent(content);
			chickenSoup.setCreateUserId(createUserId);
			chickenSoup.setCreateUserName(createUserName);
			chickenSoup.setCreateTime(new Timestamp(System.currentTimeMillis()));
			chickenSoup.setCommentCount(0);
			chickenSoup.setPraiseCount(0);
			chickenSoup.setDissCount(0);
			
			ChickenSoupService chickenSoupService=new ChickenSoupService();
			chickenSoupService.publish(chickenSoup);
			rb.setCode(200);
			rb.setMsg("发布成功!");
			ActionUtils.returnData(rb);
			
		}else{
			rb.setMsg("说好的鸡汤呢!");
			ActionUtils.returnData(rb);
		}
	}
}
