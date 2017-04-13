package com.leadway.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.leadway.common.ImgConstant;
import com.leadway.common.PageModel;
import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;
import com.leadway.dto.User;
/**
 * 商品管理Action处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/goods/")
public class GoodsAction {
	GoodsTypeDao goodsTypeDao = null;
	
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
	@RequestMapping("/goodsList")
	public String getGoodsList(Goods goods,@RequestParam(name="pageIndex",defaultValue="0")int pageIndex,
			Model data,HttpServletRequest req,HttpServletResponse resp){
		goodsTypeDao = new GoodsTypeDao();
		
		if(goods.getGoodsType() == null || goods.getGoodsType().getCode().equals("请选择商品类型")){
			List<GoodsType> goodstypeList = goodsTypeDao.getGoodsTypeList();
			data.addAttribute("goodstypeList", goodstypeList);
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("goods",goods);
			
			goodsDao = new GoodsDao();
			int recordCount = goodsDao.count1(params);
			if(recordCount != 0){
				//设置总共有多个条记录（方便进行分页计算）
				pageModel.setRecordCount(recordCount);
				//设置当前页
				pageModel.setPageIndex(pageIndex);
				
				params.put("pageModel", pageModel);
				List<Goods> goodsList = goodsDao.getPageGoods1(params);
				
				data.addAttribute("pageModel", pageModel);
				data.addAttribute("goodsList", goodsList);

			}
		}else{
		List<GoodsType> goodstypeList = goodsTypeDao.getGoodsTypeList();
		data.addAttribute("goodstypeList", goodstypeList);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("goods",goods);
		
		goodsDao = new GoodsDao();
		int recordCount = goodsDao.count(params);
		if(recordCount != 0){
			//设置总共有多个条记录（方便进行分页计算）
			pageModel.setRecordCount(recordCount);
			//设置当前页
			pageModel.setPageIndex(pageIndex);
			
			params.put("pageModel", pageModel);
			List<Goods> goodsList = goodsDao.getPageGoods(params);
			
			data.addAttribute("pageModel", pageModel);
			data.addAttribute("goodsList", goodsList);

		}
		}
		return "goods/goodsList";
	}
	
	/**
	 * 根据商品的类型获取商品（新增抢购页面使用）
	 * @param code
	 * @return
	 */
	@RequestMapping("/goodsListAjax")
	@ResponseBody
	public List<Goods> goodsListAjax(String code){
		goodsDao = new GoodsDao();
		List<Goods> goodsList = goodsDao.getGoodsByCode(code);
		return goodsList;
	}
	
	/**
	 * 跳转到添加商品页面
	 * @param data
	 * @return
	 */
	@RequestMapping("/toAddGoods")
	public String toAddGoods(Model data){
		goodsTypeDao = new GoodsTypeDao();
		List<GoodsType> goodstypeList = goodsTypeDao.getGoodsTypeList();
		data.addAttribute("goodstypeList", goodstypeList);
		
		return "goods/addGoods";
	}
	
	/**
	 * 跳转到修改商品页面
	 * @param data
	 * @return
	 */
	@RequestMapping("/showUpdate")
	public String showUpdate(int id,Model data){
		goodsTypeDao = new GoodsTypeDao();
		List<GoodsType> goodstypeList = goodsTypeDao.getGoodsTypeList();
		data.addAttribute("goodstypeList", goodstypeList);
		GoodsDao goodsDao = new GoodsDao();
		Goods goods = goodsDao.getGoodsById(id);
		data.addAttribute("goods",goods);
		return "goods/updateGoods";
	}
	
	/**
	 * 商品添加界面
	 * @param goods
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/addGoods")
	public String addGoods(@RequestParam(name="addPro")String addPro,@RequestParam(name="addPro1")String addPro1,
			@RequestParam(name="addPro2")String addPro2,@RequestParam(name="addPro3")String addPro3,
			@RequestParam(name="addPro4")String addPro4,HttpSession session,Goods goods,Model data) throws IOException{
		
		goodsDao = new GoodsDao();
		goods.setImage(addPro.substring(8));
		goods.setImg1(addPro1.substring(8));
		goods.setImg2(addPro2.substring(8));
		goods.setImg3(addPro3.substring(8));
		goods.setImgDetail(addPro4.substring(8));
		goods.setCreateDate(new Date());
		boolean flag = goodsDao.saveGoods(goods);
		
		//return "forward:/admin/goods/goodsList";
		
		return "redirect:/admin/goods/goodsList";
	}
	
	/**
	 * 商品删除操作
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(int id){
		GoodsDao goodsDao = new GoodsDao();
		goodsDao.deleteGoods(id);
		
		return "forward:/admin/goods/goodsList";
	}
	
	/**
	 * 商品更新操作
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateGoods")
	public String update(@RequestParam(name="addPro")String addPro,@RequestParam(name="addPro1")String addPro1,
			@RequestParam(name="addPro2")String addPro2,@RequestParam(name="addPro3")String addPro3,
			@RequestParam(name="addPro4")String addPro4,int id,Goods goods,Model data){
		goodsDao = new GoodsDao();
		goods.setImage(addPro.substring(8));
		goods.setImg1(addPro1.substring(8));
		goods.setImg2(addPro2.substring(8));
		goods.setImg3(addPro3.substring(8));
		goods.setImgDetail(addPro4.substring(8));
		goodsDao.updateGoods(id,goods);
		
		return "redirect:/admin/goods/goodsList";
	}
	
	/**
	 * ajax文件上传
	 * @param file
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/imgUpload")
	@ResponseBody
	public String fileUpload(@RequestParam(name="file")MultipartFile file,HttpSession session) throws IOException{
		
		String fileName = imgsUpload(file, session,"/images/");		
		return fileName;
	}

	/**
	 * 文件上传公共方法
	 * @param file
	 * @param session
	 * @return
	 * @throws IOException
	 */
	private String imgsUpload(MultipartFile file, HttpSession session,String savePath)
			throws IOException {
		//获取文件在服务器的存储路径
		String path = session.getServletContext().getRealPath(savePath);
		//获取上传文件的名称
		String fileName = file.getOriginalFilename();
		//进行文件存储
		file.transferTo(new File(path,fileName));
		return savePath+fileName;
	}
	
}
