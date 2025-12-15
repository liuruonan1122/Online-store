package com.kk.em.config;
//配置MybatisPlus分页插件
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 扫描 Mapper 接口所在的包
public class MybatisPlusConfig {
/**添加分页插件**/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 设置最大单页限制数量，默认500
        return interceptor;
        // 旧的分页插件,分页插件的执行顺序在分页拦截器之前
    }
}
