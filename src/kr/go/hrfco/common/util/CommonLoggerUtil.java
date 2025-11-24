package kr.go.hrfco.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * @ClassName   : CommonLoggerUtil
 * @Description : 로그 입력 
 * @Modification 
 *
 * -----------------------------------------------------
 * 2025.09.30, 최준규, 최초 생성
 *
 * </pre>
 * @author 최준규
 * @since 2025.09.30
 * @version 1.0
 * @see reference
 *
 * @Copyright (c) 2025 by wiseplus All right reserved.
 */

public enum CommonLoggerUtil {
    INSTANCE;

	private static final Logger log = LoggerFactory.getLogger(CommonLoggerUtil.class);
	
	/**
	 * 사용자 로그 입력 비동기 실행
	 *
	 * @author 최준규
	 * @since 2025.09.30
	 * @param 2 Default Thread 갯수
	 * @param 5 Maximum Thread 갯수
	 * @param 30L 대기 시간
	 * @param TimeUnit.SECONDS 대기 시간 단위 (초)
	 * @param new LinkedBlockingQueue<>(100) 최대 대기 큐 갯수
	 * @param new ThreadPoolExecutor.DiscardPolicy() 대기 시간이 초과할 경우, 해당 대기 큐 버림
	 * @param new ThreadPoolExecutor.CallerRunsPolicy() 대기 시간 단위 및 최대 대기 큐 갯수를 초과할 경우 (모든 대기 큐를 전달자가 작업)
	*/
	
    private static final ExecutorService executor = new ThreadPoolExecutor(
		2, 5, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.DiscardPolicy()
    );

	/**
	 * CommonLoggerUtil 인스턴스화
	 *
	 * @author 최준규
	 * @since 2025.09.30
	*/
	
    public static CommonLoggerUtil getInstance() {
        return INSTANCE;
    }

	/**
	 * Logging 강제 종료
	 *
	 * @author 최준규
	 * @since 2025.09.30
	*/
	
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        	log.error("Shutting down logger...");
            shutdownLogger();
        }));
    }

	/**
	 * 실행 가능한 task(insertUserLog) 실행
	 *
	 * @author 최준규
	 * @since 2025.09.30
	 * @param Runnable task
	*/
	
    public void submit(Runnable task) {
    	if (executor.isShutdown()) {
    		log.error("Warning: Logger is shut down, skipping log.");
            return;
        }
        try {
        	 executor.submit(task);
        } catch (RejectedExecutionException e) {
        	log.error("Logger task rejected: " + e.getMessage());
        } catch (Exception e) {
        	log.error("Error submitting log task: " + e.getMessage());
            e.printStackTrace();
        }
    }

	/**
	 * Logging 강제 종료
	 *
	 * @author 최준규
	 * @since 2025.09.30
	*/
	
    public static void shutdownLogger() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                log.error("Forcing executor shutdown...");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
        	log.error("Executor shutdown interrupted.");
            executor.shutdownNow();
        }
        log.error("Logger shutdown complete.");
    }
}