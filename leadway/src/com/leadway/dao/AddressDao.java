package com.leadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Address;
import com.leadway.dto.Goods;
import com.leadway.dto.GoodsType;
import com.leadway.dto.GoodsType.GoodsTypes;
import com.leadway.dto.Order;
import com.leadway.dto.User;

/**
 * 专门针对收货地址的数据库操作
 * 
 * @author Administrator
 *
 */
public class AddressDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = "com.leadway.mapper.AddressMapper.";

	/**
	 * 根据userId查询收货地址
	 * @param code
	 * @return
	 */
	public List<Address> findUserAddress(String userId){
		SqlSession session = getSqlSession();
		List<Address> address = session.selectList(NAMESPACE_NAME+"getAddress",userId);
		closeSqlSession();
		return address;
	}
	
	/**
	 * 用户收货地址数据更新
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
	 * 删除收货地址
	 * @param code
	 * @return
	 */
	public int deleteUserAddress(int id){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteUserAddress", id);
		session.commit();
		closeSqlSession();
		return count;
	}
	
	/**
	 * 增加收货地址
	 * @param code
	 * @return
	 */
	public int addUserAddress(Address address){
		SqlSession session = getSqlSession();
		int count = session.insert(NAMESPACE_NAME+"addUserAddress", address);
		session.commit();
		closeSqlSession();
		return count;
	}
	
}
