package kr.go.hrfco.common.service;

import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.go.hrfco.common.util.CommonLoggerUtil;

/**
 * <pre>
 * @ClassName   : CommonLoggerDAO
 * @Description : 사용자 로그 입력 DAO
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

@Service
public class CommonLoggerService {

	private static final Logger log = LoggerFactory.getLogger(CommonLoggerService.class);
	
	private static final Map<String, String> MENU_MAP = Map.ofEntries(
	    Map.entry("/hello", "01")
	);

	/**
	 * 사용자 접속 로그 입력 Service
	 * 
	 * <pre>
	 * VO 사용 시, Setter 추가 후 DAO로 전달
	 * CommonUserLogVO userLogVO = new CommonUserLogVO();
	 * userLogVO.setMenuCd(menuCd);
	 * </pre>
	 * 
	 * @author 최준규
	 * @since 2025.09.30
	 * @param String requestURI 페이지 URI
	 * @param String sessionId 접속자 세션 아이디
	*/
	
	public void insertUserLog(String requestURI, String sessionId) {
		if (requestURI == null) {return;}
		
		String menuCd = MENU_MAP.getOrDefault(requestURI, "99");
		if ("99".equals(menuCd)) {return;}
		
		WeakHashMap<String, String> param = new WeakHashMap<>();

		CommonLoggerUtil.INSTANCE.submit(() -> {
			try {
				param.put("menuCd", menuCd);
				param.put("sessionId", sessionId);
			
				// loggerDAO.insertUserLog(userLogVO);
			} catch (IllegalStateException ie) {
				log.error(ie.toString());
			} catch (NullPointerException ne) {
				log.error(ne.toString());
			} catch (Exception e) {
				log.error("logging fail: {}", e.toString());
			}
		});
	}
}
