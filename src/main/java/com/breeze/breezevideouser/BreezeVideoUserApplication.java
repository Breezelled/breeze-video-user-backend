package com.breeze.breezevideouser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class BreezeVideoUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreezeVideoUserApplication.class, args);
    }

}
