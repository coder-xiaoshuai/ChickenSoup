package com.chickensoup.action;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.chickensoup.base.ResultBean;
import com.chickensoup.bean.User;
import com.chickensoup.bean.UserInfo;
import com.chickensoup.bean.UserInfoResult;
import com.chickensoup.service.UserInfoService;
import com.chickensoup.service.UserService;
import com.chickensoup.utils.ActionUtils;
import com.chickensoup.utils.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	// ����
	private String account;
	private String password;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void login() {
		System.out.println("===========�����¼����===========");
		// ��һ���ж�����ʽ
		HttpServletRequest request = ServletActionContext.getRequest();
		ResultBean rb = new ResultBean<>();
		rb.setCode(400);
		// �ȷſ�����
		rb.setData("{}");
		if ("GET".equals(request.getMethod())) {
			rb.setMsg("��¼��֧��get��ʽ����");
			ActionUtils.returnData(rb);
			return;
		}
		// �ڶ��� ����һЩ������֤
		if (StringUtils.isEmpty(account)) {
			rb.setMsg("�ֻ��Ų���Ϊ��!");
			// ���ؿ�����
			ActionUtils.returnData(rb);
			return;
		}
		if (StringUtils.isEmpty(password)) {
			rb.setMsg("���벻��Ϊ��!");
			ActionUtils.returnData(rb);
			return;
		}
		//��������֤�û��Ƿ����
		UserService userService=new UserService();
		User user=userService.getUserByAccount(account);
		if(user==null){
			rb.setMsg("�û�������");
			ActionUtils.returnData(rb);
			return;
		}else{
			if(user.getPassword().equals(password)){
				//��¼�ɹ� �ȸ���userToken ��ȡ�û����Ϸ���
				System.out.println("----------------------account"+user.getAccount()+"password"+user.getPassword()+"userId"+user.getUserId()+"timestamp"+user.getCreateTime());
				user.setUserToken(UUID.randomUUID().toString());
				//��¼��¼ʱ��
				user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				userService.updateUser(user);
				UserInfo userInfo=new UserInfoService().getUserInfoById(user.getUserId());
				UserInfoResult result=new UserInfoResult(userInfo,user.getUserToken());
				rb.setCode(200);
				rb.setMsg("��¼�ɹ�");
				rb.setData(result);
				ActionUtils.returnData(rb);
			}else{
				rb.setMsg("�������");
				ActionUtils.returnData(rb);
			}
		}
	}

}
