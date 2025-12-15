package com.kk.em.config;
//配置RedisTemplate
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
//`@Configuration`注解用于将类声明为Spring配置类，可以被Spring IoC容器读取。
public class RedisConfig {
    //创建RedisTemplate
    @Bean()
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();//创建RedisTemplate对象
        template.setConnectionFactory(factory);//设置连接工厂
        template.setKeySerializer(new StringRedisSerializer());//设置key的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());//设置hash key的序列化方式
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());//设置value的序列化方式
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());  //设置hash value的序列化方式
        template.afterPropertiesSet();  //初始化RedisTemplate对象
        return template;        //返回RedisTemplate对象

    }

}
