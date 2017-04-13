package com.leadway.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.leadway.common.WebConstant;
import com.leadway.dto.User;
/**
 * 登录检查拦截器
 * @author Administrator
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	/**
	 * 在Controller类调用之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		//获取Session
		HttpSession session = request.getSession();
		//获取Session中存储的登录用户数据
		User user = (User)session.getAttribute(WebConstant.SESSION_USER);
		//判断用户数据是否存在
		if(user != null){
			System.out.println("-------用户存在-------");
			//继续执行Controller请求
			return true;
		}else{
			System.out.println("-------用户  不  存在-------");
			//用户没有登录，重新登录
			response.sendRedirect(request.getContextPath()+"/admin");
			return false;
		}
	}
}
