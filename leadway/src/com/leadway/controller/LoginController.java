package com.leadway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.leadway.common.WebConstant;
import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.PicDao;
import com.leadway.dao.TimeDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.Goods;
import com.leadway.dto.Picturcarousel;
import com.leadway.dto.Timelimited;
import com.leadway.dto.User;

/**
 * 登录处理类
 */
@Controller
@RequestMapping("/customer")
public class LoginController {
	
	/**
	 * ajax登录
	 * @param userId
	 * @param password
	 * @param vcode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loginAjax")
	public Map<String,Object> login(String userId,String password,String vcode){
		Map<String,Object> map = new HashMap<>();
		//获取ServletAPI
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		//从Session中获取验证码
		String oldVcode =(String)request.getSession().getAttribute(AuthCodeAction.AUTH_CODE);
		//进行验证码判断
		if(vcode == null || ! oldVcode.equals(vcode)){
			map.put("tip", "验证码不正确");
			map.put("status", 1);
		}else{
			//创建dao对象
			UserDao userDao = new UserDao();
			User u = new User();
			u.setUserId(userId);
			u.setPassword(password);
			//查询登录用户是否存在
			User user = userDao.getUserByUserIdAndByPassword(u);			
			if(user != null){
				//存在将用户添加到session中方便后面使用
				request.getSession().setAttribute(WebConstant.SESSION_USER, user);
				if(user.getRole() == 2 || user.getRole() == 1){
					map.put("tip", "后台登录成功");
					map.put("status", 4);
				}else{
					map.put("tip", "登录成功");
					map.put("status", 0);
				}
			}else{
				map.put("tip", "用户名或密码不正确！");
				map.put("status", 3);
			}
			
		/*
		 * 	登录成功 = 0
			验证码不正确 = 1
			账号或密码不能为空 = 2
			账号或密码格式不正确 = 3
			用户还未激活 = 4
		 */
		}
		return map;
	}
	/**
	 * 用户注销处理方法
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(Goods goods,Model data,HttpSession session){
		//用户名注销（退出）
		session.invalidate();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("goods",goods);
		//获取轮播数据
		PicDao picDao = new PicDao();
		List<Picturcarousel> picList = picDao.getPicList();
		data.addAttribute("picList", picList);
		
		//获取抢购数据
		TimeDao timeDao = new TimeDao();
		List<Timelimited> timeList = timeDao.getTimeList();
		data.addAttribute("timeList", timeList);
		//查询热卖商品
		GoodsDao goodsDao = new GoodsDao();
		List<Goods> goodsSale = goodsDao.getGoodsSale(params);
		data.addAttribute("goodsSale",goodsSale);	
		//查询夏日必备
		List<Goods> goodsSummer = goodsDao.getGoodsSummer(params);
		data.addAttribute("goodsSummer",goodsSummer);	
		//查询导航栏
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		List<Map<String,String>> typeList = goodsTypeDao.getGoodsMaxTypes();
		data.addAttribute("typeList",typeList);
		//查询导航栏下拉类型
		List<Map<String,String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
		data.addAttribute("navList", navList);
		return "index";
	}
}
