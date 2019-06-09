package cn.zua.smbms.service.impl;


import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Sp;
import cn.zua.smbms.dao.SpMapper;
import cn.zua.smbms.service.SpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpServiceImpl implements SpService {

	@Resource
	private SpMapper spMapper ;

	@Override
	public Page<Sp> selectSpByPageAndCondition(String spName, String spCd, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Page<Sp> pPage = new Page<Sp>();
		pPage.setPageIndex(pageIndex);
		pPage.setPageSize(pageSize);
		pPage.setTotalCount(spMapper.getCountByCondition(spName, spCd));
		pPage.setPageList(spMapper.selectSpByPageAndCondition(spName, spCd, (pageIndex-1)*pageSize, pageSize));
		return pPage;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return spMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Sp record) {
		// TODO Auto-generated method stub
		return spMapper.insert(record);
	}

	@Override
	public Sp selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return spMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Sp record) {
		// TODO Auto-generated method stub
		return spMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Sp> selectAll() {
		// TODO Auto-generated method stub
		return spMapper.selectAll();
	}



}
