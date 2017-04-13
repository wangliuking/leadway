package com.leadway.dto;

import java.util.List;
import java.util.Map;

/**
 * GoodsType 数据传输类
 * @author hanfeili
 * @email 1783322568@qq.com
 * @date 2016-03-02 10:22:50
 * @version 1.0
 */
public class GoodsType implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String remark;

	
	@Override
	public String toString() {
		return "GoodsType [code=" + code + ", name=" + name + ", remark=" + remark + "]";
	}
	/**
	 * 返回一个大类型和小类型组合好的json数据
	 * @author Administrator
	 *
	 */
	
	public static class GoodsTypes{
		private Map<String,String> maxType;
		private List<Map<String,String>> minType;
		public Map<String, String> getMaxType() {
			return maxType;
		}
		public void setMaxType(Map<String, String> maxType) {
			this.maxType = maxType;
		}
		public List<Map<String, String>> getMinType() {
			return minType;
		}
		public void setMinType(List<Map<String, String>> minType) {
			this.minType = minType;
		}
		@Override
		public String toString() {
			return maxType.toString() + minType.toString();
		}
	}
	
	/** setter and getter method */
	public void setCode(String code){
		this.code = code;
	}
	public String getCode(){
		return this.code;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}

}