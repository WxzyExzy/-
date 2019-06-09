package cn.zua.smbms.controller;


import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Sp;
import cn.zua.smbms.service.SpService;
import cn.zua.smbms.util.Constants;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sys")
public class SpController {

	@Resource
	private SpService spService;


	/**
	 * 获取供应商列表
	 * @param model
	 * @param querySpCd
	 * @param querySpName
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/sp/splist.do")
	public String getAllSp(Model model ,
			@RequestParam(value="querySpName" , required = false) String querySpName ,
			@RequestParam(value="querySpCd" , required = false) String querySpCd ,
			@RequestParam(value="pageIndex" , required = false) String pageIndex){

		int pageSize = Constants.PAGESIZE; //设置页面容量
		int _pageIndex = 1;   //当前页码

		if(pageIndex != null && !pageIndex.equals("")){
			_pageIndex = Integer.valueOf(pageIndex);
		}

		Page<Sp> pPage = spService.selectSpByPageAndCondition(querySpName, querySpCd,
				_pageIndex, pageSize);

		if(querySpName != null && !querySpName.equals("")){
			model.addAttribute("querySpName", querySpName);
		}
		if(querySpCd != null && !querySpCd.equals("")){
			model.addAttribute("querySpCd", querySpCd);
		}

		model.addAttribute("spPage", pPage);

		return "splist";
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("/sp/add.html")
	public String toSaveSp(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		return "spadd";
	}

	/**
	 * 添加供应商
	 * @param sp
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/sp/addsave.html" , method = RequestMethod.POST)
	public String saveSp(Sp sp , HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		spService.insert(sp);

		return "redirect:/sys/sp/splist.do";
	}

	/**
	 * 根据id删除
	 * @param spid
	 * @return
	 */
	@RequestMapping("/sp/delete.do")
	@ResponseBody
	public String deleteSp(String spid){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(spid)){
			resultMap.put("delResult", "notexist");
		}else {
			if(spService.deleteByPrimaryKey(Long.valueOf(spid)) > 0){
				resultMap.put("delResult", "true");
			}else {
				resultMap.put("delResult", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 根据id查看详情
	 * @param spid
	 * @param model
	 * @return
	 */
	@RequestMapping("/sp/view.do")
	public String getSpById(Long spid , Model model){
		Sp sp = spService.selectByPrimaryKey(spid);
		model.addAttribute(sp);
		return "spview";
	}

	/**
	 * 根据id查询信息并跳转到修改页面
	 * @param spid
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/sp/modify.do")
	public String toModifysp(Long spid , HttpSession session , Model model){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		Sp sp = spService.selectByPrimaryKey(spid);
		model.addAttribute(sp);
		return "spmodify";
	}

	@ModelAttribute(value = "sp")
	public Sp getSp(@RequestParam(value = "id" , required = false)Long id ){
		if(id != null ){
			Sp sp = spService.selectByPrimaryKey(id);
			return sp;
		}
		return null ;

	}

	/**
	 * 修改供应商信息
	 * @param sp
	 * @param session
	 * @return
	 */
	@RequestMapping("/sp/modifysave.html")
	public String modifySp(@ModelAttribute Sp sp , HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		spService.updateByPrimaryKey(sp);
		return "redirect:/sys/sp/splist.do";
	}
}
