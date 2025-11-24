package kr.go.hrfco.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.go.hrfco.main.service.MainService;
import org.springframework.ui.Model;

@Controller
public class MainController {
	
	@Autowired
    private MainService mainService;
	

    @RequestMapping("/hello")
    public String hello(Model model) {
    	String message = mainService.getHelloMessage();
        model.addAttribute("message", message);
        return "main/test_hello";
    }
    
    @RequestMapping("/dbtest")
    public String testDb(Model model) {
        String result = mainService.checkConnection();
        model.addAttribute("result", result);
        return "dbtest";
    }
    
}

