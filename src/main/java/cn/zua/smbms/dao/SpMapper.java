package cn.zua.smbms.dao;

import cn.zua.smbms.bean.Sp;

import java.util.List;

public interface SpMapper {


	/**
	 * 根据条件分页查询供应商信息
	 * @param spName
	 * @param spCd
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Sp> selectSpByPageAndCondition(String spName, String spCd, int pageIndex, int pageSize);

	/**
	 * 根据条件查询供应商总数
	 * @param userName
	 * @param userRole
	 * @return
	 */
	int getCountByCondition(String spName, String spCd);


    int deleteByPrimaryKey(Long id);

    int insert(Sp record);

    Sp selectByPrimaryKey(Long id);

    List<Sp> selectAll();

    int updateByPrimaryKey(Sp record);
}