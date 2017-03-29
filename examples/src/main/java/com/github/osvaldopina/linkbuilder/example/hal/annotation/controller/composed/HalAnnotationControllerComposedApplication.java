package com.github.osvaldopina.linkbuilder.example.hal.annotation.controller.composed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
public class HalAnnotationControllerComposedApplication {


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(HalAnnotationControllerComposedApplication.class, args);
    }

}