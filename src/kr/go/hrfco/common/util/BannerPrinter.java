package kr.go.hrfco.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class BannerPrinter implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger log = LoggerFactory.getLogger(BannerPrinter.class);
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("\n"
        	    + " __        __   _                            \n"
        	    + " \\ \\      / /__| | ___ ___  _ __ ___   ___   \n"
        	    + "  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  \n"
        	    + "   \\ V  V /  __/ | (_| (_) | | | | | |  __/  \n"
        	    + "    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \n"
        	    + "                                             \n"
        	    + "              :: dev@wisesys ::              \n");
    }
}