package kr.go.hrfco.main.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.go.hrfco.api.me.vo.UffBsceDataVO;
import kr.go.hrfco.api.me.vo.MeWlDataVO;
import kr.go.hrfco.api.util.CommonCorrectionValueUtil;
import kr.go.hrfco.api.util.CommonSnapShotStorage;
import kr.go.hrfco.api.vo.MeWlDataSnapShotVO;
import kr.go.hrfco.api.vo.UffBsceDataSnapShotVO;
import kr.go.hrfco.main.dao.MainDAO;
import kr.go.hrfco.main.vo.LinkDataVO;

/**
 * <pre>
 * @ClassName   : MainService
 * @Description : 메인 데이터 표출 Service
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
public class MainService {

	private final Logger log = LoggerFactory.getLogger(MainService.class);

	@Autowired
    private CommonSnapShotStorage commonSnapShotStorage;
	
    @Autowired
    private MainDAO dao;

	private final double DEFAULT_WL_VAL = 10;
	private final double DEFAULT_FW_VAL = 10;

	/**
	 * 실시간 환경부 수위 관측소 10분 데이터 리턴 Service
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<MeWlDataVO> dataList
	*/
	
	public List<MeWlDataVO> selectRealTimeMeWl10min(){
		MeWlDataSnapShotVO snapShotVO = commonSnapShotStorage.getMeWlDataSnapShot();
		List<MeWlDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = snapShotVO.getMeWlDataList();
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}

	/**
	 * 과거 특정 시점 환경부 수위 관측소 데이터 리턴 Service
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<MeWlDataVO> dataList
	*/
	
	public List<MeWlDataVO> selectPastMeWl10min(String targetTime){
		List<MeWlDataVO> dataList = new ArrayList<>();
		HashMap<String, String> param = new HashMap<>();
		
		try {
			param.put("targetTime", targetTime);
			
			dataList = dao.selectPastMeWl10min(param);
			getValidValueWithDefault(dataList);
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}

	/**
	 * 실시간 Uff Bsce 10분 데이터 리턴 Service
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<UffBsceDataVO> dataList
	*/
	
	public List<UffBsceDataVO> selectRealTimeUffBsceData(){
		UffBsceDataSnapShotVO snapShotVO = commonSnapShotStorage.getUffBsceDataSnapShot();
		List<UffBsceDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = snapShotVO.getUffBsceDataList();
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}
	
	public List<LinkDataVO> selectAy01LinkData(){
		List<LinkDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = dao.selectAy01LinkData();
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}

	public List<LinkDataVO> selectAy01NodeData(){
		List<LinkDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = dao.selectAy01NodeData();
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
