package com.leadway.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.leadway.common.PageModel;
import com.leadway.common.WebConstant;
import com.leadway.dao.OrderDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.Order;
import com.leadway.dto.User;

@Controller
@RequestMapping("/admin/user")
public class UsersAction {
	UserDao userDao = null;
	
	/**
	 * 分页模型对象
	 */
	PageModel pageModel = new PageModel();
	/**
	 * 分页查询用户
	 * @param user
	 * @param data
	 * @return
	 */
	@RequestMapping("/userList")
	public String getOrderList(User user,@RequestParam(name="pageIndex",defaultValue="0")int pageIndex,Model data){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("user",user);
		userDao = new UserDao();
		int recordCount = userDao.count(params);
		if(recordCount != 0){
			//设置总共有多个条记录（方便进行分页计算）
			pageModel.setRecordCount(recordCount);
			//设置当前页
			pageModel.setPageIndex(pageIndex);
			
			params.put("pageModel", pageModel);
			List<User> userList = userDao.getPageUser(params);
			
			data.addAttribute("pageModel", pageModel);
			data.addAttribute("userList", userList);
			
		}
		return "user/userList";
	}
	
	/**
	 * 删除操作
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(int id){
		userDao = new UserDao();
		userDao.deleteUser(id);
		
		return "forward:/admin/user/userList";
	}
	
	/**
	 * 跳转到编辑页面
	 * @param id
	 * @param data
	 * @return
	 */
	@RequestMapping("/showUpdate")
	public String showUpdate(int id,Model data){
		userDao = new UserDao();
		User user = userDao.getUserById(id);
		data.addAttribute("user", user);
		return "user/updateUser";
	}
	/**
	 * 用户更新
	 * @param user
	 * @return
	 */
	@RequestMapping("/update")
	public String update(User user){
		userDao = new UserDao();
		userDao.updateUser(user);
		return "redirect:/admin/user/userList";
	}
	
	
}
