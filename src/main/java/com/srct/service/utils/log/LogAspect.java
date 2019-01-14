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
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.srct.service.controller..*.*(..))" + " || "
            + "execution(public * com.srct.service.service..*.*(..))" + " || "
            + "execution(public * com.srct.service.temp..*.*(..))" + " || "
            // Annotate Class
            + "@within(com.srct.service.utils.log.MySlf4j)" + " || "
            // Annotate Method
            + "@annotation(com.srct.service.utils.log.MySlf4j)")
    public void recordLog() {
        // Just a pointCut function
    }

    @Before("recordLog()")
    public void before(JoinPoint point) {
        // TODO:
    }

    @Around("recordLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object res;
        String methodName = pjp.getSignature().getName();
        String paramNames = getParams(pjp);
        Log.d("Enter " + methodName + "(" + paramNames + ")");

        res = pjp.proceed();

        Log.d("Exit " + methodName + "(" + paramNames + ")");
        return res;
    }

    @After("recordLog()")
    public void after() {
        // TODO:
    }

    @AfterThrowing(pointcut = "recordLog()", throwing = "e")
    public void afterThrow(JoinPoint point, Throwable e) {
        String methodName = point.getSignature().getName();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String msg = sw.toString();
        Log.d("Exception is occured on " + methodName + "(" + getParams(point) + ") : " + msg);
    }

    private String getParams(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] names = u.getParameterNames(method);
        Class<?>[] types = method.getParameterTypes();
        Object[] args = point.getArgs();
        String res = "";
        if (args != null && args.length > 0) {
            res = types[0].getSimpleName() + " " + names[0] + "<" + args[0].toString() + ">";
            for (int i = 1; i < names.length; i++) {
                res += ", ";
                res += types[i].getSimpleName() + " " + names[i] + "<" + args[i].toString() + ">";
            }
        }
        return res;

    }
}
