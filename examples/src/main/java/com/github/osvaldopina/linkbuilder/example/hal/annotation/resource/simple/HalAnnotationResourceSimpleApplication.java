package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HalAnnotationResourceSimpleApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(HalAnnotationResourceSimpleApplication.class, args);
    }






}