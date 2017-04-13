package com.leadway.controller;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leadway.alipay.DirectPayService;
import com.leadway.common.PageModel;
import com.leadway.common.WebConstant;
import com.leadway.dao.AddressDao;
import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.OrderDao;
import com.leadway.dao.OrderItemDao;
import com.leadway.dto.Address;
import com.leadway.dto.Goods;
import com.leadway.dto.Order;
import com.leadway.dto.OrderItem;
import com.leadway.dto.User;
/**
 * 订单支付处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("customer")
public class OrderController {
	/**
	 * 分页模型对象
	 */
	PageModel pageModel = new PageModel();
	User u = new User();
	/**
	 * 提交订单处理方法
	 * @param goodsIdArrays
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/submitOrder")
	public String submitOrder(String goodsIdArrays,HttpSession session,Model data){
		User u = (User) session.getAttribute(WebConstant.SESSION_USER);
		if(u != null){
		double totalMoney = 0.0;
		List<Goods> goodsList = new ArrayList<>();
		Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
		if(shopCar != null){
			GoodsDao dao = new GoodsDao();
			//1,2,3
			String[] ids = goodsIdArrays.split(",");
			for(String goodsId : ids){
				
				int buyNum = shopCar.get(Integer.parseInt(goodsId));
				//根据商品的id获取商品的详细信息
				Goods goods = dao.getGoodsById(Integer.parseInt(goodsId));
				//设置商品的购买数量，并设置给商品对象
				goods.setBuyNum(buyNum);
				//统计商品的价格
				totalMoney += goods.getBuyMoney();
				goodsList.add(goods);
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		totalMoney = Double.valueOf(df.format(totalMoney));
		//为了下一个Action方法获取方法，所以存储到session中
		session.setAttribute("totalMoney", totalMoney);
		session.setAttribute("goodsList", goodsList);
		String userId = u.getUserId();
		AddressDao addressDao = new AddressDao();
		List<Address> addressList = addressDao.findUserAddress(userId);
		session.setAttribute("addressList", addressList);
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		// 查询导航栏
					List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
					data.addAttribute("typeList", typeList);
					// 查询导航栏下拉类型
					List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
					data.addAttribute("navList", navList);
		return "confirm_order";
		}else{
			return "login";
		}
	}
	
	/**
	 * 保存订单
	 * @return
	 */
	@RequestMapping(value="/saveOrder")
	public String saveOrder(HttpSession session,RedirectAttributes data){
		OrderDao orderDao = new OrderDao();
		OrderItemDao orderItemDao = new OrderItemDao();
		double totalMoney = (double)session.getAttribute("totalMoney");
		List<Goods> goodsList =(List<Goods>)session.getAttribute("goodsList");
		Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
		Order order = new Order();
		User user = (User)session.getAttribute(WebConstant.SESSION_USER);
		//将用户绑定到订单对象
		order.setUser(user);
		//订单创建时间
		order.setCreateDate(new Date());
		//订单的状态（订单状态 0:物流运输中 1:交易成功 -1交易失败）
		order.setTradingStatus(0);
		//订单金额
		order.setTotalAmount(totalMoney);
		//订单编号
		order.setOrderCode(createOrderCode(user.getId()));
		//订单支付状态（是否支付 0:未支付 1:已支付）默认是0，所以其实可以不用写
		//order.setAlipay(0);
		//保存订单
		orderDao.saveOrder(order);
		
		for(Goods goods : goodsList){
			OrderItem orderItem = new OrderItem();
			//订单明细绑定商品
			orderItem.setGoods(goods);
			//订单明细绑定订单对象
			orderItem.setOrder(order);
			//订单明细中的订单商品数量
			orderItem.setOrderNum(goods.getBuyNum());
			//保存订单明细
			orderItemDao.saveOrderItem(orderItem);
			//将订单明细与订单关联（一个订单有多个订单明细）
			order.getOrderItems().add(orderItem);
			//删除购物车中对应的商品
			shopCar.remove(goods.getId());
		}
		//存储购物车到session中
		session.setAttribute(WebConstant.SHOP_CAR, shopCar);
		session.removeAttribute("totalMoney");
		session.removeAttribute("goodsList");

		data.addFlashAttribute("order", order);
		return "redirect:/customer/orderAlipay";
	}
	
	
	/**
	 * 支付订单
	 * @return
	 */
	@RequestMapping(value="/orderAlipay")
	public ModelAndView orderAlipay(@ModelAttribute("order")Order order){
		//拼装支付需要的参数
		Map<String,String> params = DirectPayService.getRequestDataByDirectPay(order.getOrderCode(), order.getOrderItems().get(0).getGoods().getTitle(), String.valueOf(order.getTotalAmount()), order.getOrderItems().get(0).getGoods().getBrandName());
		//指定跳转支付的中转界面
		ModelAndView modelAndView = new ModelAndView("alipay/request_alipay");
		//配置支付的中转界面参数
		modelAndView.addObject("params", params);
		modelAndView.addObject("requestUrl", DirectPayService.direct_alipay_gateway);
		
		return modelAndView;
	}
	
	/**
	 * 去支付
	 * @param orderCode 订单编号
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/toAlipay")
	public String orderDetails(String orderCode,RedirectAttributes data){
		OrderDao dao = new OrderDao();
		Order order = dao.getOrderByOrderCode(orderCode);
		data.addFlashAttribute("order",order);
		return "redirect:/customer/orderAlipay";
	}
	
	/**
	 * 支付完毕，返回处理
	 * @return
	 */
	@RequestMapping(value="/directPayResult")
	public String directPayResult(String out_trade_no,String trade_no,String trade_status,String buyer_id,String total_fee){
		System.out.println("订单编号："+out_trade_no);
		System.out.println("支付宝交易流水号："+trade_no);
		System.out.println("交易状态："+trade_status);
		System.out.println("买家支付宝帐号："+buyer_id);
		System.out.println("订单金额："+total_fee);
		if(trade_status.equals("TRADE_SUCCESS") || trade_status.equals("TRADE_FINISHED")){
			OrderDao dao = new OrderDao();
			dao.updateOrderAliPayStatus(out_trade_no);
		}
		//去订单详情界面进行显示
		return "redirect:/customer/orderDetails";
	}
	
	/**
	 * 我的订单列表见面
	 * @return
	 */
	@RequestMapping(value="/orderDetails")
	public String orderDetails(Order order,@RequestParam(name="pageIndex",defaultValue="0")int pageIndex,HttpSession session,Model data){
		User user = (User)session.getAttribute(WebConstant.SESSION_USER);
		OrderDao orderDao = new OrderDao();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("order",order);
		params.put("user",user);
		int recordCount = orderDao.countByUserId(user.getId());
		if(recordCount != 0){
			//设置总共有多个条记录（方便进行分页计算）
			pageModel.setRecordCount(recordCount);
			//设置当前页
			pageModel.setPageIndex(pageIndex);
			
			params.put("pageModel", pageModel);
			List<Order> orderList = orderDao.getOrderByUserId(params);
			
			data.addAttribute("pageModel", pageModel);
			data.addAttribute("orderList", orderList);
			GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
			// 查询导航栏
			List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
			data.addAttribute("typeList", typeList);
			// 查询导航栏下拉类型
			List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
			data.addAttribute("navList", navList);
		}
		
		return "order";
	}

	/**
	 * 生成订单编号
	 * @param userId
	 * @return
	 */
	private String createOrderCode(int userId){
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyyMMddHHmmss");
		return "YYD_"+userId+"_"+sdf.format(new Date());
	}
	
	/**
	 * 根据id删除订单
	 */
	@RequestMapping(value="/deleteOrderById")
	public String deleteOrderById(HttpServletRequest req,HttpServletResponse resp){
		int orderId = Integer.parseInt(req.getParameter("id"));
		OrderItemDao orderItemDao = new OrderItemDao();
		orderItemDao.deleteOrderItemById(orderId);
		OrderDao orderDao = new OrderDao();
		orderDao.deleteOrder(orderId);
		
		return "redirect:/customer/orderDetails";
	}
	
}
