package com.yuan.study.config;

import com.yuan.study.StudyApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder()
                .contact(new Contact("yuan", "https://github.com/someGenki", "1159140147@qq.com"))
                .description("后端api接口V1")
                .title("xxx的api接口")
                .version("0.1")
                .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(StudyApplication.class.getPackageName()))
                .paths(PathSelectors.any())
                .build();
    }
}