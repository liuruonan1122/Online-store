package com.kk.em.config;
//作用：配置拦截器，添加jwt拦截器和权限校验拦截器

import com.kk.em.interceptor.AuthorityInterceptor;
import com.kk.em.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
// @EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
    //注入jwt拦截器和权限校验拦截器
    @Resource
    JwtInterceptor jwtInterceptor;
    @Resource
    AuthorityInterceptor authorityInterceptor;
    @Override
    //配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        //jwt拦截器
        registry.addInterceptor(jwtInterceptor)
                //拦截所有请求
                .addPathPatterns("/**")
                //排除登录注册等不需要验证的请求
                .excludePathPatterns("/login","/register","/file/**","/avatar/**","/api/good/**","/api/icon/**","/api/category/**","/api/market/**","/api/carousel/**")
                //设置优先级
                .order(0)//jwt拦截器的优先级要小于权限校验拦截器的优先级

        ;
        //权限校验拦截器
        registry.addInterceptor(authorityInterceptor)
                //拦截所有请求
                .addPathPatterns("/**")
                //排除登录注册等不需要验证的请求
                .excludePathPatterns()
                //设置优先级
                .order(1)//权限校验拦截器的优先级要大于jwt拦截器的优先级
        ;

        WebMvcConfigurer.super.addInterceptors(registry);//调用父类的方法
    }


}
