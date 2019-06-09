package cn.zua.smbms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cn.zua.smbms.bean.Page;
import org.springframework.stereotype.Service;

import cn.zua.smbms.bean.Role;
import cn.zua.smbms.dao.RoleMapper;
import cn.zua.smbms.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Resource
	private RoleMapper roleMapper ;

	@Override
	public Page<Role> selectRoleByPageAndCondition(String proCode, String proName, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Page<Role> pPage = new Page<Role>();
		pPage.setPageIndex(pageIndex);
		pPage.setPageSize(pageSize);
		pPage.setTotalCount(roleMapper.getCountByCondition(proCode, proName));
		pPage.setPageList(roleMapper.selectProviderByPageAndCondition(proCode, proName, (pageIndex-1)*pageSize, pageSize));
		return pPage;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Role record) {
		return roleMapper.insert(record);
	}

	@Override
	public Role selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Role> selectAll() {
		// TODO Auto-generated method stub
		return roleMapper.selectAll();
	}

}
