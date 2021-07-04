package com.blackcodex.demo.spring.twitter;

import io.swagger.Swagger2SpringBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BCSpringTwitterDemo extends Swagger2SpringBoot {
    public static void main(String[] args) {
        SpringApplication.run(BCSpringTwitterDemo.class, args);
    }
}
