package com.leadway.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;

@Controller
@RequestMapping("/customer/goods/")
public class GoodsDetailController {
	GoodsDao goodsDao = null;
	
	@RequestMapping(value = "/goodsDetail")
	public String goodsDetail(Goods goods, Model data, @RequestParam(name="pageIndex",defaultValue="0")int pageIndex, HttpServletRequest req, HttpServletResponse resp){
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		String r = req.getParameter("id");
		//查询对应要求的物品	
		goodsDao = new GoodsDao();
		int id = Integer.parseInt(r);
		Goods goodsDetail = goodsDao.getGoodsById(id);
		String str = goodsDetail.getGoodsType().getCode();
		data.addAttribute("goodsDetail", goodsDetail);
		if(str.length() == 4){
			GoodsType indexName = goodsTypeDao.getIndexPageType(str);
			data.addAttribute("indexName",indexName);
			// 查询导航栏
			List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
			data.addAttribute("typeList", typeList);
			// 查询导航栏下拉类型
			List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
			data.addAttribute("navList", navList);			
		}else if(str.length() == 8){
			String s = str.substring(0, 4);
			//查询滚动图片
			List<Goods> footList = goodsDao.getGoodsByCode(s);
			data.addAttribute("footList", footList);
			GoodsType indexName = goodsTypeDao.getIndexPageType(s);
			GoodsType indexName1 = goodsTypeDao.getIndexPageType(str);
			data.addAttribute("indexName",indexName);
			data.addAttribute("indexName1",indexName1);
			//查询对应要求的物品	
			str = s;
			//查询当前大类型下的所有小类型
			List<Map<String,String>> maxToMin = goodsTypeDao.getMaxToMinType(str);
			data.addAttribute("maxToMin", maxToMin);
			// 查询导航栏
			List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
			data.addAttribute("typeList", typeList);
			// 查询导航栏下拉类型
			List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
			data.addAttribute("navList", navList);
		}	
		
		return "pro_details";
	}

}
