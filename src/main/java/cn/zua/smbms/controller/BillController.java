package cn.zua.smbms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.sun.tools.internal.jxc.ap.Const;

import cn.zua.smbms.bean.Bill;
import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Provider;
import cn.zua.smbms.bean.User;
import cn.zua.smbms.service.BillService;
import cn.zua.smbms.service.ProviderService;
import cn.zua.smbms.util.Constants;

@Controller
@RequestMapping("/sys")
public class BillController {
	
	@Resource
	private BillService billService;
	@Resource
	private ProviderService providerService;
	
	
	/**
	 * 显示列表
	 * @param queryProductName
	 * @param queryProviderId
	 * @param queryIsPayment
	 * @param pageIndex
	 * @param model
	 * @return
	 */
	@RequestMapping("/bill/list.do")
	public String getBillByPage(String queryProductName , Long queryProviderId , 
			Integer queryIsPayment , Integer pageIndex , Model model) {
		
		int pageSize = Constants.PAGESIZE;
		int _pageIndex = 1;
		if(pageIndex != null && !pageIndex.equals("")){
			_pageIndex = pageIndex;
		}
		
		Page<Bill> bPage = billService.selectBillByPageAndCondition(queryProviderId, queryProductName,
				queryIsPayment, _pageIndex, pageSize);
		List<Provider> providerList = providerService.selectAll();
		for(Bill bill : bPage.getPageList()){
			bill.setProviderName(providerService.selectByPrimaryKey(bill.getProviderId()).getProName());
		}
		if(queryProductName != null && !queryProductName.equals("")){
			model.addAttribute("queryProductName",queryProductName);
		}
		if(queryProviderId != null && !queryProviderId.equals("")){
			model.addAttribute("queryProviderId",queryProviderId);
		}
		if(queryIsPayment != null && !queryIsPayment.equals("")){
			model.addAttribute("queryIsPayment",queryIsPayment);
		}
		model.addAttribute("billPage", bPage);
		model.addAttribute("providerList", providerList);
		return "billlist";
	}
	
	/**
	 * 根据id删除订单信息
	 * @param billid
	 * @return
	 */
	@RequestMapping("/bill/delete.do")
	@ResponseBody
	public String deleteBill(String billid){
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(billid)){
			resultMap.put("delResult", "notexist");
		}else {
			if(billService.deleteByPrimaryKey(Long.valueOf(billid)) > 0){
				resultMap.put("delResult", "true");
			}else {
				resultMap.put("delResult", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	
	/**
	 * 跳转到添加页面
	 * @param session
	 * @return
	 */
	@RequestMapping("bill/add.html")
	public String toSaveBill(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		return "billadd";
	}
	
	@RequestMapping(value = "/bill/getproviderlist.do",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getProviderList(){
		List<Provider> pList = providerService.selectAll();
		return JSONArray.toJSONString(pList);
	}
	
	/**
	 * 添加订单
	 * @param bill
	 * @param session
	 * @return
	 */
	@RequestMapping("bill/addsave.html")
	public String saveBill(Bill bill , HttpSession session){
		User user = (User)session.getAttribute(Constants.USER_SESSION);
		bill.setCreatedBy(user.getId());
		bill.setCreationDate(new Date());
		if(billService.insert(bill) > 0){
			return "redirect:/sys/bill/list.do";	
		}
		return "billadd";
	}
	
	/**
	 * 根据id查看订单详情
	 * @param billid
	 * @return
	 */
	@RequestMapping("/bill/view.do")
	public String getBillById(Long billid , Model model){
		Bill bill = billService.selectByPrimaryKey(billid);
		bill.setProviderName(providerService.selectByPrimaryKey(bill.getProviderId()).getProName());
		model.addAttribute("bill", bill);
		return "billview";
	}
	
	/**
	 * 跳转到修改页面
	 * @param billid
	 * @param model
	 * @return
	 */
	@RequestMapping("/bill/modify.do")
	public String toModify(Long billid , Model model , HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		Bill bill = billService.selectByPrimaryKey(billid);
		model.addAttribute(bill);
		return "billmodify";
	}
	
	
	
	
	@ModelAttribute
	public void getBill(@RequestParam(value = "id" , required = false) Long billid , Map<String, Object> map){
		if(billid != null){
			Bill bill = billService.selectByPrimaryKey(billid);
			map.put("mybill", bill);
		}
	}
	/**
	 * 修改
	 * @param bill
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/bill/modifysave.html" , method = RequestMethod.POST)
	public String modifyBill(@ModelAttribute("mybill") Bill bill , HttpSession session){
		bill.setModifyDate(new Date());
		bill.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		billService.updateByPrimaryKey(bill);
		return "redirect:/sys/bill/list.do";
	}
	
}
