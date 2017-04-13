package com.leadway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.PicDao;
import com.leadway.dao.TimeDao;
import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;
import com.leadway.dto.GoodsType.GoodsTypes;
import com.leadway.dto.Picturcarousel;
import com.leadway.dto.Timelimited;

/**
 * 首页处理类
 */
@Controller
@RequestMapping("/customer")
public class IndexController {
	
	@RequestMapping(value="/index")
	public String home(Goods goods,Model data){
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
	
	
	/**
	 * 显示商品详情页面
	 * @return
	 */
	@RequestMapping(value="/detail")
	public String detail(int id,Model data){
		//根据id获取商品
		GoodsDao goodsDao = new GoodsDao();
		Goods goods = goodsDao.getGoodsById(id);
		//将小类型上 - 进行替换
		goods.getGoodsType().setName(goods.getGoodsType().getName().replace("-", ""));
		data.addAttribute("goods", goods);

		//根据具体的商品类型获取商品的大类型
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		GoodsType maxGoodsType = goodsTypeDao.findGoodsTypeByCode(goods.getGoodsType().getCode().substring(0, 4));
		data.addAttribute("maxGoodsType", maxGoodsType);

		return "details";
	}
}


