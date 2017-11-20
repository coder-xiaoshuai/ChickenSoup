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
	 * �������
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
		System.out.println("===========���뷢������===========");
		// ��һ���ж�����ʽ
		HttpServletRequest request = ServletActionContext.getRequest();
		ResultBean rb = new ResultBean<>();
		rb.setCode(400);
		// �ȷſ�����
		rb.setData("{}");
		if ("GET".equals(request.getMethod())) {
			rb.setMsg("����������֧��get��ʽ����");
			ActionUtils.returnData(rb);
			return;
		}
		System.out.println("--------���յ���content----------"+content);
		if(!StringUtils.isEmpty(content)){
			//���뵽���ݿ�
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
			rb.setMsg("�����ɹ�!");
			ActionUtils.returnData(rb);
			
		}else{
			rb.setMsg("˵�õļ�����!");
			ActionUtils.returnData(rb);
		}
	}
}
