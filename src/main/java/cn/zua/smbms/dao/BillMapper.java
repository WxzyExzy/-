package cn.zua.smbms.dao;

import cn.zua.smbms.bean.Bill;

import java.util.List;

public interface BillMapper {
	

	/**
	 * 根据条件分页查询订单信息
	 * @param userName
	 * @param userRole
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<Bill> selectBillByPageAndCondition(Long proId, String proName, Integer isPayment, int pageIndex, int pageSize);
	
	/**
	 * 根据条件查询订单总数
	 * @param userName
	 * @param userRole
	 * @return
	 */
	int getCountByCondition(Long proId, String proName, Integer isPayment);
	
    int deleteByPrimaryKey(Long id);

    int insert(Bill record);

    Bill selectByPrimaryKey(Long id);

    List<Bill> selectAll();

    int updateByPrimaryKey(Bill record);
}