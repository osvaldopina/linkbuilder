package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HalAnnotationResourceComposedApplication  {


    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(HalAnnotationResourceComposedApplication.class, args);
    }


}