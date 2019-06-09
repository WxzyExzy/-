package cn.zua.smbms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.zua.smbms.util.Constants;

/**
 * 自定义登录拦截器
 * @author
 * create date 2017年12月18日
 */
public class SysInterceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = Logger.getLogger(SysInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		logger.debug("SysInterceptor preHandle ==============");
		HttpSession session = request.getSession();
		
		if(session.getAttribute(Constants.USER_SESSION) == null){
			//response.sendRedirect(request.getContextPath() + "/WEB-INF/jsp/login.jsp");
			System.out.println("===================== " + request.getContextPath() + "/WEB-INF/jsp/login.jsp");
			request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/login.jsp");
			return false;
		}
		return true;
	}
}
