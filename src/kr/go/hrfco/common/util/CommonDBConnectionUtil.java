package kr.go.hrfco.common.util;

import java.sql.Connection;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <pre>
 * @ClassName   : CommonDBConnectionUtil
 * @Description : DB Connection 상태 확인 및 문제 발생 시, JSON 기반 임시 데이터 표출 클래스
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
public class CommonDBConnectionUtil {

	@Autowired
    private DataSource dataSource;
	
	private final Logger log = LoggerFactory.getLogger(CommonDBConnectionUtil.class);
	
	private static final String USE_BACKUP = "USE_BACKUP";

	/**
	 * DB Connection 상태 확인
	 *
	 * <pre>
	 * Interceptor 에서 확인
	 * 
	 * if (commonDBConnectionUtil.isDatabaseAlive()) {
	 * 		commonDBConnectionUtil.disableUseBackup();
	 * } else {
	 * 		commonDBConnectionUtil.enableUseBackup();
	 * }
	 * </pre>
	 *
	 * @author 최준규
	 * @since 2025.11.05
	 * @return boolean
	*/
	
	public boolean isDatabaseAlive() {
	    try (Connection connection = dataSource.getConnection()) {
	        return connection.isValid(3);
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	/**
	 * JSON 사용 상태 확인 
	 * - DB Connection 오류가 지속될 경우, Connection 시도를 건너뛰고 바로 백업 데이터로 넘어감 (지속적인 Connection 시도 방지)
	 *
	 * <pre>
	 * DAO 에서 사용
	 * if (commonDBConnectionUtil.isUseBackup()) {
	 * 		return commonDBConnectionUtil.defaultData();
	 * }
	 * </pre>
	 *
	 * @author 최준규
	 * @since 2025.11.05
	 * @return boolean
	*/
	
	public boolean isUseBackup() {
		try {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			
			if (requestAttributes == null) {return false;}
			
			HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
			
			return session != null && Boolean.TRUE.equals(session.getAttribute(USE_BACKUP));
		} catch (Exception ignored) {}
		return false;
	}

	/**
	 * 백업 데이터 사용으로 전환
	 *
	 * @author 최준규
	 * @since 2025.11.05
	*/
	
	public void enableUseBackup() {
		try {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			
			if (requestAttributes == null) {return;}
			
			HttpSession httpSession = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
			
			if (httpSession != null) {httpSession.setAttribute(USE_BACKUP, true);}
		} catch (Exception ignored) {}
	}

	/**
	 * 백업 데이터 비사용으로 전환 (DB Connect)
	 *
	 * @author 최준규
	 * @since 2025.11.05
	*/
	
	public void disableUseBackup() {
		try {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			
			if (requestAttributes == null) {return;}
			
			HttpSession httpSession = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
			
			if (httpSession != null) {httpSession.removeAttribute(USE_BACKUP);}
		} catch (Exception ignored) {}
	}
}
