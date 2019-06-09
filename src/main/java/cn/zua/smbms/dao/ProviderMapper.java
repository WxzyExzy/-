package cn.zua.smbms.dao;

import cn.zua.smbms.bean.Provider;

import java.util.List;

public interface ProviderMapper {
	
	
	/**
	 * 根据条件分页查询供应商信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Provider> selectProviderByPageAndCondition(String proCode, String proName, int pageIndex, int pageSize);
	
	/**
	 * 根据条件查询供应商总数
	 * @param userName
	 * @param userRole
	 * @return
	 */
	int getCountByCondition(String proCode, String proName);
	
	
    int deleteByPrimaryKey(Long id);

    int insert(Provider record);

    Provider selectByPrimaryKey(Long id);

    List<Provider> selectAll();

    int updateByPrimaryKey(Provider record);
}