package com.leadway.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Order;
import com.leadway.dto.OrderItem;
/**
 * 专门针对订单明细对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class OrderItemDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = 
			"com.leadway.mapper.OrderItemMapper.";
	
	/**
	 * 订单id获取 订单明细数据
	 * @return
	 */
	public List<OrderItem> getOrderItem(int id){
		SqlSession session = getSqlSession();
		
		List<OrderItem> orderItemList = session.selectList(
				NAMESPACE_NAME+"getOrderItemByOrderId",id);
		
		closeSqlSession();
		return orderItemList;
	}
	
	
	/**
	 * 新增订单明细数据
	 * @param order
	 * @return true 新增成功，false 新增失败
	 */
	public boolean saveOrderItem(OrderItem orderItem){
		SqlSession session = getSqlSession();
		int count = session.insert(NAMESPACE_NAME+"save",orderItem);
		session.commit();
		closeSqlSession();
		return count>0?true:false;
	}
	
	/**
	 * 根据订单id删除订单详情数据
	 */
	public int deleteOrderItemById(int id){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteOrderItemById",id);
		session.commit();
		closeSqlSession();
		return count;
	}
}
