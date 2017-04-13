package com.leadway.dto;

/**
 * Picturcarousel 轮播数据传输类
 * @author hanfeili
 * @email 1783322568@qq.com
 * @date 2016-03-02 10:22:50
 * @version 1.0
 */
public class Picturcarousel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String imageUrl;
	private String requestUrl;

	/** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}
	public String getImageUrl(){
		return this.imageUrl;
	}
	public void setRequestUrl(String requestUrl){
		this.requestUrl = requestUrl;
	}
	public String getRequestUrl(){
		return this.requestUrl;
	}

}