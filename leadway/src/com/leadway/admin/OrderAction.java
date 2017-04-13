package com.leadway.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.leadway.common.PageModel;
import com.leadway.dao.OrderDao;
import com.leadway.dao.OrderItemDao;
import com.leadway.dao.TimeDao;
import com.leadway.dto.Goods;
import com.leadway.dto.Order;
import com.leadway.dto.OrderItem;

/**
 * 订单管理Action处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/order/")
public class OrderAction {
	OrderDao orderDao = null;
	OrderItemDao orderItemDao = null;
	/**
	 * 分页模型对象
	 */
	PageModel pageModel = new PageModel();
	/**
	 * 查询所有订单操作
	 * @param data
	 * @return
	 */
	@RequestMapping("/orderList")
	public String getOrderList(Order order,@RequestParam(name="pageIndex",defaultValue="0")int pageIndex,Model data){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("order",order);
		orderDao = new OrderDao();
		int recordCount = orderDao.count(params);
		if(recordCount != 0){
			//设置总共有多个条记录（方便进行分页计算）
			pageModel.setRecordCount(recordCount);
			//设置当前页
			pageModel.setPageIndex(pageIndex);
			
			params.put("pageModel", pageModel);
			List<Order> orderList = orderDao.getPageOrder(params);
			
			data.addAttribute("pageModel", pageModel);
			data.addAttribute("orderList", orderList);
			
		}
		return "order/orderList";
	}
	
	/**
	 * 显示订单明细
	 * @return
	 */
	@RequestMapping("/showDetail")
	public String getOrderItem(int id ,Model data){
		
		orderItemDao = new OrderItemDao();
		
		List<OrderItem> orderItemList = orderItemDao.getOrderItem(id);
		
		data.addAttribute("orderItemList", orderItemList);
		
		return "order/orderItem";
	}
	
	/**
	 * 订单删除
	 * @param id
	 */
	
	@RequestMapping("/delete")
	public String deleteOrder(int id){
		orderDao = new OrderDao();
		orderDao.deleteOrder(id);
		
		return "forward:/admin/order/orderList";
	}
}
