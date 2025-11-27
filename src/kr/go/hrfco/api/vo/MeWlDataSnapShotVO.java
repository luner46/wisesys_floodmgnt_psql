package kr.go.hrfco.api.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.go.hrfco.api.me.vo.MeWlDataVO;

/**
 * <pre>
 * @ClassName   : MeWlDataSnapShotVO
 * @Description : 환경우 수위 관측소 데이터 SnapShot 데이터 VO
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

public class MeWlDataSnapShotVO {
	
	private final List<MeWlDataVO> dataList;

	/**
	 * DB 조회 데이터(dataList)를 복사 후 ReadOnly의 형태로 SnapShot 생성
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public MeWlDataSnapShotVO(List<MeWlDataVO> dataList) {
		this.dataList = Collections.unmodifiableList(new ArrayList<MeWlDataVO>(dataList));
	}

	/**
	 * DB 조회 데이터(dataList) 반환
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public List<MeWlDataVO> getMeWlDataList(){return dataList;}

	/**
	 * 데이터가 비어있는 SnapShot Instance 생성 (초기화 및 비움)
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public static MeWlDataSnapShotVO empty() {
		return new MeWlDataSnapShotVO(Collections.<MeWlDataVO>emptyList());
	}
}
