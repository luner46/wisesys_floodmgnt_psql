package kr.go.hrfco.api.me.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.go.hrfco.api.me.dao.UffBsceDataDAO;
import kr.go.hrfco.api.me.vo.UffBsceDataVO;
import kr.go.hrfco.api.util.CommonCorrectionValueUtil;
import kr.go.hrfco.api.util.CommonSnapShotStorage;
import kr.go.hrfco.api.vo.UffBsceDataSnapShotVO;

/**
 * <pre>
 * @ClassName   : UffBsceDataService
 * @Description : Uff Bsce 실시간 데이터 Service
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
public class UffBsceDataService {
	
	private final Logger log = LoggerFactory.getLogger(UffBsceDataService.class);
	
	private final String DAFAULT_RMSE_VAL = "10";
	private final String DAFAULT_WL_VAL = "10";
	private final String DAFAULT_RN_VAL = "10";
	private final String DAFAULT_DUR_VAL = "10";
	private final String DAFAULT_OX_VAL = "10";
	
	@Autowired
	private UffBsceDataDAO dao;

	@Autowired
    private CommonSnapShotStorage commonSnapShotStorage;

	/**
	 * Uff Bsce 실시간 데이터 스냅샷 Service
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public void getRealTimeUffBsceSnapShot() {
		try {
			List<UffBsceDataVO> realTimeData = selectRealTimeUffBsceData();
			
			UffBsceDataSnapShotVO snapShot = new UffBsceDataSnapShotVO(realTimeData);
			
			commonSnapShotStorage.replaceUffBsceDataSnapShot(snapShot);
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
	}

	/**
	 * Uff Bsce 실시간 데이터 호출 Service
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	private List<UffBsceDataVO> selectRealTimeUffBsceData() {
		List<UffBsceDataVO> dataList = new ArrayList<>();
		HashMap<String, String> param = new HashMap<>();
		String realTime = "";
		
		try {
			realTime = CommonCorrectionValueUtil.getFormattedRealTime();
			
			param.put("realTime", realTime);
			
			dataList = dao.selectRealTimeUffBsceData(param);
			
			getValidValueWithDefault(dataList);

			for (UffBsceDataVO bsceVO : dataList) {
				bsceVO.setRealTime(realTime);
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
	
	private void getValidValueWithDefault(List<UffBsceDataVO> dataList) {
		try {
			int missingVal = -999;

			for (UffBsceDataVO uffBsceDataVO : dataList) {
				String rmseVal = Double.toString(CommonCorrectionValueUtil.getDisplayVal(Double.parseDouble(uffBsceDataVO.getOrgnlRmse()), Double.parseDouble(uffBsceDataVO.getSubRmse()), Double.parseDouble(DAFAULT_RMSE_VAL), missingVal));
				String wlVal = Double.toString(CommonCorrectionValueUtil.getDisplayVal(Double.parseDouble(uffBsceDataVO.getOrgnlWl()), Double.parseDouble(uffBsceDataVO.getSubWl()), Double.parseDouble(DAFAULT_WL_VAL), missingVal));
				String rnVal = Double.toString(CommonCorrectionValueUtil.getDisplayVal(Double.parseDouble(uffBsceDataVO.getOrgnlRn()), Double.parseDouble(uffBsceDataVO.getSubRn()), Double.parseDouble(DAFAULT_RN_VAL), missingVal));
				String durVal = Double.toString(CommonCorrectionValueUtil.getDisplayVal(Double.parseDouble(uffBsceDataVO.getOrgnlDur()), Double.parseDouble(uffBsceDataVO.getSubDur()), Double.parseDouble(DAFAULT_DUR_VAL), missingVal));
				String oxVal = Double.toString(CommonCorrectionValueUtil.getDisplayVal(Double.parseDouble(uffBsceDataVO.getOrgnlOx()), Double.parseDouble(uffBsceDataVO.getSubOx()), Double.parseDouble(DAFAULT_OX_VAL), missingVal));
				
				uffBsceDataVO.setRmseVal(rmseVal);
				uffBsceDataVO.setWlVal(wlVal);
				uffBsceDataVO.setRnVal(rnVal);
				uffBsceDataVO.setDurVal(durVal);
				uffBsceDataVO.setOxVal(oxVal);
			} 
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
	}
}
