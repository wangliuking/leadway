package com.leadway.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Picturcarousel;
import com.leadway.dto.Timelimited;

/**
 * 专门针对抢购对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class TimeDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = "com.leadway.mapper.TimeMapper.";
	
	/**
	 * 查询 抢购数据
	 * @return
	 */
	public List<Timelimited> getTimeList(){
		SqlSession session = getSqlSession();
		List<Timelimited> timeList = session.selectList(NAMESPACE_NAME+"getTimeList");
		closeSqlSession();
		return timeList;
	}
	
	/**
	 * 新增抢购数据
	 * @param Picturcarousel
	 * @return true 新增成功，false 新增失败
	 */
	public boolean saveTime(Timelimited timelimited){
		SqlSession session = getSqlSession();
		int count = session.insert(NAMESPACE_NAME+"save",timelimited);
		session.commit();
		closeSqlSession();
		return count>0?true:false;
	}
	
	/**
	 * 删除抢购数据
	 * @param id
	 * @return
	 */
	public int deleteGoodsTime(int id){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteGoodsTime", id);
		session.commit();
		closeSqlSession();
		return count;
	}
}
