package org.wzp.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: zp.wei
 * @DATE: 2020/12/7 11:43
 */
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {


    @Bean
    public Docket commonDocket() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .groupName("通用接口")
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/common/**"))
                .build();
    }

    @Bean
    public Docket backApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .groupName("后台接口")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/back/**"))
                .build();
    }


    /**
     * API 页面上半部分展示信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("文档描述")
                .contact(new Contact("南风落尽", null, "wzp9215@qq.com"))
                .version("1.0")
                .build();
    }
}
