package cn.zua.smbms.service;


import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Sp;

import java.util.List;

public interface SpService {

	/**
	 * 根据条件分页查询供应商信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<Sp> selectSpByPageAndCondition(String spName, String spCd, int pageIndex, int pageSize);


    int deleteByPrimaryKey(Long id);

    int insert(Sp record);

    Sp selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Sp record);

    List<Sp> selectAll();
}
