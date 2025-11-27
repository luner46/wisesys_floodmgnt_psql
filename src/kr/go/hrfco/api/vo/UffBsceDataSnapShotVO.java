package kr.go.hrfco.api.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.go.hrfco.api.me.vo.UffBsceDataVO;

/**
 * <pre>
 * @ClassName   : UffBsceDataSnapShotVO
 * @Description : Uff Bsce 데이터 SnapShot 데이터 VO
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

public class UffBsceDataSnapShotVO {
	
	private final List<UffBsceDataVO> dataList;

	/**
	 * DB 조회 데이터(dataList)를 복사 후 ReadOnly의 형태로 SnapShot 생성
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public UffBsceDataSnapShotVO(List<UffBsceDataVO> dataList) {
		this.dataList = Collections.unmodifiableList(new ArrayList<UffBsceDataVO>(dataList));
	}

	/**
	 * DB 조회 데이터(dataList) 반환
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public List<UffBsceDataVO> getUffBsceDataList(){return dataList;}

	/**
	 * 데이터가 비어있는 SnapShot Instance 생성 (초기화 및 비움)
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public static UffBsceDataSnapShotVO empty() {
		return new UffBsceDataSnapShotVO(Collections.<UffBsceDataVO>emptyList());
	}
}
