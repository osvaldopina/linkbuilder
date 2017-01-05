package com.github.osvaldopina.linkbuilder.example.annotation.resource.composed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@Configuration
public class AnnotationResourceComposedApplication {


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(AnnotationResourceComposedApplication.class, args);
    }




}