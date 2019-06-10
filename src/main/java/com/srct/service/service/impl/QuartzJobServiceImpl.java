/**
 * Title: QuartzJobServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 14:22
 * @description Project Name: Tanya
 * Package: com.srct.service.service.impl
 */
package com.srct.service.service.impl;

import com.srct.service.exception.ServiceException;
import com.srct.service.service.QuartzJobService;
import com.srct.service.utils.log.Log;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    public static QuartzJobBean getClass(String classname) {
        Class<?> class1;
        try {
            class1 = Class.forName(classname);
            return (QuartzJobBean) class1.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new ServiceException("创建定时任务失败");
        }
    }

    @Override
    public void startJob() {
        // 启动调度器
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            Log.e(e);
            throw new ServiceException("开始定时任务失败");
        }
    }

    @Override
    public void addJob(String jobClassName, String jobGroupName, String cronExpression) {

        startJob();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                .withIdentity(jobClassName + cronExpression, jobGroupName).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName + cronExpression, jobGroupName)
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            Log.e(e);
            throw new ServiceException("创建定时任务失败");
        }
    }

    @Override
    public void pauseJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName + cronExpression, jobGroupName));
        } catch (SchedulerException e) {
            Log.e(e);
            throw new ServiceException("暂停定时任务失败");
        }
    }

    @Override
    public void resumeJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobClassName + cronExpression, jobGroupName));
        } catch (SchedulerException e) {
            Log.e(e);
            throw new ServiceException("恢复定时任务失败");
        }
    }

    @Override
    public void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName + cronExpression, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            Log.e(e);
            throw new ServiceException("更新定时任务失败");
        }
    }

    @Override
    public void deleteJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName + cronExpression, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName + cronExpression, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName + cronExpression, jobGroupName));
        } catch (SchedulerException e) {
            Log.e(e);
            throw new ServiceException("删除定时任务失败");
        }
    }

}
