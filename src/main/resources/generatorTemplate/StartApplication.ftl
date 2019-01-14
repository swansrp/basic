/**   
 * Copyright ?2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Project Name: ${projectName}
 * @Package: ${packageName} 
 * @author: ${author}   
 * @date: ${date}
 */
package ${packageName};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/** 
 * @ClassName: ${moduleName}StartApplication 
 * @Description: Start Application for ${projectName} - ${moduleName} 
 */

@ComponentScan(basePackages = "com.srct.service")
@SpringBootApplication
@MapperScan("com.srct.service.**.mapper")
@ServletComponentScan
// @EnableTransactionManagement
public class ${moduleName}StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(${moduleName}StartApplication.class, args);
    }
}