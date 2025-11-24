package kr.go.hrfco.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * <pre>
 * @ClassName   : CacheHeadersFilter
 * @Description : 캐시 저장 방지 필터
 * @Modification
 *
 * -----------------------------------------------------
 * 2025.09.30, 최준규, 최초 생성
 *
 * </pre>
 * @author 최준규
 * @since 2025.09.30
 * @version 1.0
 * @see reference
 *
 * @Copyright (c) 2025 by wiseplus All right reserved.
 */

@Component
public class CacheHeadersFilter implements Filter{

	/**
	 * 캐시 저장 방지 필터
	 *
	 * @author 최준규
	 * @since 2025.09.30
	 * @param ServletRequest request
	 * @param ServletResponse response
	 * @param FilterChain chain
	 * @throws IOException
	 * @throws ServletException
	*/
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		res.setHeader("Pragma", "no-cache");
	    res.setDateHeader("Expires", 0);
	    chain.doFilter(request, response);
	}
}
