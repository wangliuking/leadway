package com.leadway.dto;

import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Goods 数据传输类
 * @author hanfeili
 * @email 1783322568@qq.com
 * @date 2016-03-02 10:22:50
 * @version 1.0
 */
public class Goods implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String brandName;
	private double price;
	private double favorablePrice;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date groundingDate;
	private int storage;
	private String image;
	private String description;
	private String color;
	private String service;
	private String other;
	private GoodsType goodsType;
	private java.util.Date createDate;
	private String img1;
	private String img2;
	private String img3;
	private String imgDetail;
	private String small;
	private String middle;
	private String big;
	
	private String d;
	private String e;
	private String f;
	/**
	 * 业务相关属性
	 */
	private int buyNum;
	/**
	 * 业务相关方法
	 * 计算商品价格
	 */
	public double getBuyMoney(){
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(this.favorablePrice*this.buyNum));
	}

	/** setter and getter method */
	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	
	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d = d;
	}
	
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
	@Override
	public String toString() {
		return "Goods [id=" + id + ", title=" + title + ", brandName=" + brandName + ", price=" + price
				+ ", favorablePrice=" + favorablePrice + ", groundingDate=" + groundingDate + ", storage=" + storage
				+ ", image=" + image + ", description=" + description + ", color=" + color + ", service=" + service
				+ ", other=" + other + ", goodsType=" + goodsType + ", createDate=" + createDate + ", img1=" + img1
				+ ", img2=" + img2 + ", img3=" + img3 + ", imgDetail=" + imgDetail + ", small=" + small + ", middle="
				+ middle + ", big=" + big + ", d=" + d + ", e=" + e + "]";
	}
	/** setter and getter method */
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setBrandName(String brandName){
		this.brandName = brandName;
	}
	public String getBrandName(){
		return this.brandName;
	}
	public void setPrice(double price){
		this.price = price;
	}
	public double getPrice(){
		return this.price;
	}
	public void setFavorablePrice(double favorablePrice){
		this.favorablePrice = favorablePrice;
	}
	public double getFavorablePrice(){
		return this.favorablePrice;
	}
	public void setGroundingDate(java.util.Date groundingDate){
		this.groundingDate = groundingDate;
	}
	public java.util.Date getGroundingDate(){
		return this.groundingDate;
	}
	public void setStorage(int storage){
		this.storage = storage;
	}
	public int getStorage(){
		return this.storage;
	}
	public void setImage(String image){
		this.image = image;
	}
	public String getImage(){
		return this.image;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return this.description;
	}
	
	public GoodsType getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	public java.util.Date getCreateDate(){
		return this.createDate;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImgDetail() {
		return imgDetail;
	}
	public void setImgDetail(String imgDetail) {
		this.imgDetail = imgDetail;
	}
	public String getSmall() {
		return small;
	}
	public void setSmall(String small) {
		this.small = small;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public String getBig() {
		return big;
	}
	public void setBig(String big) {
		this.big = big;
	}
	

}