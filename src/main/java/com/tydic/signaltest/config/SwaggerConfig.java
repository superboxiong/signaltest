package com.tydic.signaltest.config;

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
 * @Author superxiong
 * @Date 2020/2/11 11:34
 * @Version 1.0
 * 测试环境swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enableSwagger;
  @Bean
  public Docket api(){
     return new Docket(DocumentationType.SWAGGER_2)
             .apiInfo(apiInfo())
             .enable(enableSwagger)
             .select()
             .apis(RequestHandlerSelectors.basePackage("com.tydic.signaltest.controller")) //API接口所在位置
             .paths(PathSelectors.any())
             .build();
  }

    private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              .title("信号测试app测试文档")//文档名称
              .version("v1.0.0")//版本
              .build();
    }
}
