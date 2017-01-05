package com.github.osvaldopina.linkbuilder.example.annotation.controller.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class AnnotationControllerSimpleApplication extends WebMvcAutoConfiguration {


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(AnnotationControllerSimpleApplication.class, args);
    }






}