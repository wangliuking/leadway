package com.leadway.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leadway.common.PageModel;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.OrderDao;
import com.leadway.dao.PicDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.GoodsType;
import com.leadway.dto.Order;
import com.leadway.dto.Picturcarousel;
import com.leadway.dto.User;

@Controller
@RequestMapping("/admin/goodstype/")
public class GoodsTypeAction {
	GoodsTypeDao goodsTypeDao = null;
	/**
	 * 分页模型对象
	 */
	PageModel pageModel = new PageModel();
	/**
	 * 分页查询所有商品类型操作
	 * @param data
	 * @return
	 */
	@RequestMapping("/goodstypeList")
	public String getGoodsTypeList(GoodsType goodstype,@RequestParam(name="pageIndex",defaultValue="0")int pageIndex,Model data){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("goodstype",goodstype);
		goodsTypeDao = new GoodsTypeDao();
		int recordCount = goodsTypeDao.count(params);
		if(recordCount != 0){
			//设置总共有多个条记录（方便进行分页计算）
			pageModel.setRecordCount(recordCount);
			//设置当前页
			pageModel.setPageIndex(pageIndex);
			
			params.put("pageModel", pageModel);
			List<GoodsType> goodstypeList = goodsTypeDao.getPageGoodsType(params);
			
			data.addAttribute("pageModel", pageModel);
			data.addAttribute("goodstypeList", goodstypeList);
			
		}
		return "goodstype/goodstypeList";
	}
	
	
	/**
	 * ajax获取商品类型数据（新增抢购页面使用）
	 * @return
	 */
	@RequestMapping("/goodstypeListAjax")
	@ResponseBody
	public List<GoodsType> getGoodsTypeList(){
		goodsTypeDao = new GoodsTypeDao();
		List<GoodsType> goodstypeList = goodsTypeDao.getGoodsTypeList();
		return goodstypeList;
	}
	/**
	 * 删除操作
	 * @param code
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(String code){
		goodsTypeDao = new GoodsTypeDao();
		goodsTypeDao.deleteGoodsType(code);
		
		return "forward:/admin/goodstype/goodstypeList";
	}
	/**
	 * 商品类型更新
	 * @param user
	 * @return
	 */
	@RequestMapping("/update")
	public String update(GoodsType goodsType){
		goodsTypeDao = new GoodsTypeDao();
		goodsTypeDao.updateGoodsType(goodsType);
		return "forward:/admin/goodstype/goodstypeList";
	}
	
	/**
	 * 跳转到商品类型更新页面
	 * @param data
	 * @return
	 */
	@RequestMapping("/showUpdate")
	public String showUpdate(String code,Model data){
		goodsTypeDao = new GoodsTypeDao();
		GoodsType goodstype = goodsTypeDao.findGoodsTypeByCode(code);
		data.addAttribute("goodstype",goodstype);
		return "goodstype/updateGoodsType";
	}
}
