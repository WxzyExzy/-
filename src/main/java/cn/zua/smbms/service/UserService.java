package cn.zua.smbms.service;

import java.util.List;

import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.User;

public interface UserService {
	
	/**
	 * 根据条件分页查询用户信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<User> selectUserByPageAndCondition(String userName, Long userRole, int pageIndex, int pageSize);
	

    /**
     * 添加用户记录
     * @param record
     * @return
     */
    int insert(User record);
    
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
     * @param userCode
     * @return
     */
    User selectByUserCode(String userCode, String userPwd);
    
    /**
     * 根据id修改密码
     * @param id
     * @param newPwd
     * @return
     */
    boolean updatePwd(Long id, String newPwd);
	
}
