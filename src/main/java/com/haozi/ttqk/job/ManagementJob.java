package com.haozi.ttqk.job;

import com.haozi.ttqk.model.OperationUser;
import com.haozi.ttqk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public class ManagementJob extends QuartzJobBean {
    @Resource
    private UserService userService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        log.info("定时任务执行了");
//        List<OperationUser> users= userService.getAllUser();
//        System.out.println("定时任务执行了");

    }
}
