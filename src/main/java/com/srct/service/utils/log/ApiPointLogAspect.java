package com.srct.service.utils.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.srct.service.utils.HttpUtil;
import com.srct.service.utils.JSONUtil;

@Aspect
@Component
public class ApiPointLogAspect {

    private static final Logger mLogger = LoggerFactory.getLogger(ApiPointLogAspect.class);

    private static final String LogFormat_SND = "[SND]|%s|%s|%s|%s|%s";

    private static final String LogFormat_RCV = "[RCV]|%s|%s|%s|%s|%s";

    private static final String LogFormat_EX = "[EXP]|%s|%s|%s|%s|%s";

    private static final String LogFormat_TIME = "[TIME]|%s|%s|%s|%s|%s";

    public static ThreadLocal<ThreadLogInfo> threadLocal = new ThreadLocal<ThreadLogInfo>();

    private String url = "-";

    private String ipAddress = "0.0.0.0";

    private String partnerId = "-";

    private String methodName = "-";

    private String paramNames = "-";

    private Long startTime = 0L;

    private Long endTime = 0L;

    @Pointcut("execution(public * com.srct.service..*.*Controller.*(..))")
    public void apiLog() {
        // Just a pointCut function
    }

    @Pointcut("@annotation(com.srct.service.utils.log.MySlf4j)" + "||" + "@within(com.srct.service.utils.log.MySlf4j)")
    public void myslf4j() {
    }

    @Before("apiLog()")
    public void before(JoinPoint point) {
        // TODO:
    }

    @Around("apiLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object res = null;
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest req = attr.getRequest();
        url = req.getMethod() + " " + req.getRequestURI();
        ipAddress = req.getHeader("x-forwarded-for");
        partnerId = HttpUtil.getHeader(req, "partnerid");
        methodName = pjp.getSignature().getName();
        paramNames = getParams(pjp);
        startTime = System.currentTimeMillis();
        threadLocal.set(new ThreadLogInfo(methodName, paramNames, startTime, null));
        mLogger.trace(String.format(LogFormat_SND, ipAddress, url, partnerId, methodName, paramNames));
        try {
            res = pjp.proceed();
            endTime = System.currentTimeMillis();
            threadLocal.get().setEndTime(endTime);
            mLogger.trace(
                    String.format(LogFormat_RCV, ipAddress, url, partnerId, methodName, JSONUtil.toJSONString(res)));
        } catch (Throwable e) {
            endTime = System.currentTimeMillis();
            ;
            threadLocal.get().setEndTime(endTime);
            throw e;
        }
        return res;
    }

    @After("apiLog()")
    public void after() {
        mLogger.trace(String.format(LogFormat_TIME, endTime - startTime, ipAddress, url, partnerId, methodName));
    }

    @Around("myslf4j()")
    public Object aroundMyslf4j(ProceedingJoinPoint pjp) throws Throwable {
        Object res = null;
        if (threadLocal.get() == null) {
            threadLocal.set(
                    new ThreadLogInfo(pjp.getSignature().getName(), getParams(pjp), System.currentTimeMillis(), null));
        } else {
            threadLocal.get().setStartTime(System.currentTimeMillis());
        }
        try {
            res = pjp.proceed();
            endTime = System.currentTimeMillis();
            threadLocal.get().setEndTime(endTime);
            mLogger.trace(
                    String.format(LogFormat_RCV, ipAddress, url, partnerId, methodName, JSONUtil.toJSONString(res)));
        } catch (Throwable e) {
            endTime = System.currentTimeMillis();
            threadLocal.get().setEndTime(endTime);
            throw e;
        }
        return res;
    }

    @After("myslf4j()")
    public void afterMyslf4j() {
        ThreadLogInfo info = threadLocal.get();
        mLogger.info(String.format(LogFormat_TIME, info.getEndTime() - info.getStartTime(), "-", "-", "-",
                info.getMethodName()));
        threadLocal.remove();
    }

    @AfterThrowing(pointcut = "apiLog()", throwing = "e")
    public void afterThrow(JoinPoint point, Throwable e) {
        String methodName = point.getSignature().getName();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String msg = sw.toString();
        endTime = System.currentTimeMillis();
        mLogger.trace(String.format(LogFormat_EX, ipAddress, url, partnerId, methodName, msg));
    }

    @AfterReturning("apiLog()")
    public void afterReturning() {
    }

    private String getParams(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] names = u.getParameterNames(method);
        Class<?>[] types = method.getParameterTypes();
        Object[] args = point.getArgs();
        String res = "";
        if (args != null && args.length > 0) {
            String value = null;
            try {
                value = JSONUtil.toJSONString(args[0]);
            } catch (Exception e) {
                value = args[0].toString();
            } finally {
                res = types[0].getSimpleName() + " " + names[0] + "<" + value + ">";
            }
            for (int i = 1; i < names.length; i++) {
                value = null;
                try {
                    value = JSONUtil.toJSONString(args[i]);
                } catch (Exception e) {
                    value = args[i].toString();
                } finally {
                    res += ", " + types[i].getSimpleName() + " " + names[i] + "<" + value + ">";
                }
            }
        }
        return res;
    }
}
