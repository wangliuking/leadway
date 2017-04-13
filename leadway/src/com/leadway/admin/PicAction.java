package com.leadway.admin;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.leadway.common.FileUpload;
import com.leadway.common.PageModel;
import com.leadway.common.WebConstant;
import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.PicDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;
import com.leadway.dto.Picturcarousel;
import com.leadway.dto.User;
/**
 * 图片轮播管理Action处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/picturcarousel/")
public class PicAction {
	
	private PicDao picDao = null;
	
	/**
	 * 查询所有轮播图操作
	 * @param data
	 * @return
	 */
	@RequestMapping("/picList")
	public String getGoodsList(Model data){
		picDao = new PicDao();
		List<Picturcarousel> picList = picDao.getPicList();
		data.addAttribute("picList", picList);
		return "pic/picList";
	}
	
	/**
	 * 跳转到添加轮播图页面
	 * @param data
	 * @return
	 */
	@RequestMapping("/toAddPic")
	public String toAddGoods(Model data){
		return "pic/addPic";
	}
	
	/**
	 * 轮播图添加
	 * @param goods
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/addPic")
	public String addGoods(@RequestParam(name="pic")MultipartFile file,HttpSession session,
			Picturcarousel picturcarousel,Model data) throws IOException{
		//轮播图上传
		String fileName = FileUpload.imgsUpload(file, session,WebConstant.PIC_IMG_PATH);
		
		picDao = new PicDao();
		picturcarousel.setImageUrl(fileName);

		boolean flag = picDao.savePic(picturcarousel);

		return "redirect:/admin/picturcarousel/picList";
	}
	
	/**
	 * 轮播数据更新操作
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/updatePic")
	public String update(@RequestParam(name="pic")MultipartFile file,HttpSession session,int id,Picturcarousel picturcarousel,Model data) throws IOException{
		//轮播图上传
		String fileName = FileUpload.imgsUpload(file, session,WebConstant.PIC_IMG_PATH);
		picDao = new PicDao();	
		picturcarousel.setImageUrl(fileName);
		picDao.updateGoodsPic(id,picturcarousel);
		
		return "redirect:/admin/picturcarousel/picList";
	}
	
	/**
	 * 轮播图删除
	 * @param id
	 */
	
	@RequestMapping("/delete")
	public String deleteGoodsPic(int id){
		picDao = new PicDao();
		picDao.deleteGoodsPic(id);
		
		return "redirect:/admin/picturcarousel/picList";
	}
	
	/**
	 * 跳转到修改轮播数据页面
	 * @param data
	 * @return
	 */
	@RequestMapping("/showUpdate")
	public String showUpdate(int id,Model data){
		picDao = new PicDao();
		Picturcarousel pic = picDao.getGoodsPicById(id);
		data.addAttribute("pic",pic);
		return "pic/updatePic";
	}
}
