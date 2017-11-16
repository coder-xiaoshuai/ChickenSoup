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
	// 参数
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
		System.out.println("===========进入登录流程===========");
		// 第一步判断请求方式
		HttpServletRequest request = ServletActionContext.getRequest();
		ResultBean rb = new ResultBean<>();
		rb.setCode(400);
		// 先放空数据
		rb.setData("{}");
		if ("GET".equals(request.getMethod())) {
			rb.setMsg("登录不支持get方式访问");
			ActionUtils.returnData(rb);
			return;
		}
		// 第二步 进行一些基本验证
		if (StringUtils.isEmpty(account)) {
			rb.setMsg("手机号不能为空!");
			// 返回空数据
			ActionUtils.returnData(rb);
			return;
		}
		if (StringUtils.isEmpty(password)) {
			rb.setMsg("密码不能为空!");
			ActionUtils.returnData(rb);
			return;
		}
		//第三步验证用户是否存在
		UserService userService=new UserService();
		User user=userService.getUserByAccount(account);
		if(user==null){
			rb.setMsg("用户不存在");
			ActionUtils.returnData(rb);
			return;
		}else{
			if(user.getPassword().equals(password)){
				//登录成功 先更新userToken 获取用户资料返回
				System.out.println("----------------------account"+user.getAccount()+"password"+user.getPassword()+"userId"+user.getUserId()+"timestamp"+user.getCreateTime());
				user.setUserToken(UUID.randomUUID().toString());
				//记录登录时间
				user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				userService.updateUser(user);
				UserInfo userInfo=new UserInfoService().getUserInfoById(user.getUserId());
				UserInfoResult result=new UserInfoResult(userInfo,user.getUserToken());
				rb.setCode(200);
				rb.setMsg("登录成功");
				rb.setData(result);
				ActionUtils.returnData(rb);
			}else{
				rb.setMsg("密码错误");
				ActionUtils.returnData(rb);
			}
		}
	}

}
