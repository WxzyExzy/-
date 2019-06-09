package cn.zua.smbms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zua.smbms.bean.Bill;
import cn.zua.smbms.bean.Page;
import cn.zua.smbms.dao.BillMapper;
import cn.zua.smbms.service.BillService;

@Service
public class BillServiceImpl implements BillService {
	
	@Resource
	private BillMapper billMapper ;
	
	@Override
	public Page<Bill> selectBillByPageAndCondition(Long proId, String proName,
			Integer isPayment , int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Page<Bill> bPage = new Page<Bill>();
		bPage.setPageIndex(pageIndex);
		bPage.setPageSize(pageSize);
		bPage.setPageList(billMapper.selectBillByPageAndCondition(proId, proName,isPayment, 
				(pageIndex-1)*pageSize, pageSize));
		bPage.setTotalCount(billMapper.getCountByCondition(proId, proName , isPayment));
		
		return bPage;
	}
	

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return billMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Bill record) {
		// TODO Auto-generated method stub
		return billMapper.insert(record);
	}

	@Override
	public Bill selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return billMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Bill> selectAll() {
		// TODO Auto-generated method stub
		return billMapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(Bill record) {
		// TODO Auto-generated method stub
		return billMapper.updateByPrimaryKey(record);
	}


}
