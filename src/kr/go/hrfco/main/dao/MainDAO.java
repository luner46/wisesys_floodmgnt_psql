package kr.go.hrfco.main.dao;

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
import kr.go.hrfco.main.vo.LinkDataVO;

/**
 * <pre>
 * @ClassName   : MainDAO
 * @Description : 메인 데이터 표출 DAO
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
public class MainDAO {

    @Autowired
    @Qualifier("sqlSessionPostgre")
    private SqlSessionTemplate sqlSession;

	private final Logger log = LoggerFactory.getLogger(MainDAO.class);

	/**
	 * 과거 특정 시점 환경부 수위 관측소 데이터 리턴 DAO
	 *
	 * @author 최준규
	 * @since 2025.11.27
	 * @return List<MeWlDataVO> dataList
	*/
	
    public List<MeWlDataVO> selectPastMeWl10min(HashMap<String, String> param){
    	List<MeWlDataVO> dataList = new ArrayList<>();
    	try {
    		dataList = sqlSession.selectList("MainMapper.selectPastMeWl10min", param);
    	} catch (MyBatisSystemException me) {
			log.error(me.toString());
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (PersistenceException pe) {
			log.error(pe.toString());
		}
    	return dataList;
    }
    
    public List<LinkDataVO> selectAy01LinkData(){
    	List<LinkDataVO> dataList = new ArrayList<>();
    	try {
    		dataList = sqlSession.selectList("MainMapper.selectAy01LinkData");
    	} catch (MyBatisSystemException me) {
			log.error(me.toString());
		} catch (IllegalArgumentException ie) {
			log.error(ie.toString());
		} catch (PersistenceException pe) {
			log.error(pe.toString());
		}
    	return dataList;
    }

    public List<LinkDataVO> selectAy01NodeData(){
    	List<LinkDataVO> dataList = new ArrayList<>();
    	try {
    		dataList = sqlSession.selectList("MainMapper.selectAy01NodeData");
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

