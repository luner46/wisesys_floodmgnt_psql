package kr.go.hrfco.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * @ClassName   : CommonCorrectionValueUtil
 * @Description : 데이터 후처리 및 보정
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

public class CommonCorrectionValueUtil {

	private static final Logger log = LoggerFactory.getLogger(CommonCorrectionValueUtil.class);

	/**
	 * 현재 시간 보정 (10분 단위로 절삭)
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return String realTime
	*/
	
	public static String getFormattedRealTime() {
		String realTime = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		int minute = 0;
		int formattedMinute = 0;
		
		try {
			Calendar calendar = Calendar.getInstance();
			
			minute = calendar.get(Calendar.MINUTE);
			formattedMinute = minute - (minute % 10);
			
			calendar.set(Calendar.MINUTE, formattedMinute);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			realTime = format.format(calendar.getTime());
			
		} catch (IllegalArgumentException ie) {
			log.info(ie.toString());
		} catch (NullPointerException ne) {
			log.info(ne.toString());
		}
		
		return realTime;
	}

	/**
	 * 표출 값 설정
	 * 
	 * <pre>
	 * 	1순위 : orgVal (현 시점 데이터)
	 * 	2순위 : subVal (직전 시점 데이터)
	 * 	3순위 : defaultVal (기본 설정 데이터)
	 * </pre>
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return double defaultVal
	*/
	
	public static double getDisplayVal(double orgVal, double subVal, double defaultVal, int missingVal) {
		double displayVal = 0;
		
		try {
			if (orgVal != missingVal) {displayVal = orgVal;}
			else if (subVal != missingVal) {displayVal = subVal;}
			else {displayVal = defaultVal;}
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return displayVal;
	}
}
