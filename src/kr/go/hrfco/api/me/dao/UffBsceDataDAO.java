package kr.go.hrfco.api.me.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import kr.go.hrfco.api.me.vo.UffBsceDataVO;

/**
 * <pre>
 * @ClassName   : UffBsceDataDAO
 * @Description : Uff Bsce 실시간 데이터 DAO
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

@Repository
public class UffBsceDataDAO {
	@Autowired
	@Qualifier("sqlSessionPostgre")
	private SqlSessionTemplate sqlSession;
	
	private final Logger log = LoggerFactory.getLogger(UffBsceDataDAO.class);

	/**
	 * Uff Bsce 실시간 데이터 표출 DAO
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<UffBsceDataVO> dataList
	*/
	
	public List<UffBsceDataVO> selectRealTimeUffBsceData(HashMap<String, String> param){
		List<UffBsceDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = sqlSession.selectList("UffBsceDataMapper.selectRealTimeUffBsceData", param);
		} catch (MyBatisSystemException me) {
			log.error(me.toString());
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (PersistenceException pe) {
			log.error(pe.toString());
		}
		
		return dataList;
	}
}
