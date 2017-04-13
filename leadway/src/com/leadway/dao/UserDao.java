package com.leadway.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.leadway.dto.Order;
import com.leadway.dto.User;

/**
 * 专门针对User对象的数据库操作
 * 
 * @author Administrator
 *
 */
public class UserDao extends BaseDao {
	
	private static final String NAMESPACE_NAME = "com.leadway.mapper.UserMapper.";
	
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
	 * 分页查询 用户数据
	 * @return
	 */
	public List<User> getPageUser(Map<String,Object> params){
		SqlSession session = getSqlSession();
		List<User> userList = session.selectList(NAMESPACE_NAME+"getUserListByPage",params);
		closeSqlSession();
		return userList;
	}
	
	/**
	 * 通过用户名和密码查询用户名（登录）
	 * @param u
	 * @return
	 */
	public User getUserByUserIdAndByPassword(User u){
		
		SqlSession session = getSqlSession();
		User user = session.selectOne(NAMESPACE_NAME+"getUserByUserIdAndByPassword", u);
		closeSqlSession();
		return user;
	}
	
	/**
	 * 通过用户编号查询用户数据
	 * @param id
	 * @return user
	 */
	public User getUserById(int id){
		
		SqlSession session = getSqlSession();
		User user = session.selectOne(NAMESPACE_NAME+"getUserById", id);
		closeSqlSession();
		return user;
	}
	
	/**
	 * 检查用户名是否重复
	 * @param userId
	 * @return true 表示重复，false表示不重复
	 */
	public boolean getUserByUserId(String userId){
		
		SqlSession session = getSqlSession();
		int count = session.selectOne(NAMESPACE_NAME+"getUserByUserId", userId);
		closeSqlSession();
		return count>0?true:false;
	}
	/**
	 * 管理员登录
	 * @param u
	 * @return
	 */
	public User getAdmin(User u){
		
		SqlSession session = getSqlSession();
		User user = session.selectOne(NAMESPACE_NAME+"getAdmin", u);
		closeSqlSession();
		return user;
	}
	/**
	 * 新增用户
	 * @param u
	 * @return
	 */
	public int save(User u){
		SqlSession session = getSqlSession();
		int count = session.insert(NAMESPACE_NAME+"save",u);
		session.commit();
		closeSqlSession();
		return count;	
	}
	/**
	 * 用户激活操作
	 * @param map
	 * @return
	 */
	public int activeUser(Map<String,String> map){
		SqlSession session = getSqlSession();
		int count = session.update(NAMESPACE_NAME+"activeUser", map);
		session.commit();
		closeSqlSession();
		return count;
	}
	
	/**
	 * 用户数据更新
	 * @param user
	 * @return
	 */
	public int updateUser(User user){
		SqlSession session = getSqlSession();
		int count = session.update(NAMESPACE_NAME+"updateUser", user);
		session.commit();
		closeSqlSession();
		return count;
	}
	
	/**
	 * 删除用户操作
	 * @param id
	 * @return
	 */
	public int deleteUser(int id){
		SqlSession session = getSqlSession();
		int count = session.delete(NAMESPACE_NAME+"deleteUser", id);
		session.commit();
		closeSqlSession();
		return count;
	}
	
}
