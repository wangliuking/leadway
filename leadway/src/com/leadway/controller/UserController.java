package com.leadway.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.leadway.common.EmailTools;
import com.leadway.common.WebConstant;
import com.leadway.dao.AddressDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.Address;
import com.leadway.dto.User;

@Controller
@RequestMapping("customer")
public class UserController {
	UserDao dao = null;
	/**
	 * 用户注册
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register")
	public Map<String,Object> register(User user,String code){
		Map<String,Object> map = new HashMap<>();
		//获取ServletAPI
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		//从Session中获取验证码
		String oldVcode =(String)request.getSession().getAttribute(AuthCodeAction.AUTH_CODE);
		//进行验证码判断
		if(code == null || ! oldVcode.equals(code)){
			map.put("tip", "验证码不正确");
			map.put("status", 1);
		}else{
		//发送激活邮件
		String activeCode = String.valueOf(System.currentTimeMillis());
		String activeURL = "http://localhost:9999/leadway/customer/active?userId="+user.getUserId()+"&activeCode="+activeCode;
		StringBuilder content = new StringBuilder();
		content.append(user.getUserId()+" 亲,您好，欢迎您使用我们商城，请点击下面的链接进行帐号激活:<br/>");
		content.append("<a href='"+activeURL+"'>"+activeURL+"</a>");
		boolean flag = EmailTools.send(user.getEmail(), "路标汽车商城-注册激活", content.toString());
		if(flag){
			dao = new UserDao();
			//创建帐号的时间
			user.setCreateDate(new Date());
			user.setActiveCode(activeCode);
			System.out.println("--register-----"+user);
			dao.save(user);
		}else{
			map.put("tip", "邮件发送失败");
		}
		}
		return map;
		
	}
	/**
	 * 用户激活
	 * @param userId
	 * @param activeCode
	 * @return
	 */
	@RequestMapping("/active")
	public String active(String userId,String activeCode,Model data){
		dao = new UserDao();
		Map<String,String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("activeCode", activeCode);
		int count = dao.activeUser(map);
		String tip = true?"帐号激活成功":"帐号激活失败";
		data.addAttribute("tip",tip);
		return "login";
	}
	/**
	 * 检查注册用户名是否重复
	 * @param userId
	 * @return
	 */
	@RequestMapping("/userIdCheck")
	@ResponseBody
	public Map<String,Object> userIdCheck(String userId){
		dao = new UserDao();
		boolean flag = dao.getUserByUserId(userId);
		Map<String,Object> map = new HashMap<>();
		//status等于 1 表示重复，0表示不重复
		map.put("status", (flag?1:0));
		return map;
	}
	
	@RequestMapping("/registerPage")
	public String registerPage(){
		
		return "register";
	}
	@RequestMapping("/loginPage")
	public String loginPage(){
		
		return "login";
	}
	
	/**
	 * 根据id查询用户信息
	 */
	@RequestMapping(value="/getUserById")
	public String getUserById(HttpSession session,Model data){
		UserDao userDao = new UserDao();
		User user = (User) session.getAttribute(WebConstant.SESSION_USER);
		User users = userDao.getUserById(user.getId());
		data.addAttribute("users", users);
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		// 查询导航栏
		List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
		data.addAttribute("typeList", typeList);
		// 查询导航栏下拉类型
		List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
		data.addAttribute("navList", navList);
		return "user";
	}
	
	/**
	 * 根据id更新用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String update(User user,Model data){
		UserDao userDao = new UserDao();
		userDao.updateUser(user);
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		// 查询导航栏
		List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
		data.addAttribute("typeList", typeList);
		// 查询导航栏下拉类型
		List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
		data.addAttribute("navList", navList);
		return "redirect:/customer/getUserById";
	}
	
	/**
	 * 根据用户名查询收货地址
	 */
	@RequestMapping(value="/getAddressById")
	public String getAddressById(HttpSession session,Model data){
		User user = (User) session.getAttribute(WebConstant.SESSION_USER);
		AddressDao addressDao = new AddressDao();
		List<Address> addressList = addressDao.findUserAddress(user.getUserId());
		data.addAttribute("addressList", addressList);
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		// 查询导航栏
		List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
		data.addAttribute("typeList", typeList);
		// 查询导航栏下拉类型
		List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
		data.addAttribute("navList", navList);
		return "address";
	}
	
	/**
	 * 添加收货地址
	 */
	@RequestMapping(value="/addUserAddress")
	public String addUserAddress(Address address,Model data,HttpSession session){
		User user = (User) session.getAttribute(WebConstant.SESSION_USER);
		address.setUserId(user.getUserId()); 
		AddressDao addressDao = new AddressDao();
		addressDao.addUserAddress(address);
		return "redirect:/customer/getAddressById";
	}
	
	/**
	 * 根据id删除收货地址
	 */
	@RequestMapping(value="/deleteUserAddress")
	public String deleteUserAddress(HttpServletRequest req,HttpServletResponse resp){
		int id = Integer.parseInt(req.getParameter("id"));
		AddressDao addressDao = new AddressDao();
		addressDao.deleteUserAddress(id);
		return "redirect:/customer/getAddressById";
	}
	
	
	
	
}
