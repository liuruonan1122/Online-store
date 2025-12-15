package com.kk.em.config;
//配置Swagger2
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//启用Swagger2注解
@EnableSwagger2
//创建Swagger2配置类
public class SwaggerConfig {
    @Bean
    //创建Docket对象
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)       //设置文档类型为Swagger2
                .apiInfo(new ApiInfoBuilder()                     //构建API信息
                        .title("SpringBoot整合Swagger")              //设置API标题
                        .description("详细信息")          //设置API描述
                        .version("1.0")         //设置API版本
                        .license("The Apache License")//设置API许可证
                        .build()//构建API信息对象
                )
                .pathMapping("/")//设置请求路径映射
                .select()//选择扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.kk.em.controller"))//指定扫描的控制器包路径
                .paths(PathSelectors.any())//指定扫描的路径
                .build();//构建Docket对象
        //作用：配置Swagger2，通过ApiInfoBuilder构建API信息，通过select()方法指定扫描的包路径，通过build()方法构建Docket对象，并返回。
    }
}
