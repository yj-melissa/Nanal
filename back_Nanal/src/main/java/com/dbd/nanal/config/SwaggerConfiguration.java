package com.dbd.nanal.config;


import com.dbd.nanal.model.UserEntity;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket SwaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo()) // Api Docu 및 작성자 정보 매핑
                .securityContexts(Arrays.asList(securityContext()))  // swagger JWT 테스트 목적
                .securitySchemes(Arrays.asList(apiKey()))   // swagger JWT 테스트 목적
                .ignoredParameterTypes(UserEntity.class)

                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dbd.nanal.controller"))
                .paths(PathSelectors.any()) // controller package 전부
                .build()
                .useDefaultResponseMessages(false); // 기본 세팅값이 200, 401, 402, 403, 404를 사용하지 않는다.
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("나날 서버 API 설명을 위한 문서입니다.")
                .license("D110 DBD")
                .licenseUrl("nanal-d110")
                .version("0.1")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }



}
