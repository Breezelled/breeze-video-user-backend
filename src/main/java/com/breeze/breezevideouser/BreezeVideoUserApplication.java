package com.breeze.breezevideouser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

@ComponentScan(basePackages = {"com.breeze"})
@MapperScan(basePackages = {"com.breeze.breezevideouser.mapper"})
@SpringBootApplication
public class BreezeVideoUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeVideoUserApplication.class, args);
    }

}
