package cn.zua.smbms.service;

import java.util.List;

import cn.zua.smbms.bean.Bill;
import cn.zua.smbms.bean.Page;

public interface BillService{
	
	
	/**
	 * 根据条件分页查询订单
	 * @param proId
	 * @param proName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<Bill> selectBillByPageAndCondition(Long proId, String proName, Integer isPayment, int pageIndex, int pageSize);
	
	
	int deleteByPrimaryKey(Long id);

    int insert(Bill record);

    Bill selectByPrimaryKey(Long id);

    List<Bill> selectAll();

    int updateByPrimaryKey(Bill record);
	
}
