package kr.go.hrfco.main.dao;

import java.sql.Connection;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MainDAO {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private SqlSessionTemplate sqlSession;

    
    public String selectHello() {
        return sqlSession.selectOne("MainMapper.selectHello");
    }
    
    /**
     * @author 강태훈
     * @since 2025.07.29
     * @version 1.0
     * @desc db 커넥션 테스트
     */
    public boolean isConnected() {
        try(Connection conn = dataSource.getConnection()) {
            return conn != null && !conn.isClosed();
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

