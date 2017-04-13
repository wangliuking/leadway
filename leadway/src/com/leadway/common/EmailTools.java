package com.leadway.common;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送工具类
 * @author Administrator
 *
 */
public class EmailTools {
	/**
	 * 邮件发送方法
	 * @param address 邮件接收人
	 * @param subject 邮件的标题
	 * @param content 邮件的内容
	 * @return true 表示成功，false 表示失败
	 */
	public static boolean send(String address,String subject,String content){
		//1:创建Session
		Properties pro = new Properties();
		//邮件协议
		pro.put("mail.transport.protocol", "smtp");
		//邮件端口
		pro.put("mail.host","smtp.163.com");
		//邮件发送人
		pro.put("mail.from", "13551889779@163.com");
		//相当于的客户端与邮件服务器的连接对象
		Session session = Session.getDefaultInstance(pro);
		//开启调试模式（正式发布项目的时候设置为false）
		session.setDebug(true);

		try {
			//2:获取邮件发送对象
			Transport transport = session.getTransport();
			//设置发送人邮件帐号(参数1是邮件的登录名，参数2是邮件的授权码[需要去邮件服务器设置pop/stmp协议才有])
			transport.connect("13551889779", "wlk2011");
			//3:创建邮件消息体
			MimeMessage message = new MimeMessage(session);
			//设置邮件的标题
			message.setSubject(subject);
			//设置邮件的内容和页面编码
			message.setContent(content,"text/html;charset=UTF-8");
			//4:发送邮件
			transport.sendMessage(message, InternetAddress.parse(address));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
