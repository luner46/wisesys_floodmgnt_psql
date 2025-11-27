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

import kr.go.hrfco.api.me.vo.MeWlDataVO;

/**
 * <pre>
 * @ClassName   : MeWlDataDAO
 * @Description : 환경부 수위 관측소 10분 데이터 DAO
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
public class MeWlDataDAO {
	@Autowired
    @Qualifier("sqlSessionPostgre")
	private SqlSessionTemplate sqlSession;
	
	private final Logger log = LoggerFactory.getLogger(MeWlDataDAO.class);

	/**
	 * 환경부 수위 관측소 10분 자료 실시간 데이터 표출 DAO
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<MeWlDataVO> dataList
	*/
	
	public List<MeWlDataVO> selectRealTimeMeWlData(HashMap<String, String> param){
		List<MeWlDataVO> dataList = new ArrayList<>();
		
		try {
			dataList = sqlSession.selectList("MeWlDataMapper.selectRealTimeMeWlData", param);
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