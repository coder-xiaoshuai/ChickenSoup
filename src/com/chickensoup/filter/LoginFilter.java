package com.chickensoup.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.chickensoup.base.ResultBean;
import com.chickensoup.utils.StringUtils;
import com.google.gson.Gson;

public class LoginFilter extends StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		 System.out.println("----------过滤前-------------");  
		 
	        HttpServletRequest req = (HttpServletRequest) request;  
	        HttpServletResponse resp = (HttpServletResponse) response;
	        req.setCharacterEncoding("utf-8");
	        Map<String, String[]> params=req.getParameterMap();
	        System.out.println("----参数长度--"+params.size());
	        System.out.println("----请求方式----"+req.getMethod());
	        System.out.println("-----url-------"+req.getRequestURI());
	        System.out.println("----userToken----"+request.getParameter("userToken"));
	        System.out.println("----content----"+request.getParameter("content"));
	        System.out.println("----content----"+req.getParameter("content"));
	        System.out.println("----createUserName----"+req.getParameter("createUserName"));
	        if(StringUtils.isEmpty(req.getParameter("userToken"))){
	        	ResultBean rb=new ResultBean<>();
	        	rb.setCode(400);
	        	rb.setMsg("你还未登录");
	        	rb.setData("{}");
	        	
	        	response.setContentType("application/json;charset=utf-8");
	        	String jsonData=new Gson().toJson(rb);
	    		try {
	    			PrintWriter out=response.getWriter();
	    			out.println(jsonData);
	    			out.flush();
	    			out.close();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		return;
	        }
	        arg2.doFilter(request, response);
		
	}


}
