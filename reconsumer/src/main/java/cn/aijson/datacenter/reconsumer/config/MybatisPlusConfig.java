package cn.aijson.datacenter.reconsumer.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor pagination = new PaginationInterceptor();
        pagination.setDialectType(DbType.MYSQL.getDb());
        //开启pageHelper支持
//        pagination.setLocalPage(true);
        return pagination;
    }


    /**
     * 性能监控插件,生产环境可以关闭
     *
     * @return
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

}
