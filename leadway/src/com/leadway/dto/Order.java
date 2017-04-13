package com.leadway.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Order 数据传输类
 * @author hanfeili
 * @email 1783322568@qq.com
 * @date 2016-03-02 10:22:50
 * @version 1.0
 */
public class Order implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String orderCode;
	private java.util.Date createDate;
	private java.util.Date sendDate;
	private int tradingStatus;
	private double totalAmount;
	private User user;
	private int alipay;
	
	
	/**业务相关属性，一个订单关联多个订单明细*/
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	/** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setOrderCode(String orderCode){
		this.orderCode = orderCode;
	}
	public String getOrderCode(){
		return this.orderCode;
	}
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	public java.util.Date getCreateDate(){
		return this.createDate;
	}
	public void setSendDate(java.util.Date sendDate){
		this.sendDate = sendDate;
	}
	public java.util.Date getSendDate(){
		return this.sendDate;
	}
	public void setTradingStatus(int tradingStatus){
		this.tradingStatus = tradingStatus;
	}
	public int getTradingStatus(){
		return this.tradingStatus;
	}
	public void setTotalAmount(double totalAmount){
		this.totalAmount = totalAmount;
	}
	public double getTotalAmount(){
		return this.totalAmount;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setAlipay(int alipay){
		this.alipay = alipay;
	}
	public int getAlipay(){
		return this.alipay;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

}