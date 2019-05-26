package com.srct.service.config.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private DataSourceContextHolder() {
    }

    // Get DataSource Name
    public static String getDB() {
        return (contextHolder.get());
    }

    // Set DataSource Name
    public static void setDB(String dbType) {
        log.debug("Swith to DataBase: {}", dbType);
        contextHolder.set(dbType);
    }

    // Clear DataSource
    public static void clearDB() {
        contextHolder.remove();
    }
}
