package com.leadway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Goods;
import com.leadway.dto.Order;
import com.leadway.dto.Picturcarousel;
import com.leadway.dto.Timelimited;

/**
 * 专门针对订单对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class OrderDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = "com.leadway.mapper.OrderMapper.";
	
	/**
	 * 统计订单行数
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
	 * 统计用户Id查询的订单行数
	 * @param user
	 * @return
	 */
	public int countByUserId(int userId){
		SqlSession session = getSqlSession();
		int count = session.selectOne(NAMESPACE_NAME+"countByUserId",userId);
		closeSqlSession();
		return count;
	}
	
	/**
	 * 分页查询 订单数据
	 * @return
	 */
	public List<Order> getPageOrder(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Order> orderList = session.selectList(NAMESPACE_NAME+"getOrderListByPage",params);
		closeSqlSession();
		return orderList;
	}
	
	
	/**
	 * 根据用户Id查询用户的订单
	 * @param userId
	 * @return
	 */
	public List<Order> getOrderByUserId(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<Order> orderList = session.selectList(NAMESPACE_NAME+"getOrderByUserId",params);
		closeSqlSession();
		return orderList;
	}
	/**
	 * 根据订单编号查询订单
	 * @param userId
	 * @return
	 */
	public Order getOrderByOrderCode(String orderCode){
		SqlSession session = getSqlSession();
		Order order = session.selectOne(NAMESPACE_NAME+"getOrderByOrderCode",orderCode);
		closeSqlSession();
		return order;
	}
	
	
	
	
	/**
	 * 新增订单数据
	 * @param order
	 * @return true 新增成功，false 新增失败
	 */
	public boolean saveOrder(Order order){
		SqlSession session = getSqlSession();
		int count = session.insert(NAMESPACE_NAME+"save",order);
		session.commit();
		closeSqlSession();
		return count>0?true:false;
	}
	/**
	 * 更新订单支付状态
	 * @param orderCode
	 * @return
	 */
	public boolean updateOrderAliPayStatus(String orderCode){
		SqlSession session = getSqlSession();
		int count = session.update(NAMESPACE_NAME+"updateOrderAliPayStatus",orderCode);
		session.commit();
		closeSqlSession();
		return count>0?true:false;
	}
	
	/**
	 * 删除订单数据
	 * @param id
	 * @return
	 */
	public int deleteOrder(int orderId){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteOrder", orderId);
		session.commit();
		closeSqlSession();
		return count;
	}
}
