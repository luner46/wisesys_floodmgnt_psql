package kr.go.hrfco.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.go.hrfco.api.me.vo.UffBsceDataVO;
import kr.go.hrfco.api.me.vo.MeWlDataVO;
import kr.go.hrfco.main.service.MainService;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * @ClassName   : MainController
 * @Description : View 반환 및 데이터 리턴
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

@Controller
public class MainController {
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
    private MainService service;

	/**
	 * 데이터 표출 View 반환
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return String "/main/me_wl_10min_view"
	*/
	
    @RequestMapping("/mainMeWl10min")
    public String mainMeWl10min(Model model) {
    	try {
    		return "/main/me_wl_10min_view";
    	} catch (IllegalArgumentException ie) {
    		log.error(ie.toString());
    		return "../../error/error";
    	} catch (NullPointerException ne) {
    		log.error(ne.toString());
    		return "../../error/error";
    	}
    }

	/**
	 * 실시간 환경부 수위 관측소 10분 데이터 리턴 Controller
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<MeWlDataVO> dataList
	*/
	
    @RequestMapping("/selectRealTimeMeWl10min")
    @ResponseBody
	public List<MeWlDataVO> selectRealTimeMeWl10min() throws JsonProcessingException {
		List<MeWlDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = service.selectRealTimeMeWl10min();
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}

	/**
	 * 과거 특정 시점 환경부 수위 관측소 10분 데이터 리턴 Controller
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<MeWlDataVO> dataList
	*/
	
    @RequestMapping("/selectPastMeWl10min")
	@ResponseBody
	public List<MeWlDataVO> selectPastMeWl10min(@RequestParam String targetTime) {
		List<MeWlDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = service.selectPastMeWl10min(targetTime);
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}

	/**
	 * 실시간 Uff Bsce 10분 데이터 리턴 Controller
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<UffBsceDataVO> dataList
	*/
	
    @RequestMapping("/selectRealTimeUffBsceData")
    @ResponseBody
	public List<UffBsceDataVO> selectRealTimUffBsceData() {
		List<UffBsceDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = service.selectRealTimeUffBsceData();
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (NullPointerException ne) {
			log.error(ne.toString());
		}
		
		return dataList;
	}
}

