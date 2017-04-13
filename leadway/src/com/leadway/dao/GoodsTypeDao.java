package com.leadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;
import com.leadway.dto.GoodsType.GoodsTypes;
import com.leadway.dto.Order;
import com.leadway.dto.User;

/**
 * 专门针对商品类型对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class GoodsTypeDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = "com.leadway.mapper.GoodsTypeMapper.";
	
	/**
	 * 统计商品类型行数
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
	 * 分页查询 商品类型数据
	 * @return
	 */
	public List<GoodsType> getPageGoodsType(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<GoodsType> goodstypeList = session.selectList(NAMESPACE_NAME+"getGoodsTypeListByPage",params);
		closeSqlSession();
		return goodstypeList;
	}
	
	/**
	 * 查询所有用户数据
	 * @param user
	 * @return
	 */
	public List<GoodsType> getGoodsTypeList(){
		
		SqlSession session = getSqlSession();
		List<GoodsType> goodsTypeList = session.selectList(NAMESPACE_NAME+"getGoodsTypeList");
		closeSqlSession();
		return goodsTypeList;
		
	}
	
	/**
	 * 获取商品类型数据
	 * [{maxType:{code:'0001',name:'手机'},minType:[{code:'00010001',name:'苹果'},{code:'00010002',name:'华为'},]}]
	 * @return 
	 */
	public List<GoodsType.GoodsTypes> getGoodsTypes(){
		
		SqlSession session = getSqlSession();
		
		List<GoodsType.GoodsTypes> gtsList = new ArrayList<>();  
		//获取大类型
		List<Map<String,String>> maxTypes = session.selectList(NAMESPACE_NAME+"getMaxType");
		
		for(Map<String,String> max : maxTypes){
			//获取小类型
			List<Map<String,String>>minTypes = session.selectList(NAMESPACE_NAME+"getMinType", max.get("code"));
			
			//创建一个包装对象
			GoodsType.GoodsTypes gts = new GoodsType.GoodsTypes();
		
			gts.setMaxType(max);
			gts.setMinType(minTypes);
			gtsList.add(gts);
		}
		
		closeSqlSession();
		return gtsList;
	}
	
	/**
	 * 获取商品大类型数据
	 */
	public List<Map<String,String>> getGoodsMaxTypes(){
		
		SqlSession session = getSqlSession(); 
		//获取大类型
		List<Map<String,String>> maxTypes = session.selectList(NAMESPACE_NAME+"getMaxType1");
		closeSqlSession();
		return maxTypes;
	}
	
	/**
	 * 获取当前大类型下的所有小类型
	 */
	public List<Map<String,String>> getMaxToMinType(String str){
		
		SqlSession session = getSqlSession(); 
		//获取大类型下所有小类型
		List<Map<String,String>> maxToMin = session.selectList(NAMESPACE_NAME+"getMaxToMinType",str);
		closeSqlSession();
		return maxToMin;
	}
	
	
	
	/**
	 * 获取导航栏下拉类型数据
	 */
	public List<Map<String,String>> getGoodsMaxTypesAll(){
		
		SqlSession session = getSqlSession(); 
		//获取大类型
		List<Map<String,String>> Types = session.selectList(NAMESPACE_NAME+"getMaxTypeAll");
		closeSqlSession();
		return Types;
	}
	
	/**
	 * 查询当前页的大类型
	 */
	public GoodsType getIndexPageType(String str){
		SqlSession session = getSqlSession();
		GoodsType goodsType = session.selectOne(NAMESPACE_NAME+"getIndexPageType",str);
		closeSqlSession();
		return goodsType;
	}

	
	/**
	 * 根据code查询商品类型
	 * @param code
	 * @return
	 */
	public GoodsType findGoodsTypeByCode(String code){
		SqlSession session = getSqlSession();
		GoodsType goodsType = session.selectOne(NAMESPACE_NAME+"findGoodsTypeByCode",code);
		closeSqlSession();
		return goodsType;
	}
	
	/**
	 * 商品类型数据更新
	 * @param user
	 * @return
	 */
	public int updateGoodsType(GoodsType goodsType){
		SqlSession session = getSqlSession();
		int count = session.update(NAMESPACE_NAME+"updateGoodsType", goodsType);
		session.commit();
		closeSqlSession();
		return count;
	}
	
	/**
	 * 删除商品类型操作
	 * @param code
	 * @return
	 */
	public int deleteGoodsType(String code){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteGoodsType", code);
		session.commit();
		closeSqlSession();
		return count;
	}
	
}
