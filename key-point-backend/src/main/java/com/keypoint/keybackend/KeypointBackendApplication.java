package com.keypoint.keybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableScheduling
//@EnableDubbo
@EnableTransactionManagement
@MapperScan("com.keypoint.keybackend.mapper")
public class KeypointBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeypointBackendApplication.class, args);
    }

}
