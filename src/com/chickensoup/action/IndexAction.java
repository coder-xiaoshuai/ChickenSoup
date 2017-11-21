package com.chickensoup.action;

import java.util.List;

import com.chickensoup.base.ResultBean;
import com.chickensoup.bean.ChickenSoup;
import com.chickensoup.bean.CommonParameter;
import com.chickensoup.bean.IndexFirstPageBean;
import com.chickensoup.service.ChickenSoupService;
import com.chickensoup.utils.ActionUtils;
import com.chickensoup.utils.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport{
	private int startId;
	private int page;
	private int pageCount;
	private int type;//0��ȫ�� 1������
	private String userToken;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	

	public int getStartId() {
		return startId;
	}

	public void setStartId(int startId) {
		this.startId = startId;
	}

	public void getIndexData(){
		System.out.println("===========��ȡ��ҳ����===========");
		System.out.println("-------������ȡ���--------page"+page+",pageCount"+pageCount+",type"+type+",userToken"+userToken);
		ResultBean rb = new ResultBean<>();
		rb.setCode(400);
		// �ȷſ�����
		rb.setData("{}");
		CommonParameter commonParameter=new CommonParameter();
		if(pageCount==0){
			//Ĭ��10��
			commonParameter.setPageCount(10);
		}else{
			commonParameter.setPageCount(pageCount);
		}
		if(!StringUtils.isEmpty(userToken)){
			commonParameter.setUserToken(userToken);
		}
		if(startId==0){
			commonParameter.setStartId(0);
		}else{
			commonParameter.setStartId(startId);
		}
		if(page==0){
			//����page Ĭ����1
			page=1;
		}
		commonParameter.setStartOffset((page-1)*pageCount);
		List<ChickenSoup> listChickenSoup=null;
		if(type==0){
			//ȫ��
			listChickenSoup=new ChickenSoupService().getAllData(commonParameter);
			
		}else if(type==1){
			//����
			listChickenSoup=new ChickenSoupService().getHotData(commonParameter);
		}
		if(listChickenSoup!=null&&listChickenSoup.size()>0){
			if(page==1){
				//��ȡ���ǵ�һҳ������ ��Ҫ������һҳ��һ��Id
				IndexFirstPageBean firstPage=new IndexFirstPageBean();
				firstPage.setList(listChickenSoup);
				firstPage.setNextPageStartId(listChickenSoup.get(0).getChickenSoupId());
				rb.setData(firstPage);
			}else{
				rb.setData(listChickenSoup);
			}
		}else{
			rb.setData("[]");
		}
		
		rb.setCode(200);
		rb.setMsg("�������ݳɹ�");
		ActionUtils.returnData(rb);
		
		
	}
}
