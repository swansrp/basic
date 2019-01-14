/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: ${projectName}
 * @Package: ${dbConfigPackage} 
 * @author: ${author}   
 * @date: ${date}
 */
package ${dbConfigPackage};

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataBaseConfig {
	
<#list dbConfigMap?keys as db>	
	@Bean
	@ConfigurationProperties(prefix = "db.config.${dbConfigMapPackageName[db]}")
	public DataSource ${dbConfigMap[db]}ConfigDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

</#list>

}
