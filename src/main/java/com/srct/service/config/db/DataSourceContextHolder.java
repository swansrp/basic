package com.srct.service.config.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {

    private DataSourceContextHolder() {

    }

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // Set DataSource Name
    public static void setDB(String dbType) {
        log.info("Swith to DataBase: {}", dbType);
        contextHolder.set(dbType);
    }

    // Get DataSource Name
    public static String getDB() {
        return (contextHolder.get());
    }

    // Clear DataSource
    public static void clearDB() {
        contextHolder.remove();
    }
}
