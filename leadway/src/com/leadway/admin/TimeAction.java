package com.leadway.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.leadway.common.PageModel;
import com.leadway.dao.GoodsDao;
import com.leadway.dao.GoodsTypeDao;
import com.leadway.dao.PicDao;
import com.leadway.dao.TimeDao;
import com.leadway.dao.UserDao;
import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;
import com.leadway.dto.Picturcarousel;
import com.leadway.dto.Timelimited;
import com.leadway.dto.User;
/**
 * 抢购管理Action处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/timelimited/")
public class TimeAction {
	
	private TimeDao timeDao = null;
	
	/**
	 * 查询所有抢购操作
	 * @param data
	 * @return
	 */
	@RequestMapping("/timeList")
	public String getTimeList(Model data){
		timeDao = new TimeDao();
		List<Timelimited> timeList = timeDao.getTimeList();
		data.addAttribute("timeList", timeList);
		return "time/timeList";
	}
	
	/**
	 * 跳转到添加抢购页面
	 * @param data
	 * @return
	 */
	@RequestMapping("/toAddTime")
	public String toAddGoods(Model data){
		return "time/addTime";
	}
	
	/**
	 * 抢购添加
	 * @param goods
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/addTime")
	public String addGoods(Timelimited timelimited,Model data) throws IOException{
	
		timeDao = new TimeDao();
		timelimited.setIsEnd(1);
		boolean flag = timeDao.saveTime(timelimited);

		return "redirect:/admin/timelimited/timeList";
	}
	
	/**
	 * 抢购删除
	 * @param id
	 */
	
	@RequestMapping("/delete")
	public String deleteGoodsTime(int id){
		timeDao = new TimeDao();
		timeDao.deleteGoodsTime(id);
		
		return "redirect:/admin/timelimited/timeList";
	}
	
}
