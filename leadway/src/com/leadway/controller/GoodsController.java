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

import com.leadway.common.PageModel;
import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;

@Controller
@RequestMapping("/customer/goods/")
public class GoodsController {
	
	GoodsDao goodsDao = null;
	/**
	 * 分页模型对象
	 */
	PageModel pageModel = new PageModel();
	/**
	 * 查询所有商品操作
	 * @param data
	 * @return
	 */

	@RequestMapping(value = "/goodsShopping")
	public String GoodsShoppingByType(Goods goods, Model data, @RequestParam(name="pageIndex",defaultValue="0")int pageIndex, HttpServletRequest req, HttpServletResponse resp) {
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		String str = req.getParameter("ID");
		if(str.length() == 4){
			GoodsType indexName = goodsTypeDao.getIndexPageType(str);
			data.addAttribute("indexName",indexName);
			//查询当前大类型下的所有小类型
			List<Map<String,String>> maxToMin = goodsTypeDao.getMaxToMinType(str);
			data.addAttribute("maxToMin", maxToMin);
			// 查询导航栏
			List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
			data.addAttribute("typeList", typeList);
			// 查询导航栏下拉类型
			List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
			data.addAttribute("navList", navList);
			//查询对应要求的物品	
			Map<String,Object> params = new HashMap<String,Object>();
			goods.setE(str);
			params.put("goods",goods);
			goodsDao = new GoodsDao();
			int recordCount = goodsDao.countIndex(params);
			if(recordCount != 0){
				//设置总共有多个条记录（方便进行分页计算）
				pageModel.setRecordCount(recordCount);
				//设置当前页
				pageModel.setPageIndex(pageIndex);
				
				params.put("pageModel", pageModel);
				List<Goods> goodsList = goodsDao.getPageGoodsByCode(params);
				
				data.addAttribute("pageModel", pageModel);
				data.addAttribute("goodsList", goodsList);

			}
		}else if(str.length() == 8){
			String s = str.substring(0, 4);
			GoodsType indexName = goodsTypeDao.getIndexPageType(s);
			GoodsType indexName1 = goodsTypeDao.getIndexPageType(str);
			data.addAttribute("indexName",indexName);
			data.addAttribute("indexName1",indexName1);
			//查询对应要求的物品	
			Map<String,Object> params = new HashMap<String,Object>();
			goods.setE(str);
			params.put("goods",goods);
			goodsDao = new GoodsDao();
			int recordCount = goodsDao.countIndex(params);
			if(recordCount != 0){
				//设置总共有多个条记录（方便进行分页计算）
				pageModel.setRecordCount(recordCount);
				//设置当前页
				pageModel.setPageIndex(pageIndex);
				
				params.put("pageModel", pageModel);
				List<Goods> goodsList = goodsDao.getPageGoodsByCode(params);
				
				data.addAttribute("pageModel", pageModel);
				data.addAttribute("goodsList", goodsList);

			}
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
		return "shop_list";
	}
	
	/**
	 * 头部搜索框模糊查询
	 */
	@RequestMapping(value="/GoodsByTitle")
	public String GoodsByTitle(Goods goods, Model data, @RequestParam(name="pageIndex",defaultValue="0")int pageIndex){
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		//查询对应要求的物品	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("goods",goods);
		goodsDao = new GoodsDao();
		int recordCount = goodsDao.countHead(params);
		if(recordCount != 0){
			//设置总共有多个条记录（方便进行分页计算）
			pageModel.setRecordCount(recordCount);
			//设置当前页
			pageModel.setPageIndex(pageIndex);
			
			params.put("pageModel", pageModel);
			List<Goods> goodsList = goodsDao.findByPageHead(params);
			
			data.addAttribute("pageModel", pageModel);
			data.addAttribute("goodsList", goodsList);

		}
		
		// 查询导航栏
		List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
		data.addAttribute("typeList", typeList);
		// 查询导航栏下拉类型
		List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
		data.addAttribute("navList", navList);
		return "shop_lists";
	}
	
	/**
	 * 根据条件搜索商品
	 * @param goods
	 * @param data
	 * @param pageIndex
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/goodsShoppingByTitle")
	public String GoodsShoppingByTitle(Goods goods, Model data, @RequestParam(name="pageIndex",defaultValue="0")int pageIndex, HttpServletRequest req, HttpServletResponse resp) {
		GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
		String str = req.getParameter("ID");
		String st = req.getParameter("ID1");
		if(st.equals("1")){
			GoodsType indexName = goodsTypeDao.getIndexPageType(str);
			data.addAttribute("indexName",indexName);
			//查询当前大类型下的所有小类型
			List<Map<String,String>> maxToMin = goodsTypeDao.getMaxToMinType(str);
			data.addAttribute("maxToMin", maxToMin);
			// 查询导航栏
			List<Map<String, String>> typeList = goodsTypeDao.getGoodsMaxTypes();
			data.addAttribute("typeList", typeList);
			// 查询导航栏下拉类型
			List<Map<String, String>> navList = goodsTypeDao.getGoodsMaxTypesAll();
			data.addAttribute("navList", navList);
			//查询对应要求的物品	
			Map<String,Object> params = new HashMap<String,Object>();
			goods.setE(str);
			params.put("goods",goods);
			goodsDao = new GoodsDao();
			int recordCount = goodsDao.countIndex(params);
			if(recordCount != 0){
				//设置总共有多个条记录（方便进行分页计算）
				pageModel.setRecordCount(recordCount);
				//设置当前页
				pageModel.setPageIndex(pageIndex);
				
				params.put("pageModel", pageModel);
				List<Goods> goodsList = goodsDao.getPageGoodsByCode(params);
				
				data.addAttribute("pageModel", pageModel);
				data.addAttribute("goodsList", goodsList);

			}
		}else{
			str=st;
			String s = str.substring(0, 4);
			GoodsType indexName = goodsTypeDao.getIndexPageType(s);
			GoodsType indexName1 = goodsTypeDao.getIndexPageType(str);
			data.addAttribute("indexName",indexName);
			data.addAttribute("indexName1",indexName1);
			//查询对应要求的物品	
			Map<String,Object> params = new HashMap<String,Object>();
			goods.setE(str);
			params.put("goods",goods);
			goodsDao = new GoodsDao();
			int recordCount = goodsDao.countIndex(params);
			if(recordCount != 0){
				//设置总共有多个条记录（方便进行分页计算）
				pageModel.setRecordCount(recordCount);
				//设置当前页
				pageModel.setPageIndex(pageIndex);
				
				params.put("pageModel", pageModel);
				List<Goods> goodsList = goodsDao.getPageGoodsByCode(params);
				
				data.addAttribute("pageModel", pageModel);
				data.addAttribute("goodsList", goodsList);

			}
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
		
		return "shop_list";
	}
}
