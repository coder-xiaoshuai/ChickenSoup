package com.chickensoup.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import com.chickensoup.base.ResultBean;
import com.chickensoup.bean.User;
import com.chickensoup.bean.UserInfo;
import com.chickensoup.bean.UserInfoResult;
import com.chickensoup.service.UserInfoService;
import com.chickensoup.service.UserService;
import com.chickensoup.utils.ActionUtils;
import com.chickensoup.utils.OkhttpUtils;
import com.chickensoup.utils.OkhttpUtils.ResultCallback;
import com.chickensoup.utils.StringUtils;
import com.opensymphony.xwork2.ActionSupport;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterAction extends ActionSupport {
	private String account;
	private String password;
	private String code;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ע��
	 */
	public void register() {
		System.out.println("======������ע������=======");
		// ��һ���ж�����ʽ
		HttpServletRequest request = ServletActionContext.getRequest();
		ResultBean rb = new ResultBean<>();
		rb.setCode(200);
		// �ȷſ�����
		rb.setData("{}");
		if ("GET".equals(request.getMethod())) {
			rb.setMsg("ע�᲻֧��get��ʽ����");
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
		if (StringUtils.isEmpty(code)) {
			rb.setMsg("��֤�벻��Ϊ��!");
			ActionUtils.returnData(rb);
			return;
		}
		// �������鿴�Ƿ��Ѿ�ע����
		UserService userService = new UserService();
		if (userService.getUserByAccount(account) != null) {
			rb.setMsg("���ֻ����Ѿ�ע��");
			ActionUtils.returnData(rb);
			return;
		}
		// ���Ĳ��������� ��֤��֤���Ƿ���ȷ
		if(checkCodeIsRight(account,code)){
			// ��֤����ȷ ��ʼע���û�
			User user = new User();
			user.setAccount(account);
			user.setPassword(password);
			user.setCreateTime(new Timestamp(System.currentTimeMillis()));
			String userToken=UUID.randomUUID().toString();
			user.setUserToken(userToken);
			int userId = userService.insertUser(user);
			// ��ʼ
			UserInfoService userInfoService = new UserInfoService();
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);
			userInfo.setGender(0);
			userInfo.setUserName("��ʼ���û�"+userId);
			userInfo.setSignature("����˺�����ʲô��û������");
			userInfo.setHeadurl("http://img5.imgtn.bdimg.com/it/u=917366822,112370361&fm=27&gp=0.jpg");
			userInfoService.insertUserInfo(userInfo);
			rb.setData(new UserInfoResult(userInfo,userToken));
			rb.setMsg("ע��ɹ�!");
			ActionUtils.returnData(rb);
		}else {
			// ��֤�����
			rb.setMsg("��֤�����");
			ActionUtils.returnData(rb);
		}

	}

	/**
	 * �����֤���Ƿ���ȷ
	 * @param phone
	 * @param code
	 * @return
	 */
	private boolean checkCodeIsRight(String phone,String code){
		String url = "https://webapi.sms.mob.com/sms/verify";
		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody body=new FormBody.Builder().add("phone", phone).add("appkey", "221bd07c8a23f").add("zone","86").add("code", code).build();
		Request request = new Request.Builder()
		        .url(url)
		        .post(body)
		        .build();
		Call call = okHttpClient.newCall(request);
		try {
			Response response=call.execute();
			String result=response.body().string().toString();
			System.out.println("------��֤����֤���------" + result);
			JSONObject jsonObject=new JSONObject(result);
			if(jsonObject.getInt("status")==200){
				return true;
			}else{
				//��ʱ���ڲ���
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return false;
	}
}
