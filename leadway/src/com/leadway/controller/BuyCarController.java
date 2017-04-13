package com.leadway.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leadway.common.EmailTools;
import com.leadway.common.WebConstant;
import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.Goods;
import com.leadway.dto.User;
/**
 * 购物车处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("customer")
public class BuyCarController {
	UserDao dao = null;
	/**
	 * 添加商品到购物车中
	 * @return
	 */
	@RequestMapping("/addBuyCar")
	@ResponseBody
	public Map<String,Object> addBuyCar(int goodsId,int buyNum,String choose,HttpSession session){
		session.setAttribute(WebConstant.SESSION_SIZE, choose);
		//获取购物车对象
		Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
		//如果是第一次购买就创建购物车
		if(shopCar == null){
			/*
			 * key 就是商品id
			 * value 就是商品购买数量
			 */
			shopCar = new LinkedHashMap<>();
		}
		//判断之前是否有添加过相同的商品到购物车中
		if(shopCar.containsKey(goodsId)){
			//有就是直接添加商品数量
			int oldNum = shopCar.get(goodsId);
			shopCar.put(goodsId, oldNum+buyNum);
		}else{
			//没有就是将商品id和商品数量存储到购物车中
			shopCar.put(goodsId, buyNum);
		}
		int totalNum = 0;
		//统计购物车中商品数量
		for(Map.Entry<Integer, Integer> map : shopCar.entrySet()){
			totalNum += map.getValue();
		}
		
		//将购物车添加到session中
		session.setAttribute(WebConstant.SHOP_CAR, shopCar);
		Map<String,Object> result = new HashMap<>();
		result.put("status","0");
		result.put("totalNum",totalNum);
		return result;
	}
	/**
	 * 显示购物车中的商品信息
	 * @param session
	 * @param data
	 * @return
	 */
	@RequestMapping("/showBuyCar")
	public String showBuyCar(HttpSession session,String choose,Model data){
		
		double totalMoney = 0.0;
		List<Goods> goodsList = new ArrayList<>();
		GoodsDao dao = new GoodsDao();
		//获取购物车对象
		Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
		if(shopCar != null){
			//获取购物车中的每一个商品
			for(Map.Entry<Integer, Integer> map : shopCar.entrySet()){
				//获取商品的id
				int goodsId = map.getKey();
				//获取商品的数量
				int buyNum = map.getValue();
				//根据商品的id获取商品的详细信息
				Goods goods = dao.getGoodsById(goodsId);
				//设置商品的购买数量，并设置给商品对象
				goods.setBuyNum(buyNum);
				//统计商品的价格
				totalMoney += goods.getBuyMoney();
				//将商品存储到list集合（方便页面获取）
				goodsList.add(goods);
			}
		}
		//对最后的总计进行格式化处理
		DecimalFormat df = new DecimalFormat("0.00");
		totalMoney = Double.valueOf(df.format(totalMoney));
		data.addAttribute("totalMoney", totalMoney);
		data.addAttribute("goodsList", goodsList);
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		// 查询导航栏
		List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
		data.addAttribute("typeList", typeList);
		// 查询导航栏下拉类型
		List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
		data.addAttribute("navList", navList);
		data.addAttribute("choose",choose);
		return "shop_cart";
		
		
	}
	
	/**
	 * 删除购物车中的商品信息
	 * @param session
	 * @param data
	 * @return
	 */
	@RequestMapping("/deleteCar")
	@ResponseBody
	public Map deleteCar(int goodsId,HttpSession session,Model data){
		//获取购物车对象
		Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
		if(shopCar != null){
			shopCar.remove(goodsId);
		}
		session.setAttribute(WebConstant.SHOP_CAR, shopCar);
		int totalNum = 0;
		//统计购物车中商品数量
		for(Map.Entry<Integer, Integer> map : shopCar.entrySet()){
			totalNum += map.getValue();
		}
		Map<String,Object> result = new HashMap<>();
		result.put("status","0");
		result.put("totalNum",totalNum);
		return result;
	}
	
	/**
	 * 更新购物车中的商品信息
	 * @param session
	 * @param data
	 * @return
	 */
	@RequestMapping("/updateCar")
	@ResponseBody
	public Map updateCar(int goodsId,int buyNum,HttpSession session,Model data){
		//获取购物车对象
		Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
		int totalNum = 0;
		if(shopCar != null){
			shopCar.put(goodsId, buyNum);
			//统计购物车中商品数量
			for(Map.Entry<Integer, Integer> map : shopCar.entrySet()){
				totalNum += map.getValue();
			}
		}
		session.setAttribute(WebConstant.SHOP_CAR, shopCar);
		Map<String,Object> result = new HashMap<>();
		result.put("status","0");
		result.put("totalNum",totalNum);
		return result;
	}
	
	/**
	 * 获取购物车中商品的数量
	 * @return
	 */
	@RequestMapping("/loadAjaxShopCarNum")
	@ResponseBody
	public Map loadAjaxShopCarNum(HttpSession session){
		//获取购物车对象
		Map<Integer,Integer> shopCar = (Map<Integer,Integer>)session.getAttribute(WebConstant.SHOP_CAR);
		int totalNum = 0;
		if(shopCar != null){
			for(Map.Entry<Integer, Integer> map : shopCar.entrySet()){
				totalNum += map.getValue();
			}
		}
		Map<String,Object> result = new HashMap<>();
		result.put("status","0");
		result.put("totalNum",totalNum);
		return result;
	}
}
