package cn.zua.smbms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.User;
import cn.zua.smbms.dao.UserMapper;
import cn.zua.smbms.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public int insert(User record) {
		// TODO Auto-generated method stub
		return userMapper.insert(record);
	}

	@Override
	public User selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> selectAll() {
		// TODO Auto-generated method stub
		return userMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(User record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public User selectByUserCode(String userCode , String userPwd) {
		// TODO Auto-generated method stub
		return userMapper.selectByUserCode(userCode , userPwd);
	}

	@Override
	public Page<User> selectUserByPageAndCondition(String userName, Long userRole, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Page<User> uPage = new Page<User>();
		uPage.setPageIndex(pageIndex);
		uPage.setPageSize(pageSize);
		uPage.setTotalCount(userMapper.getCountByCondition(userName, userRole));
		uPage.setPageList(userMapper.selectUserByPageAndCondition(userName, userRole, 
				( pageIndex-1 ) * pageSize , pageSize));
		 
		return uPage;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public boolean updatePwd(Long id, String newPwd) {
		// TODO Auto-generated method stub
		return userMapper.updatePwd(id, newPwd);
	}

	@Override
	public User selectUserCodeExist(String userCode) {
		// TODO Auto-generated method stub
		return userMapper.selectUserCodeExist(userCode);
	}

}
