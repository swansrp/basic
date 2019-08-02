package com.srct.service.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Value("${my.db.config.url}")
    private String dbIP;

    @Value("${my.db.config.port}")
    private String dbPort;

    @Value("${my.db.config.property}")
    private String dbProp;

    @Value("${my.db.config.username}")
    private String username;

    @Value("${my.db.config.password}")
    private String password;

    @Value("${my.db.config.driver}")
    private String driverClassName;

    @Bean(name = "configMasterDS")
    @Primary // Master Database
    @ConfigurationProperties(prefix = "config.datasource")
    public DataSource configDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // Default Data Source
        dynamicDataSource.setDefaultTargetDataSource(configDataSource());
        // Confige Multi-DataSource
        Map<Object, Object> dsMap = new HashMap<>(0);
        dsMap.put(DataSourceEnumCommon.TESTDB, configDataSource());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * Sql session factory bean. Here to config datasource for SqlSessionFactory
     * <p>
     * You need to add @{@code @ConfigurationProperties(prefix = "mybatis")}, if
     * you are using *.xml file, the {@code 'mybatis.type-aliases-package'} and
     * {@code 'mybatis.mapper-locations'} should be set in
     * {@code 'application.properties'} file, or there will appear invalid bond
     * statement exception
     *
     * @return the sql session factory bean
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // Here is very important, if don't config this, will can't switch
        // datasource
        // put all datasource into SqlSessionFactoryBean, then will autoconfig
        // SqlSessionFactory
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        log.info("sqlSessionFactoryBean");
        return sqlSessionFactoryBean;
    }

    /**
     * Transaction manager platform transaction manager.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    public void addDynamicDataSource(Map<String, DataSource> dataSourceMap) {
        DynamicDataSource dds = (DynamicDataSource) dynamicDataSource();
        Map<Object, Object> dsMap = new HashMap<>();
        dsMap.put(DataSourceEnumCommon.TESTDB, configDataSource());
        for (Entry<String, DataSource> dbNameEntry : dataSourceMap.entrySet()) {
            String dbName = dbNameEntry.getKey();
            log.info("add datasource: {}", dbName);
            DataSource datasource = dataSourceMap.get(dbName);
            dsMap.put(dbName, datasource);
        }
        dds.setTargetDataSources(dsMap);
        dds.afterPropertiesSet();
    }

    public void addDynamicDataSource(String dbNameList) {
        DynamicDataSource dds = (DynamicDataSource) dynamicDataSource();
        String[] dbNameArray = dbNameList.split(",");
        Map<Object, Object> dsMap = new HashMap<>();
        for (String dbName : dbNameArray) {
            log.info("add datasource: {}", dbName);
            dsMap.put(DataSourceEnumCommon.TESTDB, configDataSource());
            DruidDataSource datasource = new DruidDataSource();
            // jdbc:mysql://${my.db.config.url}:${my.db.config.port}/Configuration?${my.db.config.property}
            String dbUrl = "jdbc:mysql://" + dbIP + ":" + dbPort + "/" + dbName + "?" + dbProp;
            datasource.setUrl(dbUrl);
            datasource.setUsername(username);
            datasource.setPassword(password);
            datasource.setDriverClassName(driverClassName);
            dsMap.put(dbName, datasource);
        }
        dds.setTargetDataSources(dsMap);
        dds.afterPropertiesSet();
    }
}
