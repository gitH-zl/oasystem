package com.exoa.oasystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.exoa.oasystem.dao")
@SpringBootApplication
public class OasystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OasystemApplication.class, args);
    }

}
