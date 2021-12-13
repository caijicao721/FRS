package com.cao.frs.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Configuration
@EnableSwagger2 //开启swagger
public class MySwaggerConfig{

    @Bean //创建swagger Bean实例
    public Docket docker(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("财务系统") //组名
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cao.frs.controller"))//扫描控制器路径
                .paths(PathSelectors.any()) //过滤
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("曹佳铭", "https://www.cnblogs.com/benbicao/", "17621778372@163.com");
        return new ApiInfo("财务管理系统",
                "后台接口",
                "1.0",
                "https://www.cnblogs.com/benbicao/",
                contact, "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}
