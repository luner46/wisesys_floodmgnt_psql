package kr.go.hrfco.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import kr.go.hrfco.common.service.CommonLoggerService;

/**
 * <pre>
 * @ClassName   : CommonInterceptor.java
 * @Description : 인터셉터 클래스
 * @Modification 
 *
 * -----------------------------------------------------
 * 2025.04.18, 최준규, 최초 생성
 *
 * </pre>
 * @author 최준규
 * @since 2025.04.18
 * @version 1.0
 * @see reference
 *
 * @Copyright (c) 2025 by wiseplus All right reserved.
 */

public class CommonInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	public static final String LOGGING_FLAG = "isLogged";
	
	@Value("#{config['ipWhiteList']}")
	private String configIpWhiteList;
	
	@Autowired
	CommonLoggerService commonLoggerService;
	
	@Autowired
	CommonDBConnectionUtil commonDBConnectionUtil;
	
	/**
	 * preHandle (request 들어가기 전에 수행)
	 *
	 * @author 최준규
	 * @since 2025.09.30
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @param Object handler
	 * @return boolean
	*/
	
	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = "";
		String contextPath = "";
		String sessionId = "";
		HttpSession session = null;

		try {
			if (handler instanceof ResourceHttpRequestHandler) {return true;}
			
			if (Boolean.TRUE.equals(request.getAttribute("isLogged"))) {return true;}
			
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {return true;}

			session = request.getSession(false);
            requestURI = request.getRequestURI() == null ? "" : request.getRequestURI();
			contextPath = request.getContextPath();
			
			if (contextPath != null && !contextPath.isEmpty() && requestURI.startsWith(contextPath)) {
            	requestURI = requestURI.substring(contextPath.length());
            }

			sessionId = (session != null && session.getId() != null) ? session.getId() : "NO_SESSION";
			
			request.setAttribute("sessionId", sessionId);
            request.setAttribute("requestURI", requestURI);
		
		} catch (IllegalStateException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}

	    return true;
	}

	/**
	 * postHandle (View 반환 전에 수행)
	 *
	 * @author 최준규
	 * @since 2025.09.30
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @param Object handler
	 * @param ModelAndView modelAndView
	 * @return boolean
	*/
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//log.info("[postHandle]");
		try {
			if (commonDBConnectionUtil.isDatabaseAlive()) {commonDBConnectionUtil.disableUseBackup();}
			else {commonDBConnectionUtil.enableUseBackup();}
		} catch (IllegalStateException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
	}

	/**
	 * afterCompletion (View 반환이 이루어진 이후에 수행)
	 *
	 * @author 최준규
	 * @since 2025.09.30
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @param Object handler
	 * @param Exception exception
	 * @return boolean
	*/
	
	@Override
	public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//log.info("[afterCompletion]");
		String requestURI = "";
		String sessionId = "";
		
		try {
			requestURI = (String) request.getAttribute("requestURI");
			sessionId = (String) request.getAttribute("sessionId");
			
			if (Boolean.TRUE.equals(request.getAttribute("isLogged"))) {return;}
			request.setAttribute("isLogged", Boolean.TRUE);

			if (requestURI == null) {return;}
			else {commonLoggerService.insertUserLog(requestURI, sessionId);}
		} catch (IllegalStateException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
	}
}
