package com.afstudy.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.afstudy.springboot.mapper")
public class SpringbootShequApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShequApplication.class, args);
    }

}
