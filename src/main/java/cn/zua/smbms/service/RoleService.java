package cn.zua.smbms.service;

import java.util.List;

import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Role;

public interface RoleService {
	
	/*
	 * 根据id获取角色信息
	 * @param id
	 * @return
	 *//*
	Role selectByPrimaryKey(Long id);

	*//*
	 * 获取所有角色信息
	 * @return
	 *//*
    List<Role> selectAll();*/

	/**
	 * 根据条件分页查询用户信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	/**
	 * 根据条件分页查询角色信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<Role> selectRoleByPageAndCondition(String proCode, String proName, int pageIndex, int pageSize);


	int deleteByPrimaryKey(Long id);

	int insert(Role record);

	Role selectByPrimaryKey(Long id);

	int updateByPrimaryKey(Role record);

	List<Role> selectAll();
}
