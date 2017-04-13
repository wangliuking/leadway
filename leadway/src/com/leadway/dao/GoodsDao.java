package com.leadway.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;
import com.leadway.dto.User;

/**
 * 专门针对商品对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class GoodsDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = "com.leadway.mapper.GoodsMapper.";
	
	/**
	 * 统计商品行数
	 * @param user
	 * @return
	 */
	public int count(Map<String,Object> params){
		SqlSession session = getSqlSession();
		int count = session.selectOne(NAMESPACE_NAME+"count",params);
		closeSqlSession();
		return count;
	}
	
	/**
	 * 统计首页头部搜索行数
	 * @param user
	 * @return
	 */
	public int countHead(Map<String,Object> params){
		SqlSession session = getSqlSession();
		int count = session.selectOne(NAMESPACE_NAME+"countHead",params);
		closeSqlSession();
		return count;
	}
	
	/**
	 * 统计商品行数 当goodstype为空时-
	 * @param user
	 * @return
	 */
	public int count1(Map<String,Object> params){
		SqlSession session = getSqlSession();
		int count = session.selectOne(NAMESPACE_NAME+"count1",params);
		closeSqlSession();
		return count;
	}
	
	/**
	 * 统计首页商品列表行数
	 * @param user
	 * @return
	 */
	public int countIndex(Map<String,Object> params){
		SqlSession session = getSqlSession();
		int count = session.selectOne(NAMESPACE_NAME+"countIndex",params);
		closeSqlSession();
		return count;
	}
	
	/**
	 * 首页头部查询商品数据
	 * @param user
	 * @return
	 */
	public List<Goods> findByPageHead(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Goods> goodsList = session.selectList(NAMESPACE_NAME+"findByPageHead",params);
		closeSqlSession();
		return goodsList;
	}
	
	/**
	 * 分页查询商品数据
	 * @param user
	 * @return
	 */
	public List<Goods> getPageGoods(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Goods> goodsList = session.selectList(NAMESPACE_NAME+"findByPage",params);
		closeSqlSession();
		return goodsList;
	}
	
	/**
	 * 分页查询首页列表对应商品数据
	 * @param user
	 * @return
	 */
	public List<Goods> getPageGoodsByCode(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Goods> goodsList = session.selectList(NAMESPACE_NAME+"findByPageShop",params);
		closeSqlSession();
		return goodsList;
	}
	
	/**
	 * 分页查询商品数据(当goodstype为空时进入)
	 * @param user
	 * @return
	 */
	public List<Goods> getPageGoods1(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Goods> goodsList = session.selectList(NAMESPACE_NAME+"findByPage1",params);
		closeSqlSession();
		return goodsList;
	}
	
	/**
	 * 根据商品的类型查询商品
	 * @param code
	 * @return
	 */
	public List<Goods> getGoodsByCode(String code){
		SqlSession session = getSqlSession();
		List<Goods> goodsList = session.selectList(NAMESPACE_NAME+"getGoodsByCode",code);
		closeSqlSession();
		return goodsList;
	}
	
	
	/**
	 * 新增商品
	 * @param goods
	 * @return true 新增成功，false 新增失败
	 * @throws ParseException 
	 */
	public boolean saveGoods(Goods goods){	
		SqlSession session = getSqlSession();
		int count = session.insert(NAMESPACE_NAME+"saveGoods",goods);
		session.commit();
		closeSqlSession();
		return count>0?true:false;
	}
	
	/**
	 * 删除商品操作
	 * @param id
	 * @return
	 */
	public int deleteGoods(int id){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteGoods", id);
		session.commit();
		closeSqlSession();
		return count;
	}
	
	/**
	 * 更新商品操作
	 * @param id
	 * @return
	 */
	public int updateGoods(int id,Goods goods){
		Date d = goods.getGroundingDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(d);
		goods.setD(str);
		SqlSession session = getSqlSession();
		int count = session.update(NAMESPACE_NAME+"updateGoods", goods);
		session.commit();
		closeSqlSession();
		return count;
	}
	
	/**
	 * 根据给定的商品id查询商品
	 * @param id
	 * @return
	 */
	public Goods getGoodsById(int id){
		SqlSession session = getSqlSession();
		Goods goods = session.selectOne(NAMESPACE_NAME+"getGoodsById",id);
		closeSqlSession();
		return goods;
	}
	
	/**
	 * 查询热卖商品
	 */
	public List<Goods> getGoodsSale(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Goods> goodsList = session.selectList(NAMESPACE_NAME+"getGoodsSale",params);
		closeSqlSession();
		return goodsList;
	}
	
	/**
	 * 查询夏日必备商品
	 */
	public List<Goods> getGoodsSummer(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Goods> goodsList = session.selectList(NAMESPACE_NAME+"getGoodsSummer",params);
		closeSqlSession();
		return goodsList;
	}
	
}
