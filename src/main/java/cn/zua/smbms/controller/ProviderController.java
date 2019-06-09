package cn.zua.smbms.controller;


import java.util.Date;
import java.util.HashMap;
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

import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Provider;
import cn.zua.smbms.bean.User;
import cn.zua.smbms.service.ProviderService;
import cn.zua.smbms.util.Constants;

@Controller
@RequestMapping("/sys")
public class ProviderController {
	
	@Resource
	private ProviderService providerService;
	
	
	/**
	 * 获取供应商列表
	 * @param model
	 * @param queryProCode
	 * @param queryProName
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/provider/list.do")
	public String getAllProvider(Model model , 
			@RequestParam(value="queryProCode" , required = false) String queryProCode ,
			@RequestParam(value="queryProName" , required = false) String queryProName ,
			@RequestParam(value="pageIndex" , required = false) String pageIndex){
		
		int pageSize = Constants.PAGESIZE; //设置页面容量
		int _pageIndex = 1;   //当前页码
		
		if(pageIndex != null && !pageIndex.equals("")){
			_pageIndex = Integer.valueOf(pageIndex);
		}
		
		Page<Provider> pPage = providerService.selectProviderByPageAndCondition(queryProCode, queryProName,
				_pageIndex, pageSize);	
		
		if(queryProCode != null && !queryProCode.equals("")){
			model.addAttribute("queryProCode", queryProCode);
		}
		if(queryProName != null && !queryProName.equals("")){
			model.addAttribute("queryProName", queryProName);
		}
		
		model.addAttribute("providerPage", pPage);
		
		return "providerlist";
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("/provider/add.html")
	public String toSaveProvider(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		return "provideradd";
	}
	
	/**
	 * 添加供应商
	 * @param provider
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/provider/addsave.html" , method = RequestMethod.POST)
	public String saveProvider(Provider provider , HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		provider.setCreationDate(new Date());
		provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		providerService.insert(provider);
		return "redirect:/sys/provider/list.do";
	}
	
	/**
	 * 根据id删除供应商
	 * @param proid
	 * @return
	 */
	@RequestMapping("/provider/delete.do")
	@ResponseBody
	public Map<String, String> deleteProvider(Long proid){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(proid == null || "".equals(proid)){
			resultMap.put("delResult", "notexist");
		}else{
			if(providerService.deleteByPrimaryKey(proid) > 0){
				resultMap.put("delResult", "true");
			}else {
				resultMap.put("delResult", "false");
			}
		}
		return resultMap;	
	}
	
	/**
	 * 根据id查看供应商详情
	 * @param proid
	 * @param model
	 * @return
	 */
	@RequestMapping("/provider/view.do")
	public String getProviderById(Long proid , Model model){
		Provider provider = providerService.selectByPrimaryKey(proid);
		model.addAttribute(provider);
		return "providerview";
	}
	
	/**
	 * 根据id查询供应商信息并跳转到修改页面
	 * @param proid
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/provider/modify.do")
	public String toModifyProvider(Long proid , HttpSession session , Model model){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		Provider provider = providerService.selectByPrimaryKey(proid);
		model.addAttribute(provider);
		return "providermodify";
	}
	
	@ModelAttribute(value = "provider")
	public Provider getProvider(@RequestParam(value = "id" , required = false)Long id ){
		if(id != null ){
			Provider provider = providerService.selectByPrimaryKey(id);
			return provider;
		}
		return null ;
		
	}
	
	/**
	 * 修改供应商信息
	 * @param provider
	 * @param session
	 * @return
	 */
	@RequestMapping("/provider/modifysave.html")
	public String modifyProvider(@ModelAttribute Provider provider , HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		provider.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		provider.setCreationDate(new Date());
		providerService.updateByPrimaryKey(provider);
		return "redirect:/sys//provider/list.do";
	}
}
