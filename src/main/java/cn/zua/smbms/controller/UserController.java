package cn.zua.smbms.controller;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.zua.smbms.bean.Page;
import cn.zua.smbms.bean.Role;
import cn.zua.smbms.bean.User;
import cn.zua.smbms.service.RoleService;
import cn.zua.smbms.service.UserService;
import cn.zua.smbms.util.Constants;

@RequestMapping("/user")
@Controller
public class UserController extends BaseController {
	
	@Resource
	private UserService userService ;
	@Resource
	private RoleService roleService ;
	
	/**
	 * 登录
	 * @param user
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dologin.html" , method = RequestMethod.POST)
	public String login(User user , HttpSession session , HttpServletRequest request){
		
		User u = userService.selectByUserCode(user.getUserCode(), user.getUserPassword());
		if(u != null ){
			session.setAttribute(Constants.USER_SESSION, u);
			return "frame";
		}
		request.setAttribute("error", "用户名或密码不正确");
		return "login";	
	}
	
	/**
	 * 退出
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout.html")
	public String logout(HttpSession session){
		session.removeAttribute(Constants.USER_SESSION);
		return "login";
	}
	
	/**
	 * 显示用户列表
	 * @param request
	 * @param queryName
	 * @param queryUserRole
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/userlist")
	public String userlist(HttpServletRequest request , 
			@RequestParam(value="queryname" , required=false) String queryName ,
			@RequestParam(value="queryUserRole" , required=false) String queryUserRole ,
			@RequestParam(value="pageIndex" , required=false) Integer pageIndex){
		
		int pageSize = Constants.PAGESIZE; //设置页面容量
		int _pageIndex = 1;   //当前页码
		Long _queryUserRole = null;  //用户角色
		
		
		
		if(pageIndex != null && !pageIndex.equals("")){
			_pageIndex = Integer.valueOf(pageIndex);
		}
		if(queryUserRole != null && !queryUserRole.equals("")){
			_queryUserRole = Long.valueOf(queryUserRole);
		}
		Page<User> uPage = userService.selectUserByPageAndCondition(queryName, _queryUserRole,
				_pageIndex, pageSize);	
		
		for (User u : uPage.getPageList()) {
			u.setUserRoleName(roleService.selectByPrimaryKey(u.getUserRole()).getRoleName());
		}
		
		if(queryName != null && !"".equals(queryName)){
			request.setAttribute("queryName", queryName);
		}
		if(queryUserRole != null && !"".equals(queryUserRole)){
			request.setAttribute("queryUserRole", queryUserRole);
		}
		
		List<Role> roleList = roleService.selectAll();
		request.setAttribute("roleList", roleList);
		request.setAttribute("userPage", uPage);
		return "userlist";
	}
	
	/**
	 * 删除用户
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/deleteUser.do" , method=RequestMethod.GET)
	@ResponseBody
	public String deleteUser(@RequestParam String uid){
		Map<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(uid)){
			resultMap.put("delResult", "notexist");
		}else {
			if(userService.deleteByPrimaryKey(Long.valueOf(uid)) > 0){
				resultMap.put("delResult", "true");
			}else{
				resultMap.put("delResult", "false");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	
	/**
	 * 根据id查看用户详情
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "view/{id}" , method = RequestMethod.GET)
	public String View(@PathVariable String id , Model model , HttpServletRequest request ){
		User user = new User();
		user = userService.selectByPrimaryKey(Long.valueOf(id));
		user.setUserRoleName(roleService.selectByPrimaryKey(user.getUserRole()).getRoleName());
		model.addAttribute(user);
		return "userview";
	}
	
	
	/**
	 * 跳转到添加用户界面
	 * @return
	 */
	@RequestMapping(value = "/useradd.html" , method = RequestMethod.GET)
	public String goUserAdd(@ModelAttribute("user") User user){
		return "useradd";
	}
	
	
	/**
	 * 验证用户编码是否存在
	 * jquery的方法验证
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/ucexist.json")
	@ResponseBody
	public String checkUserCode(String userCode){
		
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(userCode)){
			resultMap.put("userCode", "noexist");
		}else {
			User user = userService.selectUserCodeExist(userCode) ;
			if(user != null){
				resultMap.put("userCode", "exist");
			}else {
				resultMap.put("userCode", "noexist");
			}
		}
		
		return JSONArray.toJSONString(resultMap);
	}
	
	/**
	 * 添加用户
	 * 多文件上传
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/useraddsave.html" , method = RequestMethod.POST)
	public String saveUser(@Valid User user ,BindingResult bindingResult , HttpSession session ,
			@RequestParam(value ="attachs", required = false) 	MultipartFile[] attachs , HttpServletRequest request){
		
		String errorInfo = null;
		boolean flag = true ;
		String path = session.getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
		for (int i = 0 ; i < attachs.length ; i ++) {
			MultipartFile attach = attachs[i];
			if(!attach.isEmpty()){
				if(i == 0){
					errorInfo = "uploadFileError";
				}else if (i == 1) {
					errorInfo = "uploadWpError";
				}
				String oldFileName = attach.getOriginalFilename();//原文件名
				String prefix = FilenameUtils.getExtension(oldFileName);//原文件后缀
				int filesize = 500000;
				if(attach.getSize() > filesize){ //上传文件大小不得超过500k
					request.setAttribute(errorInfo, "上传文件大小不得超过500k");
					flag = false;
				}else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png") 
	            		|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){
					String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000000) + "_Personal.jpg";
					File targetFile = new File(path, fileName);
					if(!targetFile.exists()){
						targetFile.mkdirs();
					}
					//保存
					try {
						attach.transferTo(targetFile);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute(errorInfo, "上传失败");
						flag = false;	
					} 
				}else {
					request.setAttribute(errorInfo, "上传图片格式不正确");
					flag = false;
				}
			}
		}
		if(bindingResult.hasErrors()){
			return "useradd";
		}
		User u = (User)session.getAttribute(Constants.USER_SESSION);
		if(flag && u != null ){
			user.setCreationDate(new Date());	
			user.setCreatedBy(u.getId());
			userService.insert(user);
			return "redirect:/user/userlist.html";
		}
		
		return "login";
	}
	
	/**
	 * 获取用户信息并跳转到修改页面
	 * @param uid
	 * @return
	 */
	@RequestMapping("usermodify.html")
	public String toModifyUser(String uid , Model model){
		User user = userService.selectByPrimaryKey(Long.valueOf(uid));
		model.addAttribute("user", user);
		return "usermodify";
	}
	
	/**
	 * 1.从数据库中取出了对象，并把对象放到了Map中，
	 * 2.SpringMVC从map中取出user对象，并把表单的请求参数赋给该User对象的对应属性
	 * 3.SpringMVC把上述对象传入目标方法的参数
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id" , required = false) Long id, Map<String,Object> map){
		if(id != null){
			User user = userService.selectByPrimaryKey(id);
			map.put("myuser", user);
		}
	}
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping("/usermodifysave.html")
	public String modifyUser(@ModelAttribute("myuser") @Valid User user ,BindingResult bindingResult ,HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
		user.setModifyDate(new Date());
		userService.updateByPrimaryKey(user);	
		return "redirect:/user/userlist.html";
	}
	
	/**
	 * 跳转到修改密码界面
	 * @return
	 */
	@RequestMapping("/pwdmodify.do")
	public String toModifyPwd(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "login";
		}
		return "pwdmodify";
	}
	
	@RequestMapping(value="/getPwdByUserId.do" , method = RequestMethod.POST)
	@ResponseBody
	public Object getPwdByUserId(String oldpassword , HttpSession session){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(session.getAttribute(Constants.USER_SESSION) == null){
			resultMap.put("result", "sessionError");
		}else if (StringUtils.isNullOrEmpty(oldpassword)) {
			resultMap.put("result", "error");
		}else {
			String sessionPwd = ((User)session.getAttribute(Constants.USER_SESSION)).getUserPassword();
			if (sessionPwd.equals(oldpassword)) {
				resultMap.put("result", "true");
			} else {
				resultMap.put("result", "false");
			}
		}	
		return JSONArray.toJSONString(resultMap);
	}
	
	@RequestMapping(value = "/pwdsave.html" , method = RequestMethod.POST)
	public String modifyPwdSave(@RequestParam(value="newpassword") String newPassword,
			HttpSession session, HttpServletRequest request){
		boolean flag = false ;
		Object obj = session.getAttribute(Constants.USER_SESSION);
		if(obj != null && !StringUtils.isNullOrEmpty(newPassword)){
			flag = userService.updatePwd(((User)obj).getId(), newPassword);
		}
		if(flag){
			request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功，请退出系统重新登录");
			session.removeAttribute(Constants.USER_SESSION);
			return "login";
		}else {
			request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败");
		}
		return "pwdmodify";
	}
}















