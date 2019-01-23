package com.srct.service.utils.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.srct.service.utils.JSONUtil;

@Aspect
@Component
public class LogAspect {

    private static final Logger mLogger = LoggerFactory.getLogger(LogAspect.class);

    private static final String LogFormat = "[%s]|%s|%s";

    private static ThreadLocal<MySlf4jLogInfo> mySlfLogLocal = new ThreadLocal<MySlf4jLogInfo>();

    @Pointcut("@annotation(com.srct.service.utils.log.MySlf4j)" + "||" + "@within(com.srct.service.utils.log.MySlf4j)")
    public void myslf4j() {}

    @Around("myslf4j()")
    public Object aroundMyslf4j(ProceedingJoinPoint pjp) throws Throwable {
        Object res = null;
        String methodName = pjp.getSignature().getName();
        String paramNames = getParams(pjp);
        mySlfLogLocal.set(new MySlf4jLogInfo(methodName, paramNames, System.currentTimeMillis(), null));
        mLogger.trace(String.format(LogFormat, "START", methodName, paramNames));
        try {
            res = pjp.proceed();
            Long endTime = System.currentTimeMillis();
            mySlfLogLocal.get().setEndTime(endTime);
            mLogger.trace(String.format(LogFormat, "END", methodName, JSONUtil.toJSONString(res)));
        } catch (Throwable e) {
            Long endTime = System.currentTimeMillis();
            mySlfLogLocal.get().setEndTime(endTime);
            throw e;
        }
        return res;
    }

    @After("myslf4j()")
    public void afterMyslf4j() {
        MySlf4jLogInfo info = mySlfLogLocal.get();
        mLogger.trace(String.format(LogFormat, "TIME", info.getEndTime() - info.getStartTime(), info.getMethodName()));
    }

    @AfterThrowing(pointcut = "myslf4j()", throwing = "e")
    public void afterThrowMyslf4j(JoinPoint point, Throwable e) {
        String methodName = point.getSignature().getName();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String msg = sw.toString();
        MySlf4jLogInfo info = mySlfLogLocal.get();
        mLogger.trace(String.format(LogFormat, "EXP", methodName, msg));
    }

    private String getParams(JoinPoint point) {
        Method method = ((MethodSignature)point.getSignature()).getMethod();
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
