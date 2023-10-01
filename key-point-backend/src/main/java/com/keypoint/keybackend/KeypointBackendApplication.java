package com.keypoint.keybackend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableDubbo
@MapperScan("com.keypoint.keybackend.mapper")
public class KeypointBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeypointBackendApplication.class, args);
    }

}
