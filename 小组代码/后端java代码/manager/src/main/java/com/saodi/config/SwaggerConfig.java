package com.saodi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//配置标签，代表该类是一个配置类
@Configuration
//开启Swagger
@EnableSwagger2
public class SwaggerConfig {
    //创建API接口文档
    @Bean
    public Docket createRestApi() {



        //创建一个Docket，可以理解问Docket代表一个文档的构建器
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//指定api相关的信息
                .select()
                //【重要】对外暴露服务的包,以controller的方式暴露,所以就是controller的包.
                .apis(RequestHandlerSelectors.basePackage("com.saodi"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(Collections.singletonList(new RequestParameterBuilder()
                        .name("token")
                        .description(" token 信息 ")
                        .in(ParameterType.HEADER)
                        .required(false)
                        .build()));

    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电影院")
                .description("管理员接口文档说明")
                .contact(new Contact("stuManager", "", "stuManager@neusoft.cn"))
                .version("1.0")
                .build();
    }
}