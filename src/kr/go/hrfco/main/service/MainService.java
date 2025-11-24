package kr.go.hrfco.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.go.hrfco.main.dao.MainDAO;

@Service
public class MainService {

    @Autowired
    private MainDAO mainDAO;

    
    public String getHelloMessage() {
        String result = mainDAO.selectHello();
        return result;
    }
    
    public String checkConnection() {
        return mainDAO.isConnected() ? "db connection success" : "db connection failed";
    }
}
