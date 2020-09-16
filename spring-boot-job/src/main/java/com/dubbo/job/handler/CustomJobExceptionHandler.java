package com.dubbo.job.handler;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zero
 * @date 2020/9/4-19:42
 */
@Slf4j
public class CustomJobExceptionHandler implements JobExceptionHandler {

    @Override
    public void handleException(String s, Throwable throwable) {
        log.info(String.format("Job '%s' exception occur in job processing", s), throwable);
        // 这里自定义异常处理逻辑
    }
}
