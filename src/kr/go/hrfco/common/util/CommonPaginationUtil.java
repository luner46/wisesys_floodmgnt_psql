package kr.go.hrfco.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * @ClassName   : CommonPaginationUtil.java
 * @Description : 페이징 처리 유틸리티 클래스
 * @Modification 
 *
 * -----------------------------------------------------
 * 2025.04.15, 최준규, 최초 생성
 *
 * </pre>
 * @author 최준규
 * @since 2025.04.15
 * @version 1.0
 * @see reference
 *
 * @Copyright (c) 2025 by wiseplus All right reserved.
 */

public class CommonPaginationUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CommonPaginationUtil.class);
	
	/**
	 * 페이징 처리
	 * 
	 * @author 최준규
	 * @since 2025.04.15
	 * @param int selectPageNo
	 * @param int pageSize
	 * @param int totalCnt
	 * @return Map<String, Object> pageMap
	 * @throws ArithmeticException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 * @throws IndexOutOfBoundsException
	 */
	
	public Map<String, Object> pagination(int selectPageNo, int pageSize, int totalCnt) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		
		// 한 페이지에 표출될 페이지 번호의 개수
		int pageNoCntPerPage = 5;
		
		// 변수 초기화
		int totalPageNo = 0;
		int firstPageNo = 0;
		int lastPageNo = 0;
		int startRowIndex = 0;
		int pageGroup = 0;
		int prevGroupPage = 0;
		int nextGroupPage = 0;
		double lastGroupPage = 0;
		boolean hasPrevPageNav = false;
		boolean hasFirstPageNav = false;
		boolean hasNextPageNav = false;
		boolean hasLastPageNav = false;
		
		try {
			// 선택 페이지 번호가 0 이하일 때, 1로 보정
			if (selectPageNo <= 0) {selectPageNo = 1;}
			
			// 표출될 컨텐츠의 개수가 0개 이하일 때, 10개로 보정 (default = 10개)
			if (pageSize <= 0) {pageSize = 10;}
			
			// 게시물 개수가 0개일 때
			if (totalCnt == 0) {
				pageMap.put("selectPageNo", 1); 
				pageMap.put("pageSize", pageSize);
				pageMap.put("totalCnt", 0);
				pageMap.put("pageNoCntPerPage", pageNoCntPerPage);
				pageMap.put("startRowIndex", 0);
				pageMap.put("firstPageNo", 1);
				pageMap.put("lastPageNo", 1);
				pageMap.put("hasPrevPageNav", false);
				pageMap.put("hasFirstPageNav", false);
				pageMap.put("hasNextPageNav", false);
				pageMap.put("hasLastPageNav", false);
				
				return pageMap;
			}
			
			// 전체 페이지 번호
			totalPageNo = ((totalCnt - 1) / pageSize) + 1;
			
			// 현재 페이지 번호가 전체 페이지 번호보다 클 때, 보정값
			if (selectPageNo > totalPageNo) {selectPageNo = totalPageNo;}
			
			// 페이지 그룹에서의 첫번째 페이지 번호
			firstPageNo = ((selectPageNo - 1) / pageNoCntPerPage) * pageNoCntPerPage + 1;
			// 페이지 그룹에서의 마지막 페이지 번호
		    lastPageNo = Math.min(firstPageNo + pageNoCntPerPage - 1, totalPageNo);
			
		    // 마지막 페이지 번호가 전체 페이지 번호보다 클 떄, 보정값
			if (lastPageNo > totalPageNo) {lastPageNo = totalPageNo;}
			
			// 페이지 그룹
			pageGroup = ((selectPageNo - 1) / pageNoCntPerPage) + 1;
			// 이전 페이지 그룹
			prevGroupPage = (pageGroup - 1) * pageNoCntPerPage;
			// 다음 페이지 그룹
			nextGroupPage = pageGroup * pageNoCntPerPage + 1;
			// 마지막 페이지 그룹
			lastGroupPage = Math.floor(((totalPageNo - 1) / pageNoCntPerPage) + 1);
			
			// 정순, 역순 일련번호
			// startRowIndex(asc) = (selectPageNo - 1) * pageSize;
			startRowIndex = totalCnt - ((selectPageNo - 1) * pageSize);
			
			// < 표출 여부
			hasPrevPageNav = firstPageNo > 1;
			// << 표출 여부
			hasFirstPageNav = selectPageNo > 1;
			// > 표출 여부
			hasNextPageNav = lastPageNo < totalPageNo;
			// >> 표출 여부
			hasLastPageNav = selectPageNo < totalPageNo;
			
			pageMap.put("selectPageNo", selectPageNo); 
			pageMap.put("pageSize", pageSize);
			pageMap.put("totalCnt", totalCnt);
			pageMap.put("totalPageNo", totalPageNo);
			pageMap.put("pageNoCntPerPage", pageNoCntPerPage);
			pageMap.put("startRowIndex", startRowIndex);
			pageMap.put("firstPageNo", firstPageNo);
			pageMap.put("lastPageNo", lastPageNo);
			pageMap.put("pageGroup", pageGroup);
			pageMap.put("prevGroupPage", prevGroupPage);
			pageMap.put("nextGroupPage", nextGroupPage);
			pageMap.put("lastGroupPage", lastGroupPage);
			pageMap.put("hasPrevPageNav", hasPrevPageNav);
			pageMap.put("hasFirstPageNav", hasFirstPageNav);
			pageMap.put("hasNextPageNav", hasNextPageNav);
			pageMap.put("hasLastPageNav", hasLastPageNav);
			
		} catch (ArithmeticException e) {
			log.error(e.toString());
		} catch (NullPointerException e) {
			log.error(e.toString());
		} catch (IllegalArgumentException e) {
			log.error(e.toString());
		} catch (IndexOutOfBoundsException e) {
			log.error(e.toString());
		}
		
		return pageMap;
	}
}
