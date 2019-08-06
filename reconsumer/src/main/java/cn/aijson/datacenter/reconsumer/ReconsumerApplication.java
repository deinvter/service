package cn.aijson.datacenter.reconsumer;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@ComponentScan(basePackages = {"cn.aijson.datacenter.reconsumer.**"})
@MapperScan(basePackages = {"cn.aijson.datacenter.reconsumer.mapper.**"})
@SpringBootApplication
public class ReconsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReconsumerApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
