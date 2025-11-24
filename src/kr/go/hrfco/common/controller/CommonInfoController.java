package kr.go.hrfco.common.controller;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.lang.management.ManagementFactory;
import java.lang.management.GarbageCollectorMXBean;

@Controller
public class CommonInfoController {
	
	private static final Logger log = LoggerFactory.getLogger(CommonInfoController.class);

	@Value("#{globals['workspace.name']}") private String name;
    @Value("#{globals['workspace.version']}") private String version;
    @Value("#{globals['workspace.build']}") private String build;
    @Value("#{globals['workspace.work']}") private String work;

    @Autowired
    private DataSource dataSource;

    
    /**
     * @author 강태훈
     * @since 2025.07.29
     * @version 1.0
     * @desc 프로젝트 정보(버전, os, jdk 등) 문자열 리턴
     */
    @RequestMapping("/info")
    public String infoPage(Model model) {
    	Map<String, String> info = new LinkedHashMap<>();
        info.put("name", name);
        info.put("version", version);
        info.put("build", build);
        info.put("work", work);
        info.put("jdk", System.getProperty("java.version"));
        info.put("osinfo", System.getProperty("os.name") + " " + System.getProperty("os.version"));
        
        log.info("test name: " + name);
        
        model.addAttribute("info", info);
        return "common/common_workspace_info";
    }
    
    
    /**
     * @author 강태훈
     * @since 2025.07.29
     * @version 1.0
     * @desc api 활용, 서버 헬스 체크 및 정보 json map 리턴, 향후 암호화 필요
     */
    @GetMapping("/wisemonitor")
    @ResponseBody
    public Map<String, Object> monitor() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("status", "up");
        status.put("timestamp", LocalDateTime.now().toString());

        try(Connection conn = dataSource.getConnection()) {
            status.put("db", conn.isValid(2) ? "UP" : "down");
            
        } catch (Exception e) {
            status.put("db", "down");
        }

        status.put("version", version);
        status.put("buildDate", build);

        Runtime rt = Runtime.getRuntime();
        status.put("freeMemoryMB", rt.freeMemory() / 1024 / 1024);
        status.put("totalMemoryMB", rt.totalMemory() / 1024 / 1024);

        long gcCount = 0;
        long gcTime = 0;
        
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            if(gc.getCollectionCount() != -1) gcCount += gc.getCollectionCount();
            if(gc.getCollectionTime() != -1) gcTime += gc.getCollectionTime();
        }
        
        status.put("gcCount", gcCount);
        status.put("gcTimeMs", gcTime);

        return status;
    }
}
