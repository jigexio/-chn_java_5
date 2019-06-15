package com.chaojilaji.messageborad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ServletComponentScan
@ComponentScan({"com.chaojilaji.messageborad"})
@MapperScan({"com.chaojilaji.messageborad.mapper"})
public class MessageboradApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageboradApplication.class, args);
    }

}
