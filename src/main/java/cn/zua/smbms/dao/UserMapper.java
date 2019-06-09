package cn.zua.smbms.dao;

import cn.zua.smbms.bean.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
	
	/**
	 * 根据条件分页查询用户信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<User> selectUserByPageAndCondition(String userName, Long userRole, int pageIndex, int pageSize);
	
	/**
	 * 根据条件查询用户总数
	 * @param userName
	 * @param userRole
	 * @return
	 */
	int getCountByCondition(String userName, Long userRole);
	
	/**
	 * 根据用户编码查询用户是否存在
	 * @param userCode
	 * @return
	 */
	User selectUserCodeExist(String userCode);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加用户记录
     * @param record
     * @return
     */
    int insert(User record);
    
    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    User selectByPrimaryKey(Long id);
    
    /**
     * 获取所有用户信息
     * @return
     */
    List<User> selectAll();
    
    /**
     * 更新用户信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);
    
    /**
     * 根据用户号查找用户信息
     * @param
     * @return
     */
    //@Select("select * from smbms_user where user_code = #{param1}  and user_password = #{param2}")
    User selectByUserCode(String userCode,String userPwd);
    
    /**
     * 根据id修改密码
     * @param id
     * @param newPwd
     * @return
     */
    boolean updatePwd(Long id, String newPwd);
}