package com.srct.service.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.srct.service.utils.JSONUtil;

public class Log {

    private Log() {
    }

    private static Logger mLogger = null;

    private static String getCallerClassName() {
        return new SecurityManager() {

            public String getClassName() {
                return getClassContext()[4].getSimpleName();
            }
        }.getClassName();
    }

    private static Class<?> getCallerClass() {
        return new SecurityManager() {

            public Class<?> getClassName() {
                try {
                    return Class.forName(getClassContext()[4].getName());
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }
            }
        }.getClassName();
    }

    private static int getCallerLine() {
        return new Throwable().getStackTrace()[3].getLineNumber();
    }

    private static String loc() {
        return "(" + getCallerClassName() + ".java:" + getCallerLine() + ") " + "%s";
    }

    public static void i(String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isInfoEnabled()) {
            mLogger.info(String.format(loc(), msg), strings);
        }
    }

    public static void d(String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isDebugEnabled()) {
            mLogger.debug(String.format(loc(), msg), strings);
        }
    }

    public static void e(String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isErrorEnabled()) {
            mLogger.error(String.format(loc(), msg), strings);
        }
    }

    public static void w(String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isWarnEnabled()) {
            mLogger.warn(String.format(loc(), msg), strings);
        }
    }

    public static void v(String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isTraceEnabled()) {
            mLogger.trace(String.format(loc(), msg), strings);
        }
    }

    public static void ii(Object o) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isInfoEnabled()) {
            mLogger.info(String.format(loc(), JSONUtil.toJSONString(o)));
        }
    }

    public static void ee(Object o) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isErrorEnabled()) {
            mLogger.error(String.format(loc(), JSONUtil.toJSONString(o)));
        }
    }

    public static void ww(Object o) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isWarnEnabled()) {
            mLogger.warn(String.format(loc(), JSONUtil.toJSONString(o)));
        }
    }

    public static void dd(Object o) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isDebugEnabled()) {
            mLogger.debug(String.format(loc(), JSONUtil.toJSONString(o)));
        }
    }

    public static void vv(Object o) {
        mLogger = LoggerFactory.getLogger(getCallerClass());
        if (mLogger.isTraceEnabled()) {
            mLogger.trace(String.format(loc(), JSONUtil.toJSONString(o)));
        }
    }

    public static void i(Class<?> clazz, String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(clazz);
        mLogger.info(msg, strings);
    }

    public static void e(Class<?> clazz, String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(clazz);
        mLogger.error(msg, strings);
    }

    public static void w(Class<?> clazz, String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(clazz);
        mLogger.warn(msg, strings);
    }

    public static void d(Class<?> clazz, String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(clazz);
        mLogger.debug(msg, strings);
    }

    public static void v(Class<?> clazz, String msg, Object... strings) {
        mLogger = LoggerFactory.getLogger(clazz);
        mLogger.trace(msg, strings);
    }

    public static void ii(Class<?> clazz, Object o) {
        i(clazz, JSONUtil.toJSONString(o));
    }

    public static void ee(Class<?> clazz, Object o) {
        e(clazz, JSONUtil.toJSONString(o));
    }

    public static void ww(Class<?> clazz, Object o) {
        w(clazz, JSONUtil.toJSONString(o));
    }

    public static void dd(Class<?> clazz, Object o) {
        d(clazz, JSONUtil.toJSONString(o));
    }

    public static void vv(Class<?> clazz, Object o) {
        v(clazz, JSONUtil.toJSONString(o));
    }
}
