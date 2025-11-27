package kr.go.hrfco.api.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.go.hrfco.api.me.service.MeWlDataService;
import kr.go.hrfco.api.me.service.UffBsceDataService;

/**
 * <pre>
 * @ClassName   : CommonScheduleUtil
 * @Description : 데이터 수집 Scheduler
 * @Modification
 *
 * -----------------------------------------------------
 * 2025.11.27, 최준규, 최초 생성
 *
 * </pre>
 * @author 최준규
 * @since 2025.11.27
 * @version 1.0
 * @see reference
 *
 * @Copyright (c) 2025 by wiseplus All right reserved.
 */

@Component
public class CommonScheduleUtil {
	
	private final Logger log = LoggerFactory.getLogger(CommonScheduleUtil.class);
	
	@Autowired
	private MeWlDataService meWlService;
	
	@Autowired
	private UffBsceDataService uffBsceService;

	/**
	 * 서버 부트 시 초기 실행
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	@PostConstruct
	public void init() {
		try {
			meWlService.getRealTimeMeWlSnapShot();
			uffBsceService.getRealTimeUffBsceSnapShot();
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	/**
	 * 10분 단위 데이터 수집
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	@Scheduled(cron = "0 2,12,22,32,42,52 * * * *")
	public void getRealTimeData() {
		try {
			meWlService.getRealTimeMeWlSnapShot();
			uffBsceService.getRealTimeUffBsceSnapShot();
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
	}
}
