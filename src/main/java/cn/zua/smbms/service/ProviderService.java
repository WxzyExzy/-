package cn.zua.smbms.service;


import java.util.List;

import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Provider;

public interface ProviderService {
	
	/**
	 * 根据条件分页查询供应商信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<Provider> selectProviderByPageAndCondition(String proCode, String proName, int pageIndex, int pageSize);
	
	
    int deleteByPrimaryKey(Long id);

    int insert(Provider record);

    Provider selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Provider record);
	
    List<Provider> selectAll();
}
