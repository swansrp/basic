/**
 * Title: QuartzJobService
 * Description: Copyright: Copyright (c) 2019 Company: Sharp
 *
 * @author Sharp
 * @date 2019-5-16 14:17
 * @description Project Name: Tanya
 * Package: com.srct.service.service
 */
package com.srct.service.service;

public interface QuartzJobService {


    /**
     * addJob
     */
    void startJob();

    /**
     * addJob
     *
     * @param jobClassName   job class name
     * @param jobGroupName   job group name
     * @param cronExpression job cronExpression
     */
    void addJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * pauseJob
     *
     * @param jobClassName   job class name
     * @param jobGroupName   job group name
     * @param cronExpression job cronExpression
     */
    void pauseJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * resumeJob
     *
     * @param jobClassName   job class name
     * @param jobGroupName   job group name
     * @param cronExpression job cronExpression
     */
    void resumeJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * rescheduleJob
     *
     * @param jobClassName   job class name
     * @param jobGroupName   job group name
     * @param cronExpression job cronExpression
     */
    void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * deleteJob
     *
     * @param jobClassName   job class name
     * @param jobGroupName   job group name
     * @param cronExpression job cronExpression
     */
    void deleteJob(String jobClassName, String jobGroupName, String cronExpression);
}
