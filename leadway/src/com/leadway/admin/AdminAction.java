package com.leadway.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.jsp.JspWriter;
/**
 * 后台请求处理类
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {
	
	@RequestMapping(value="/mistake")
	public String main(){
		return "main";
	}
	/**
	 * 管理员注销处理方法
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		//用户名注销（退出）
		session.invalidate();
		return "login";
	}
	
}
