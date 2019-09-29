package com.srct.service.config.redisson;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Title: RedissonLockAspect
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author 沙若鹏
 * @date 2019/9/29 21:50
 * @description Project Name: Grote
 * @Package: com.srct.service.config.redisson
 */
@Slf4j
@Aspect
@Component
public class RedissonLockAspect {

    private static final String REDIS_LOCK_KEY = "Redisson:";

    @Resource
    private RedissonClient redissonClient;

    @Pointcut("@annotation(com.bhfae.redis.aspect.RedisLock)")
    public void redisLock() {
    }

    @Around("redisLock() && @annotation(lockInfo)")
    public Object around(ProceedingJoinPoint pjp, RedisLock lockInfo) throws Throwable {
        String syncKey = getSyncKey(pjp, lockInfo.syncKey());
        RLock lock = redissonClient.getLock(syncKey);
        Object obj = null;
        try {
            log.info("尝试获取锁 {}", syncKey);
            // 得到锁，没有人加过相同的锁
            if (lock.tryLock(lockInfo.waitTime(), lockInfo.releaseTime(), TimeUnit.MILLISECONDS)) {
                log.info("成功获取锁 {}", syncKey);
                obj = pjp.proceed();
            } else {
                log.info("获取 {} 锁失败", syncKey);
            }
        } catch (Exception e) {

        } finally {
            if (lock.isHeldByCurrentThread() && lockInfo.unlockPromptly()) {
                log.info("释放 {} 锁", syncKey);
                lock.unlock();
            }
            return obj;
        }
    }


    @After("redisLock()")
    public void after() {

    }

    @AfterThrowing(pointcut = "redisLock()", throwing = "e")
    public void afterThrow(JoinPoint point, Throwable e) {

    }

    /**
     * 获取包括方法参数上的key
     *
     * @param pjp
     * @param synKey
     * @return
     */
    private String getSyncKey(ProceedingJoinPoint pjp, String synKey) {
        StringBuffer synKeyBuffer = new StringBuffer(REDIS_LOCK_KEY);
        synKeyBuffer.append(pjp.getSignature().getDeclaringTypeName()).append(".").append(pjp.getSignature().getName());
        if (!StringUtil.isEmpty(synKey)) {
            synKeyBuffer.append(".").append(synKey);
        }
        return synKeyBuffer.toString();
    }

}