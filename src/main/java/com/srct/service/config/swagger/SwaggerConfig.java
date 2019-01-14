/**   
 * Copyright © 2018 SRC-TJ Service TG. All rights reserved.
 * 
 * @Package: com.srct.service.config.swagger 
 * @author: xxfore   
 * @date: 2018-04-30 15:07
 */
package com.srct.service.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** 
 * @ClassName: SwaggerConfig 
 * @Description: TODO 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${my.swagger.config.rootpackagename}")
	private String rootPackageName;
	
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //Chooes controller path
                .apis(RequestHandlerSelectors.basePackage(rootPackageName))
                .paths(PathSelectors.any())
                .build();
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //Customer Information
                .title("Samsung China Rewards")
                .description("Just for development")
                .termsOfServiceUrl("http://poas.org.cn")
                .contact("SRC-TJ Service TG")
                .version("1.0")
                .build();
    }
}
