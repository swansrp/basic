package com.srct.service.config.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataSourceContextHolder {

    private DataSourceContextHolder() {
    }

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> DB_HOLDER = new ThreadLocal<>();

    public static void setDB(String dbType) {
        log.info("Switch to DataBase: {}", dbType);
        DB_HOLDER.set(dbType);
    }

    public static String getDB() {
        return (DB_HOLDER.get());
    }

    public static void clearDB() {
        DB_HOLDER.remove();
    }
}
