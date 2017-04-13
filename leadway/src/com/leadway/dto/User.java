package com.leadway.dto;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * User 数据传输类
 * @author hanfeili
 * @email 1783322568@qq.com
 * @date 2016-03-02 10:22:50
 * @version 1.0
 */
public class User implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String userId;
	private String password;
	private String realname;
	private String city;
	private int sex;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date birthday;
	private String email;
	private String phone;
	private String address;
	private int role;
	private java.util.Date createDate;
	private int activeStatus;
	private String activeCode;
	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", realname=" + realname + ", city="
				+ city + ", sex=" + sex + ", birthday=" + birthday + ", email=" + email + ", phone=" + phone
				+ ", address=" + address + ", role=" + role + ", createDate=" + createDate + ", activeStatus="
				+ activeStatus + ", activeCode=" + activeCode + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	
}