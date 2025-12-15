package com.kk.em.config;
//作用：配置跨域请求
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    //配置跨域请求
    //允许跨域的域名，可以用*表示允许任何域名使用


    @Bean
    //该注解用于将自定义的CorsConfig类注册为Spring Bean
    public WebMvcConfigurer corsConfigurer()
    //WebMvcConfigurer接口是Spring提供的用于配置Spring MVC的接口，我们可以实现该接口的addCorsMappings方法来配置跨域请求
    {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        allowedOriginPatterns("*"). //允许跨域的域名，可以用*表示允许任何域名使用
                        allowedMethods("*"). //允许任何方法（post、get等）
                        allowedHeaders("*"). //允许任何请求头
                        allowCredentials(true). //带上cookie信息
                        exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
            }
        };
    }
}
