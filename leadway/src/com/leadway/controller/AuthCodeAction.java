package com.leadway.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leadway.common.AuthCode;

/**
 * 验证码处理类
 * @author Administrator
 *
 */
@Controller
public class AuthCodeAction {
	public static final String AUTH_CODE = "AUTH_CODE";
	private static final long serialVersionUID = 1L;
	@RequestMapping("verify")
	public void getAuthCode(HttpSession session, HttpServletResponse response) {
		//获取验证码
		String code = AuthCode.getAuthCode();
		//将验证码存储到session中，方便后面的判断
		session.setAttribute(AUTH_CODE,code);
		//获取验证码图片
		BufferedImage img = AuthCode.getCodeImg(code);
		try{
		//输出验证码图片到客户端
		ImageIO.write(img, "JPEG", response.getOutputStream());
		}catch(Exception e){
			//e.printStackTrace();
		}
	}
}
