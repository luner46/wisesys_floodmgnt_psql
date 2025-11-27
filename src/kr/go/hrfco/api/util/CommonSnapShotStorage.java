package kr.go.hrfco.api.util;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;

import kr.go.hrfco.api.vo.MeWlDataSnapShotVO;
import kr.go.hrfco.api.vo.UffBsceDataSnapShotVO;

/**
 * <pre>
 * @ClassName   : CommonSnapShotStorage
 * @Description : SnapShot 데이터 객체 저장 스토리지
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
public class CommonSnapShotStorage {

	/**
	 * JSON 데이터가 저장될 Atomic 저장소 선언 후 초기화
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	private final AtomicReference<MeWlDataSnapShotVO> realTimeMeWlData = new AtomicReference<>(MeWlDataSnapShotVO.empty());
	private final AtomicReference<UffBsceDataSnapShotVO> realTimeUffBsceData = new AtomicReference<>(UffBsceDataSnapShotVO.empty());

	/**
	 * JSON 데이터의 SnapShot 시점 재할당 (신규 데이터로 갱신)
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public void replaceMeWlDataSnapShot(MeWlDataSnapShotVO meWlDataSnapShot) {realTimeMeWlData.set(meWlDataSnapShot);}
	public void replaceUffBsceDataSnapShot(UffBsceDataSnapShotVO uffBsceDataSnapShot) {realTimeUffBsceData.set(uffBsceDataSnapShot);}

	/**
	 * Atomic 저장소 데이터 호출
	 *
	 * @author 최준규
	 * @since 2025.11.27
	*/
	
	public MeWlDataSnapShotVO getMeWlDataSnapShot(){return realTimeMeWlData.get();}
	public UffBsceDataSnapShotVO getUffBsceDataSnapShot(){return realTimeUffBsceData.get();}
}
