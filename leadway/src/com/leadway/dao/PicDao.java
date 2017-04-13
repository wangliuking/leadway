package com.leadway.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Goods;
import com.leadway.dto.Picturcarousel;

/**
 * 专门针对图片轮播对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class PicDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = "com.leadway.mapper.PicMapper.";
	
	/**
	 * 查询图片轮播数据
	 * @return
	 */
	public List<Picturcarousel> getPicList(){
		SqlSession session = getSqlSession();
		List<Picturcarousel> picList = session.selectList(NAMESPACE_NAME+"getPicList");
		closeSqlSession();
		return picList;
	}
	
	/**
	 * 新增图片轮播数据
	 * @param Picturcarousel
	 * @return true 新增成功，false 新增失败
	 */
	public boolean savePic(Picturcarousel picturcarousel){
		SqlSession session = getSqlSession();
		int count = session.insert(NAMESPACE_NAME+"save",picturcarousel);
		session.commit();
		closeSqlSession();
		return count>0?true:false;
	}
	
	
	/**
	 * 删除图片轮播数据
	 * @param id
	 * @return
	 */
	public int deleteGoodsPic(int id){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteGoodsPic", id);
		session.commit();
		closeSqlSession();
		return count;
	}
	
	/**
	 * 根据给定的id查询轮播数据
	 * @param id
	 * @return
	 */
	public Picturcarousel getGoodsPicById(int id){
		SqlSession session = getSqlSession();
		Picturcarousel pic = session.selectOne(NAMESPACE_NAME+"getGoodsPicById",id);
		closeSqlSession();
		return pic;
	}
	
	/**
	 * 更新轮播数据操作
	 * @param id
	 * @return
	 */
	public int updateGoodsPic(int id,Picturcarousel picturcarousel){
		SqlSession session = getSqlSession();
		int count = session.update(NAMESPACE_NAME+"updateGoodsPic", picturcarousel);
		session.commit();
		closeSqlSession();
		return count;
	}
}
