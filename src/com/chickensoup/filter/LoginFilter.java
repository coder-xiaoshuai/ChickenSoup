package com.chickensoup.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import com.chickensoup.base.ResultBean;
import com.chickensoup.utils.StringUtils;
import com.google.gson.Gson;

public class LoginFilter extends StrutsPrepareAndExecuteFilter{

//	@Override
//	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
//			throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		super.doFilter(arg0, arg1, arg2);
//		
//	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		 System.out.println("----------¹ýÂËÇ°-------------");  
		 
	        HttpServletRequest req = (HttpServletRequest) request;  
	        HttpServletResponse resp = (HttpServletResponse) response; 
	        System.out.println("-----url-------"+req.getRequestURI());
	        System.out.println("----userToken----"+req.getParameter("userToken"));
	        System.out.println("----userToken----"+request.getParameter("userToken"));
	        System.out.println("----content----"+request.getParameter("content"));
	        System.out.println("----content----"+req.getParameter("content"));
	        System.out.println("----createUserName----"+req.getParameter("createUserName"));
	        if(StringUtils.isEmpty(req.getParameter("userToken"))){
	        	ResultBean rb=new ResultBean<>();
	        	rb.setCode(400);
	        	rb.setMsg("Äã»¹Î´µÇÂ¼");
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
