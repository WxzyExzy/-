package cn.zua.smbms.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Provider;
import cn.zua.smbms.dao.ProviderMapper;
import cn.zua.smbms.service.ProviderService;

@Service
public class ProviderServiceImpl implements ProviderService {
	
	@Resource
	private ProviderMapper providerMapper ;
	
	@Override
	public Page<Provider> selectProviderByPageAndCondition(String proCode, String proName, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Page<Provider> pPage = new Page<Provider>();
		pPage.setPageIndex(pageIndex);
		pPage.setPageSize(pageSize);
		pPage.setTotalCount(providerMapper.getCountByCondition(proCode, proName));
		pPage.setPageList(providerMapper.selectProviderByPageAndCondition(proCode, proName, (pageIndex-1)*pageSize, pageSize));
		return pPage;
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return providerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Provider record) {
		// TODO Auto-generated method stub
		return providerMapper.insert(record);
	}

	@Override
	public Provider selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return providerMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Provider record) {
		// TODO Auto-generated method stub
		return providerMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Provider> selectAll() {
		// TODO Auto-generated method stub
		return providerMapper.selectAll();
	}
	
	

}
