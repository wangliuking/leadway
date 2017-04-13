package com.leadway.dto;

/**
 * OrderItem 数据传输类
 * @author hanfeili
 * @email 1783322568@qq.com
 * @date 2016-03-02 10:22:50
 * @version 1.0
 */
public class OrderItem implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Order order;
	private Goods goods;
	private int orderNum;

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public void setOrderNum(int orderNum){
		this.orderNum = orderNum;
	}
	public int getOrderNum(){
		return this.orderNum;
	}
	
	

}