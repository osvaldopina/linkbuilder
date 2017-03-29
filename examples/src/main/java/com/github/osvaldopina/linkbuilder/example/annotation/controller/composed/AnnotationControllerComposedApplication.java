package com.github.osvaldopina.linkbuilder.example.annotation.controller.composed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class AnnotationControllerComposedApplication {


    public static void main(String[] args) throws Exception {
       SpringApplication.run(AnnotationControllerComposedApplication.class, args);
    }

}