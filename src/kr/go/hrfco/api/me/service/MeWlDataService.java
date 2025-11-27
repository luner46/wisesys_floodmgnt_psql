package kr.go.hrfco.api.me.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.go.hrfco.api.me.dao.MeWlDataDAO;
import kr.go.hrfco.api.me.vo.MeWlDataVO;
import kr.go.hrfco.api.util.CommonCorrectionValueUtil;
import kr.go.hrfco.api.util.CommonSnapShotStorage;
import kr.go.hrfco.api.vo.MeWlDataSnapShotVO;

/**
 * <pre>
 * @ClassName   : MeWlDataService
 * @Description : 환경부 수위 관측소 10분 데이터 Service
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

@Service
public class MeWlDataService {
	
	private final Logger log = LoggerFactory.getLogger(MeWlDataService.class);
	
	private final double DEFAULT_WL_VAL = 10;
	private final double DEFAULT_FW_VAL = 10;
	
	@Autowired
	private MeWlDataDAO dao;

	@Autowired
    private CommonSnapShotStorage commonSnapShotStorage;

	/**
	 * 환경부 수위 관측소 10분 실시간 데이터 스냅샷 Service
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public void getRealTimeMeWlSnapShot() {
		List<MeWlDataVO> realTimeData = selectRealTimeMeWlData();
		
		MeWlDataSnapShotVO snapShot = new MeWlDataSnapShotVO(realTimeData);
		
		commonSnapShotStorage.replaceMeWlDataSnapShot(snapShot);
	}

	/**
	 * 환경부 수위 관측소 10분 실시간 데이터 호출 Service
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	private List<MeWlDataVO> selectRealTimeMeWlData() {
		List<MeWlDataVO> dataList = new ArrayList<>();
		HashMap<String, String> param = new HashMap<>();
		String realTime = "";
		
		try {
			realTime = CommonCorrectionValueUtil.getFormattedRealTime();

			param.put("realTime", realTime);
			
			dataList = dao.selectRealTimeMeWlData(param);
			
			getValidValueWithDefault(dataList);

			for (MeWlDataVO meWlDataVO : dataList) {
				meWlDataVO.setRealTime(realTime);
			}
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}

	/**
	 * 데이터 후처리 보정 및 보정
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	private void getValidValueWithDefault(List<MeWlDataVO> dataList) {
		try {
			int missingVal = -999;
			
			for (MeWlDataVO meWlDataVO : dataList) {
				double wlDisplayData = CommonCorrectionValueUtil.getDisplayVal(meWlDataVO.getWlOrgVal(), meWlDataVO.getWlSubVal(), DEFAULT_WL_VAL, missingVal);
				double fwDisplayData = CommonCorrectionValueUtil.getDisplayVal(meWlDataVO.getFwOrgVal(), meWlDataVO.getFwSubVal(), DEFAULT_FW_VAL, missingVal);

				meWlDataVO.setWlVal(wlDisplayData);
				meWlDataVO.setFwVal(fwDisplayData);
			}
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
	}
}
