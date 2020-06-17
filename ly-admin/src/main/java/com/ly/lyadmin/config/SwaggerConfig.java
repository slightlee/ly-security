package com.ly.lyadmin.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @Author: SLIGHTLEE
 * @Date: 2019/10/15 10:53 下午
 * @Description: TODO
 */
@Configuration
@EnableSwagger2
@Profile({"local", "dev"})
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //包下的类，生成接口文档
                //.apis(RequestHandlerSelectors.basePackage("com.ly.lyadmin.modules.sys.controller"))
                .paths(PathSelectors.any())
                .build();
              //  .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ly")
                .description("接口文档")
//                .termsOfServiceUrl("http://www.baidu.com")
                .version("1.0.0")
                .build();
    }

    // 配置全局token
//    private List<ApiKey> security() {
//        return newArrayList(
//                new ApiKey("token", "token", "header")
//        );
//    }

}